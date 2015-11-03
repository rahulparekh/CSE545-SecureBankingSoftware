package com.sbs.group11.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sbs.group11.model.Account;
import com.sbs.group11.model.ChangePassword;
import com.sbs.group11.model.ModifiedUser;
import com.sbs.group11.model.OTP;
import com.sbs.group11.model.PaymentRequest;
import com.sbs.group11.model.StatementMonthYear;
import com.sbs.group11.model.Transaction;
import com.sbs.group11.model.User;
import com.sbs.group11.service.AccountService;
import com.sbs.group11.service.BCryptHashService;
import com.sbs.group11.service.InternalUserService;
import com.sbs.group11.service.ModifiedService;
import com.sbs.group11.service.OTPService;
import com.sbs.group11.service.PkiService;
import com.sbs.group11.service.SendEmailService;
import com.sbs.group11.service.TransactionService;
import com.sbs.group11.service.UserService;

/**
 * UserController: Controls most of our application UI paths for all types of
 * users including internal users.
 * 
 * Matches all urls with the path /home/**
 * 
 * @author Rahul
 */
@Controller
@RequestMapping(value = "/home")
public class ExternalUserController {

	final static Logger logger = Logger.getLogger(ExternalUserController.class);
	final private BigDecimal CRITICAL_VALUE = new BigDecimal(500);

	@Autowired
	private UserService userService;

	@Autowired
	private AccountService accountService;

	@Autowired
	private TransactionService transactionService;

	@Autowired
	private OTPService otpService;

	@Autowired
	private SendEmailService emailService;

	@Autowired
	private BCryptHashService hashService;
	@Autowired
	InternalUserService internalUserService;
	@Autowired
	SmartValidator validator;

	@Autowired
	ModifiedService modifiedService;
	
	@ModelAttribute("changepassword")
	public ChangePassword getChangePasswordObject() {
		return new ChangePassword();		
	}
	@Autowired
	PkiService pkiService;

	@RequestMapping(value = "/process-otp", method = RequestMethod.POST)
	public String processOTP(ModelMap model, HttpServletRequest request,
			RedirectAttributes attr) {

		String otp = request.getParameter("otp");
		String transactionId = request.getParameter("transactionId");
		String type = request.getParameter("type");
		String otpId = request.getParameter("otpId");
		String redirectUrl = "redirect:/home/credit-debit";

		if (type == null) {

			attr.addFlashAttribute("failureMsg",
					"OTP could not be verified. Please try again");
			return redirectUrl;
		}

		if (type.equalsIgnoreCase("creditdebit")) {

		} else if (type.equalsIgnoreCase("fundtransfer")) {
			redirectUrl = "redirect:/home/fund-transfer";
		} else {
			attr.addFlashAttribute("failureMsg",
					"OPT could not be verified. Please try again");
			return redirectUrl;
		}

		OTP dbOTP = otpService.getOTP(otpId);

		if (request.getParameter("submit").equalsIgnoreCase("cancel")) {

			logger.debug("Cancelling the transaction");
			logger.info(new DateTime().toLocalDateTime().minusMinutes(5));

			// set transaction to cancel and expiry the opt
			dbOTP.setOTPExpiry(new DateTime().toLocalDateTime().minusMinutes(5));
			Transaction transaction = dbOTP.getTransaction();
			transaction.setStatus("cancelled");
			transaction.setUpdatedAt(new DateTime().toLocalDateTime());
			Set<OTP> otpSet = new HashSet<OTP>();
			otpSet.add(dbOTP);
			transaction.setOtp(otpSet);
			dbOTP.setTransaction(transaction);
			transactionService.addTransaction(transaction);

			attr.addFlashAttribute("successMsg",
					"Transaction cancelled successfully.");

			return redirectUrl;
		}

		if (request.getParameter("submit").equalsIgnoreCase("confirm")) {

			if (!otpService.isOTPVerified(dbOTP, otp, transactionId, type)) {
				attr.addFlashAttribute("failureMsg",
						"OPT could not be verified. Please try again");
				return redirectUrl;
			}

			dbOTP.setOTPExpiry(new DateTime().toLocalDateTime().minusMinutes(5));
			Transaction transaction = dbOTP.getTransaction();

			// if fund transfer
			// get the reverse transaction too and set it to pending
			if (type.equalsIgnoreCase("fundtransfer")) {

				if (!transactionService.updateTransactionPair(transaction,
						"pairtxn", BigDecimal.ZERO)) {
					attr.addFlashAttribute("failureMsg",
							"Bad request. Please cancel the transaction and create a new transaction.");
					return redirectUrl;
				}

			}

			transaction.setStatus("pending");
			transaction.setUpdatedAt(new DateTime().toLocalDateTime());
			Set<OTP> otpSet = new HashSet<OTP>();
			otpSet.add(dbOTP);
			transaction.setOtp(otpSet);
			dbOTP.setTransaction(transaction);
			transactionService.addTransaction(transaction);

			attr.addFlashAttribute(
					"successMsg",
					"Transaction completed successfully. Transaction should show up on your account shortly after bank approval.");

			return redirectUrl;
		}

		attr.addFlashAttribute("failureMsg",
				"OPT could not be verified. Please try again");
		return redirectUrl;
	}

	/**
	 * Gets the home.
	 *
	 * @param model
	 *            the model
	 * @return the home
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String getHome(ModelMap model) {
		User user = userService.getUserDetails();
		List<Account> accounts = accountService.getAccountsByCustomerID(user
				.getCustomerID());
		model.addAttribute("title", "Welcome " + user.getFirstName());
		model.addAttribute("fullname",
				user.getFirstName() + " " + user.getLastName());
		model.addAttribute("accounts", accounts);
		model.addAttribute("customerId", user.getCustomerID());
		return "customer/home";
	}

	/**
	 * Gets the credit and debit page.
	 *
	 * @param model
	 *            the model
	 * @return the credit debit
	 */
	@RequestMapping(value = "/credit-debit", method = RequestMethod.GET)
	public String getCreditDebit(ModelMap model) {
		User user = userService.getUserDetails();
		model.put("user", user);

		// Check if OTP exists
		OTP otp = otpService.getOTPByCustomerIDAndType(user.getCustomerID(),
				"creditdebit");

		if (otp != null) {
			logger.debug("Otp exists");
			logger.debug(otp);

			String transactionId = otp.getTransaction().getTransactionID();
			int otpId = otp.getId();

			model.addAttribute("heading", "SBS Credit / Debit Funds");
			model.addAttribute("title", "Verify Transaction For Credit/Debit");
			model.addAttribute("transactionId", transactionId);
			model.addAttribute("otpId", otpId);
			model.addAttribute("type", "creditdebit");

			return "customer/otp";
		}

		List<Account> accounts = accountService.getAccountsByCustomerID(user
				.getCustomerID());
		model.addAttribute("title", "Welcome " + user.getFirstName());
		model.addAttribute("fullname",
				user.getFirstName() + " " + user.getLastName());
		model.addAttribute("accounts", accounts);
		return "customer/creditdebit";
	}

	/**
	 * Post credit debit.
	 *
	 * @param model
	 *            The Spring ModelMap
	 * @param request
	 *            HttpServlet request
	 * @param transaction
	 *            ModelAttribute transaction
	 * @param result
	 *            the BindingResult used for validation
	 * @param attr
	 *            RedirectAttribute used to pass error/success messages
	 * @return the string
	 */
	@RequestMapping(value = "/credit-debit", method = RequestMethod.POST)
	public String postCreditDebit(ModelMap model, HttpServletRequest request,
			@ModelAttribute("transaction") Transaction transaction,
			BindingResult result, RedirectAttributes attr) {

		// Get user details
		User user = userService.getUserDetails();
		model.put("user", user);

		// Get user accounts and other data for display
		List<Account> accounts = accountService.getAccountsByCustomerID(user
				.getCustomerID());
		model.addAttribute("fullname",
				user.getFirstName() + " " + user.getLastName());
		model.addAttribute("accounts", accounts);
		model.addAttribute("title", "Welcome " + user.getFirstName());

		BigDecimal amount = transactionService.getBigDecimal(request
				.getParameter("amount"));

		String isCritical = transactionService.isCritical(amount,
				CRITICAL_VALUE);

		// create the transaction object
		transaction = new Transaction(
				transactionService.getUniqueTransactionID(), "Self "
						+ request.getParameter("type"),
				request.getParameter("number"), request.getParameter("number"),
				"pending", request.getParameter("type"), amount, isCritical,
				request.getParameter("number"), UUID.randomUUID().toString());

		// Validate the model
		validator.validate(transaction, result);
		if (result.hasErrors()) {
			logger.debug(result);

			// attributes for validation failures
			attr.addFlashAttribute(
					"org.springframework.validation.BindingResult.transaction",
					result);
			attr.addFlashAttribute("transaction", transaction);

			attr.addFlashAttribute("failureMsg",
					"You have errors in your request.");

			// redirect to the credit debit view page
			return "redirect:/home/credit-debit";
		}

		// If account is empty or null, skip the account service check
		Account account = accountService.getValidAccountByNumber(
				request.getParameter("number"), accounts);

		// Exit the transaction if Account doesn't exist
		if (account == null) {
			logger.warn("Someone tried credit/debit functionality for some other account. Details:");
			logger.warn("Credit/Debit Acc No: "
					+ request.getParameter("number"));
			logger.warn("Customer ID: " + user.getCustomerID());
			attr.addFlashAttribute("failureMsg",
					"Could not process your transaction. Please try again or contact the bank.");
			return "redirect:/home/credit-debit";
		}

		// Check if Debit amount is < balance in the account
		if (request.getParameter("type").equalsIgnoreCase("debit")
				&& amount.compareTo(account.getBalance()) >= 0) {
			attr.addFlashAttribute(
					"failureMsg",
					"Could not process your transaction. Debit amount cannot be higher than account balance");
			return "redirect:/home/credit-debit";
		}

		// OTP only for critical transactions
		if (isCritical.equalsIgnoreCase("yes")) {

			// Generate OTP
			String otp = null;
			try {
				String sessionId = RequestContextHolder
						.currentRequestAttributes().getSessionId();
				logger.debug("Got session id: " + sessionId);
				Random range = new Random();
				int rand = range.nextInt(Integer.MAX_VALUE);
				otp = otpService.generateOTP(sessionId.getBytes(), new Long(
						rand), 8, false, rand);
			} catch (InvalidKeyException e) {
				logger.warn(e);
				attr.addFlashAttribute("failureMsg",
						"Could not process your transaction. Please try again or contact the bank.");
				return "redirect:/home/credit-debit";
			} catch (NoSuchAlgorithmException e) {
				logger.warn(e);
				attr.addFlashAttribute("failureMsg",
						"Could not process your transaction. Please try again or contact the bank.");
				return "redirect:/home/credit-debit";
			}

			OTP otpObj = new OTP(hashService.getBCryptHash(otp),
					user.getCustomerID(), "creditdebit");
			Set<OTP> otpSet = new HashSet<OTP>();
			otpSet.add(otpObj);
			transaction.setOtp(otpSet);
			transaction.setStatus("otp");
			otpObj.setTransaction(transaction);
			transactionService.addTransaction(transaction);

			String content = "You have made a new request to for Credit / Debit "
					+ "To process the payment, please go to "
					+ "https://group11.mobicloud.asu.edu/home/credit-debit.\n\n"
					+ "The payment request will expire in the next 10 minutes from now.\n\n"
					+ "Please use the following OTP to accept the payment: "
					+ otp + "\n\n" + "You can accept the payment or cancel it.";

			// send email
			emailService.sendEmail(user.getEmail(), "Sun Devil Banking OTP",
					content);

			return "redirect:/home/credit-debit";

		}

		transactionService.addTransaction(transaction);

		attr.addFlashAttribute(
				"successMsg",
				"Transaction completed successfully. Transaction should show up on your account shortly after bank approval.");

		// redirect to the credit debit view page
		return "redirect:/home/credit-debit";
	}

	@RequestMapping(value = "/statements", method = RequestMethod.GET)
	public String getStatements(ModelMap model, HttpServletRequest request) {
		User user = userService.getUserDetails();
		model.put("user", user);

		List<Account> accounts = accountService.getAccountsByCustomerID(user
				.getCustomerID());
		model.addAttribute("title", "Account Statements");
		model.addAttribute("accounts", accounts);

		return "customer/statements";
	}

	@RequestMapping(value = "/statements", method = RequestMethod.POST)
	public String postStatements(ModelMap model, HttpServletRequest request,
			RedirectAttributes attr) {
		User user = userService.getUserDetails();
		model.put("user", user);

		List<Account> accounts = accountService.getAccountsByCustomerID(user
				.getCustomerID());
		model.addAttribute("title", "Account Statements " + user.getFirstName());
		model.addAttribute("fullname",
				user.getFirstName() + " " + user.getLastName());
		model.addAttribute("accounts", accounts);

		// If account is empty or null, skip the account service check
		Account account = accountService.getValidAccountByNumber(
				request.getParameter("number"), accounts);
		// Exit the transaction if Account doesn't exist
		if (account == null) {
			logger.warn("Someone tried statements functionality for some other account. Details:");
			logger.warn("Acc No: " + request.getParameter("number"));
			logger.warn("Customer ID: " + user.getCustomerID());
			attr.addFlashAttribute("statementFailureMsg",
					"Could not process your request. Please try again or contact the bank.");
			return "redirect:/home/statements";
		}

		List<StatementMonthYear> statements = transactionService
				.getStatementMonths(request.getParameter("number"));
		if (statements.size() > 0) {
			logger.debug("Received statements: " + statements.get(0).getMonth());
		}
		model.addAttribute("statements", statements);
		model.addAttribute("accNumber", request.getParameter("number"));

		return "customer/statements";
	}

	@RequestMapping(value = "/statements/view", method = RequestMethod.POST)
	public String postViewStatement(ModelMap model, HttpServletRequest request,
			RedirectAttributes attr) {

		User user = userService.getUserDetails();
		model.put("user", user);

		List<Account> accounts = accountService.getAccountsByCustomerID(user
				.getCustomerID());

		// If account is empty or null, skip the account service check
		Account account = accountService.getValidAccountByNumber(
				request.getParameter("number"), accounts);

		if (account == null) {
			logger.warn("Someone tried view statement functionality for some other account. Details:");
			logger.warn("Acc No: " + request.getParameter("number"));
			logger.warn("Customer ID: " + user.getCustomerID());
			attr.addFlashAttribute("statementFailureMsg",
					"Could not process your request. Please try again or contact the bank.");
			return "redirect:/home/statements";
		}

		List<Transaction> transactions = transactionService
				.getCompletedTransactionsByAccountNummber(
						request.getParameter("number"),
						request.getParameter("month"),
						Integer.parseInt(request.getParameter("year")));

		model.addAttribute("title", "Account Statements");
		model.addAttribute("user", user);
		model.addAttribute("account", account);
		model.addAttribute("transactions", transactions);
		model.addAttribute("statementName", request.getParameter("month") + " "
				+ request.getParameter("year"));

		return "customer/statement";
	}

	@RequestMapping(value = "/statements/download", method = RequestMethod.POST)
	public ModelAndView postDownloadStatement(ModelMap model,
			HttpServletRequest request, RedirectAttributes attr) {

		User user = userService.getUserDetails();
		model.put("user", user);

		List<Account> accounts = accountService.getAccountsByCustomerID(user
				.getCustomerID());

		// If account is empty or null, skip the account service check
		Account account = accountService.getValidAccountByNumber(
				request.getParameter("number"), accounts);

		if (account == null) {
			logger.warn("Someone tried view statement functionality for some other account. Details:");
			logger.warn("Acc No: " + request.getParameter("number"));
			logger.warn("Customer ID: " + user.getCustomerID());
			attr.addFlashAttribute("statementFailureMsg",
					"Could not process your request. Please try again or contact the bank.");
			return new ModelAndView("redirect:/home/statements");
		}

		List<Transaction> transactions = transactionService
				.getCompletedTransactionsByAccountNummber(
						request.getParameter("number"),
						request.getParameter("month"),
						Integer.parseInt(request.getParameter("year")));

		model.addAttribute("title", "Account Statements");
		model.addAttribute("user", user);
		model.addAttribute("account", account);
		model.addAttribute("transactions", transactions);
		model.addAttribute("statementName", request.getParameter("month") + " "
				+ request.getParameter("year"));

		return new ModelAndView("pdfView", "model", model);
	}

	@RequestMapping(value = "/fund-transfer", method = RequestMethod.GET)
	public String getFundTransfer(ModelMap model) {
		User user = userService.getUserDetails();
		model.put("user", user);

		OTP otp = otpService.getOTPByCustomerIDAndType(user.getCustomerID(),
				"fundtransfer");

		if (otp != null) {
			logger.debug("Otp exists");
			logger.debug(otp);

			String transactionId = otp.getTransaction().getTransactionID();
			int otpId = otp.getId();

			model.addAttribute("heading", "SBS Fund Transfer");
			model.addAttribute("title", "Verify Transaction For Fund Transfer");
			model.addAttribute("transactionId", transactionId);
			model.addAttribute("otpId", otpId);
			model.addAttribute("type", "fundtransfer");

			return "customer/otp";
		}

		List<Account> accounts = accountService.getAccountsByCustomerID(user
				.getCustomerID());
		model.addAttribute("title", "Welcome " + user.getFirstName());
		model.addAttribute("fullname",
				user.getFirstName() + " " + user.getLastName());
		model.addAttribute("accounts", accounts);
		return "customer/fundtransfer";
	}

	@RequestMapping(value = "/fund-transfer", method = RequestMethod.POST)
	public String postFundTransfer(ModelMap model, HttpServletRequest request,
			@ModelAttribute("transaction") Transaction senderTransaction,
			BindingResult result, RedirectAttributes attr) {

		// Get user details
		User user = userService.getUserDetails();
		model.put("user", user);
		
		
		String key = request.getParameter("key");
		try {

			String cipher = pkiService.paymentinfoencryption(
					user.getCustomerID(), key);
			if (!pkiService.paymentinfodecryption(user.getCustomerID(), cipher)) {
				attr.addFlashAttribute("failureMsg",
						"Could not process your transaction. Private key doesn't match.");
				return "redirect:/home/fund-transfer";
			}
			
		} catch (Exception e) {
			logger.error(e);
			attr.addFlashAttribute("failureMsg",
					"Could not process your transaction. Private key doesn't match.");
			return "redirect:/home/fund-transfer";

		}
		

		// Get user accounts and other data for display
		List<Account> accounts = accountService.getAccountsByCustomerID(user
				.getCustomerID());
		model.addAttribute("fullname",
				user.getFirstName() + " " + user.getLastName());
		model.addAttribute("accounts", accounts);
		model.addAttribute("title", "Welcome " + user.getFirstName());

		// If account is empty or null, skip the account service check
		Account account = accountService.getValidAccountByNumber(
				request.getParameter("senderAccNumber"), accounts);

		// Exit the transaction if Account doesn't exist
		if (account == null) {
			logger.warn("Someone tried credit/debit functionality for some other account. Details:");
			logger.warn("Credit/Debit Acc No: "
					+ request.getParameter("number"));
			logger.warn("Customer ID: " + user.getCustomerID());
			attr.addFlashAttribute("failureMsg",
					"Could not process your transaction. Please try again or contact the bank.");
			return "redirect:/home/fund-transfer";
		}

		boolean isTransferAccountValid = transactionService
				.isTransferAccountValid(accounts, request, model, user, attr);
		
		String receiverAccNumber = "";
		if (request.getParameter("type").equalsIgnoreCase("internal")) {
			receiverAccNumber = request.getParameter("receiverAccNumber");
			logger.info("internal transfer");
		} else {
			receiverAccNumber = request
					.getParameter("receiverAccNumberExternal");
			logger.info("external transfer");
		}
		
		// if sender and receiver accs are equal
		if (receiverAccNumber.trim().equalsIgnoreCase(request.getParameter("senderAccNumber").trim())) {
			
			attr.addFlashAttribute("failureMsg",
					"Could not process transaction. Transferring to the same account is not allowed.");
			return "redirect:/home/fund-transfer";
		}

		logger.debug("receiverAccNumber: " + receiverAccNumber);

		logger.debug("isTransferAccountValid: " + isTransferAccountValid);
		if (isTransferAccountValid) {

			BigDecimal amount = transactionService.getBigDecimal(request
					.getParameter("amount"));

			String isCritical = transactionService.isCritical(amount,
					CRITICAL_VALUE);

			String statusSender = "approved";
			String statusReceiver = "approved";
			if (isCritical.equalsIgnoreCase("yes")) {
				statusSender = "pending";
				statusReceiver = "pairtxn";
			}

			String pairId = UUID.randomUUID().toString();

			// create the transaction object
			senderTransaction = new Transaction(
					transactionService.getUniqueTransactionID(),
					"Fund Transfer From: "
							+ request.getParameter("senderAccNumber") + " To: "
							+ receiverAccNumber, receiverAccNumber,
					request.getParameter("senderAccNumber"), statusSender, "Debit",
					amount, isCritical,
					request.getParameter("senderAccNumber"), pairId);

			logger.debug("Sender Transaction created: " + senderTransaction);

			// Validate the model
			validator.validate(senderTransaction, result);
			logger.debug("Validated model");

			if (result.hasErrors()) {
				logger.debug("Validation errors: ");
				logger.debug(result);

				// attributes for validation failures
				attr.addFlashAttribute(
						"org.springframework.validation.BindingResult.transaction",
						result);
				attr.addFlashAttribute("transaction", senderTransaction);

				// redirect to the credit debit view page
				return "redirect:/home/fund-transfer";
			}

			logger.debug("No validation errors");

			// Check if Debit amount is < balance in the account
			if (amount.compareTo(account.getBalance()) >= 0) {
				logger.debug("Debit < Balance");
				attr.addFlashAttribute(
						"failureMsg",
						"Could not process your transaction. Debit amount cannot be higher than account balance");
				return "redirect:/home/fund-transfer";
			}

			Transaction receiverTransaction = new Transaction(
					transactionService.getUniqueTransactionID(),
					"Fund Transfer From: "
							+ request.getParameter("senderAccNumber") + " To: "
							+ receiverAccNumber, receiverAccNumber,
					request.getParameter("senderAccNumber"), statusReceiver, "Credit",
					amount, isCritical, receiverAccNumber, pairId);

			logger.debug("Receiver Transaction created: " + receiverTransaction);
			logger.debug("isCritical? " + isCritical);

			// Don't transfer if the transaction is critical
			if (!isCritical.equalsIgnoreCase("yes")) {

				logger.debug("it's not critical. Transferring " + isCritical);

				try {
					logger.debug("Trying to transfer funds");
					accountService.transferFunds(transactionService,
							accountService, senderTransaction,
							receiverTransaction, amount);

					attr.addFlashAttribute(
							"successMsg",
							"Transaction completed successfully. Transaction should show up on your account shortly.");

					return "redirect:/home/fund-transfer";
				} catch (Exception e) {
					logger.error(e);
					attr.addFlashAttribute("failureMsg",
							"Transfer unsucessful. Please try again or contact the bank.");
					return "redirect:/home/fund-transfer";
				}

			}

			logger.debug("it is indeed critical " + isCritical);

			// Generate OTP etc and redirect
			String otp = null;
			try {
				String sessionId = RequestContextHolder
						.currentRequestAttributes().getSessionId();
				logger.debug("Got session id: " + sessionId);
				Random range = new Random();
				int rand = range.nextInt(Integer.MAX_VALUE);
				otp = otpService.generateOTP(sessionId.getBytes(), new Long(
						rand), 8, false, rand);
			} catch (InvalidKeyException e) {
				logger.warn(e);
				attr.addFlashAttribute("failureMsg",
						"Could not process your transaction. Please try again or contact the bank.");
				return "redirect:/home/fund-transfer";
			} catch (NoSuchAlgorithmException e) {
				logger.error(e);
				attr.addFlashAttribute("failureMsg",
						"Could not process your transaction. Please try again or contact the bank.");
				return "redirect:/home/fund-transfer";
			}

			senderTransaction.setStatus("otp");
			OTP otpObj = new OTP(hashService.getBCryptHash(otp),
					user.getCustomerID(), "fundtransfer");
			Set<OTP> otpSet = new HashSet<OTP>();
			otpSet.add(otpObj);
			senderTransaction.setOtp(otpSet);
			otpObj.setTransaction(senderTransaction);
			transactionService.addTransaction(senderTransaction);
			receiverTransaction.setStatus("otp");
			transactionService.addTransaction(receiverTransaction);

			String content = "You have made a new request to for Credit / Debit "
					+ "To process the payment, please go to "
					+ "https://group11.mobicloud.asu.edu/home/fund-transfer.\n\n"
					+ "The payment request will expire in the next 10 minutes from now.\n\n"
					+ "Please use the following OTP to accept the payment: "
					+ otp + "\n\n" + "You can accept the payment or cancel it.";

			// send email
			emailService.sendEmail(user.getEmail(), "Sun Devil Banking OTP",
					content);

			return "redirect:/home/fund-transfer";

		} else {
			attr.addFlashAttribute("failureMsg",
					"Transfer unsucessful. Please try again or contact the bank.");
		}

		// redirect to the view page
		return "redirect:/home/fund-transfer";
	}

	/**
	 * Gets the payment page customers.
	 *
	 * @param model
	 *            the model
	 * @return view
	 */
	@RequestMapping(value = "/payments", method = RequestMethod.GET)
	public String getPaymentsForCustomer(ModelMap model) {
		User user = userService.getUserDetails();
		model.put("user", user);
		model.addAttribute("title", "Payments");

		List<User> merchants = userService.getUsersOfType("Merchant");

		model.put("merchants", merchants);

		List<Account> userAccounts = accountService
				.getAccountsByCustomerID(user.getCustomerID());
		model.addAttribute("userAccounts", userAccounts);

		return "customer/customerpayments";

	}

	@RequestMapping(value = "/payments", method = RequestMethod.POST)
	public String postPaymentsForCustomer(ModelMap model,
			HttpServletRequest request,
			@ModelAttribute("paymentrequest") PaymentRequest paymentRequest,
			BindingResult result, RedirectAttributes attr) throws IOException {

		// ByteArrayInputStream stream = new
		// ByteArrayInputStream(file.getBytes());
		// String key = IOUtils.toString(stream, "UTF-8");

		// byte[] contents = file.getBytes();
		// String key = new String(contents);
		String key = request.getParameter("key");
		logger.info("received key is " + key);
		User user = userService.getUserDetails();
		
		try {
			String cipher = pkiService.paymentinfoencryption(user.getCustomerID(),
				key);
			if (!pkiService.paymentinfodecryption(user.getCustomerID(), cipher)) {
				attr.addFlashAttribute("failureMsg",
						"Could not process your transaction. Private key doesnt match.");
				return "redirect:/home/payments";
			}
		
		} catch (Exception e) {
			logger.error(e);
			attr.addFlashAttribute("failureMsg",
					"Could not process your transaction. Private key doesn't match.");
			return "redirect:/home/fund-transfer";

		}

		model.put("user", user);
		model.addAttribute("title", "Payments");

		List<Account> customerAccounts = accountService
				.getAccountsByCustomerID(user.getCustomerID());

		BigDecimal amount = transactionService.getBigDecimal(request
				.getParameter("amount"));
		
		// if sender and receiver accs are equal
		if (request.getParameter("merchantAccNumber").trim() 
				.equalsIgnoreCase(request.getParameter("customerAccNumber").trim())) {
			attr.addFlashAttribute("failureMsg",
					"Could not process transaction. Transferring to the same account is not allowed.");
			return "home/payments";
		}

		// Generate OTP
		String otp = null;
		try {
			String sessionId = RequestContextHolder.currentRequestAttributes()
					.getSessionId();
			logger.debug("Got session id: " + sessionId);
			Random range = new Random();
			int rand = range.nextInt(Integer.MAX_VALUE);
			otp = otpService.generateOTP(sessionId.getBytes(), new Long(rand),
					8, false, rand);
		} catch (InvalidKeyException e) {
			logger.warn(e);
			attr.addFlashAttribute("failureMsg",
					"Could not process your transaction. Please try again or contact the bank.");
			return "redirect:/home/payments";
		} catch (NoSuchAlgorithmException e) {
			logger.warn(e);
			attr.addFlashAttribute("failureMsg",
					"Could not process your transaction. Please try again or contact the bank.");
			return "redirect:/home/payments";
		}

		// Validate the PaymentRequest model
		paymentRequest = new PaymentRequest(
				request.getParameter("merchantAccNumber"),
				request.getParameter("customerAccNumber"), null, 1, 0, amount,
				"Debit", otp, 0,
				user.getFirstName() + " " + user.getLastName(),
				"Merchant Name", request.getParameter("customerAccNumber"),
				request.getParameter("merchantAccNumber"));

		validator.validate(paymentRequest, result);
		if (result.hasErrors()) {
			logger.debug(result);

			// attributes for validation failures
			attr.addFlashAttribute(
					"org.springframework.validation.BindingResult.paymentrequest",
					result);
			attr.addFlashAttribute("paymentrequest", paymentRequest);
			attr.addFlashAttribute("failureMsg",
					"You have errors in your request.");

			// redirect to the credit debit view page
			return "redirect:/home/payments";
		}

		// If account is empty or null, skip the account service check
		Account customerAccount = accountService.getValidAccountByNumber(
				request.getParameter("customerAccNumber"), customerAccounts);

		// Exit the transaction if Account doesn't exist
		if (customerAccount == null) {
			logger.warn("Someone tried payments functionality for some other account. Details:");
			logger.warn("Customer Acc No: "
					+ request.getParameter("customerAccNumber"));
			logger.warn("Customer ID: " + user.getCustomerID());
			attr.addFlashAttribute("failureMsg",
					"Could not process your request. Please try again or contact the bank.");
			return "redirect:/home/payments";
		}

		// verify that the merchant account exists and is of type merchant
		Account merchantAccount = accountService.getAccountByNumber(request
				.getParameter("merchantAccNumber"));

		if (amount.compareTo(customerAccount.getBalance()) >= 0) {
			attr.addFlashAttribute(
					"failureMsg",
					"Could not process your transaction. Debit amount cannot be higher than account balance");
			return "redirect:/home/payments";
		}

		if (merchantAccount != null && !merchantAccount.toString().isEmpty()) {

			paymentRequest.setMerchantName(merchantAccount.getUser()
					.getLastName());

			transactionService.initiatePayment(paymentRequest);

			logger.debug("Valid transaction");
			attr.addFlashAttribute(
					"successMsg",
					"Your payment request was made. You will a receive a notification shortly when your payment is approved.");

			return "redirect:/home/payments";

		}

		// log the errors and throw and unsuccessful
		logger.warn("Someone tried payments functionality for some other account. Details:");
		logger.warn("Merchant Acc No: "
				+ request.getParameter("customerAccNumber"));
		logger.warn("Customer ID: " + user.getCustomerID());

		attr.addFlashAttribute("failureMsg",
				"Could not process your request. Please try again or contact the bank.");

		return "redirect:/home/payments";

	}

	/**
	 * Gets the payment requests for customers.
	 *
	 * @param model
	 *            the model
	 * @return view
	 */
	@RequestMapping(value = "/payment-requests", method = RequestMethod.GET)
	public String getPaymentRequestsForCustomer(ModelMap model) {
		User user = userService.getUserDetails();
		model.put("user", user);
		model.addAttribute("title", "Payment Requests");
		Set<Account> accounts = user.getAccounts();
		List<PaymentRequest> requests = new ArrayList<PaymentRequest>();

		for (Account account : accounts) {
			// get all the payment requests for accounts which have been
			// initiated by merchant
			requests.addAll(transactionService.getPaymentsByAccNumber(
					account.getNumber(), 1));
		}

		// add them to the model to be displayed
		model.addAttribute("paymentrequests", requests);
		model.addAttribute("currentTime", new DateTime().toLocalDateTime());

		return "customer/customerpaymentrequests";

	}

	@RequestMapping(value = "/payment-requests", method = RequestMethod.POST)
	public String postPaymentRequestsForCustomer(ModelMap model,
			HttpServletRequest request, RedirectAttributes attr) {
		User user = userService.getUserDetails();
		Set<Account> accounts = user.getAccounts();
		model.put("user", user);
		model.addAttribute("title", "Payment Requests");

		if (request.getParameter("otp") == null
				|| request.getParameter("otp").isEmpty()) {
			attr.addFlashAttribute("failureMsg", "OTP is required.");
			return "redirect:/home/payment-requests";
		}

		// get all the pending requests
		if (request.getParameter("paymentrequest") != null) {
			PaymentRequest paymentRequest = transactionService
					.getPaymentRequest(Integer.parseInt(request
							.getParameter("paymentrequest")));

			boolean isOTPCheck = false;
			for (Account account : accounts) {

				// check if the account in the request does indeed belong to the
				// user
				if (paymentRequest.getCustomerAccNumber().equalsIgnoreCase(
						account.getNumber())) {
					isOTPCheck = true;

					logger.debug(paymentRequest.getOtp() + " == "
							+ request.getParameter("otp"));

					// check if otp hasn't expired
					if (paymentRequest.getOTPExpiry().isAfter(
							new DateTime().toLocalDateTime())) {

						// check if otp is the same
						boolean isValidOTP = paymentRequest
								.getOtp()
								.trim()
								.equalsIgnoreCase(
										request.getParameter("otp").trim());

						if (isValidOTP) {
							// proceed to accept
							transactionService.acceptPayment(paymentRequest);
							attr.addFlashAttribute("sucessMsg",
									"Payment has been accepted and is being processed.");
							return "redirect:/home/payment-requests";
						}

					}

					break;
				}

			}

			if (isOTPCheck) {
				attr.addFlashAttribute("failureMsg",
						"OTP mismatch. Please try again.");
				logger.debug("Incorrect OTP");
				return "redirect:/home/payment-requests";

			}

			logger.debug("Account in the payment request does not belong to the user");

		}

		attr.addFlashAttribute("failureMsg",
				"Could not process your request. Please try again");
		return "redirect:/home/payment-requests";

	}

	/**
	 * Gets the payments for merchants.
	 *
	 * @param model
	 *            the model
	 * @return the payments for merchants
	 */
	@RequestMapping(value = "/merchant-payments", method = RequestMethod.GET)
	public String getPaymentsForMerchants(ModelMap model) {
		User user = userService.getUserDetails();
		model.put("user", user);

		List<Account> accounts = accountService.getAccountsByCustomerID(user
				.getCustomerID());
		model.addAttribute("accounts", accounts);

		model.addAttribute("title", "Merchant Payments");

		return "customer/merchantpayments";
	}

	@RequestMapping(value = "/merchant-payments", method = RequestMethod.POST)
	public String postPaymentsForMerchants(ModelMap model,
			HttpServletRequest request,
			@ModelAttribute("paymentrequest") PaymentRequest paymentRequest,
			BindingResult result, RedirectAttributes attr) {
		User user = userService.getUserDetails();
		model.put("user", user);
		
		List<Account> customerAccounts = accountService
				.getAccountsByCustomerID(user.getCustomerID());

		BigDecimal amount = transactionService.getBigDecimal(request
				.getParameter("amount"));

		// Generate OTP
		String otp = null;
		try {
			String sessionId = RequestContextHolder.currentRequestAttributes()
					.getSessionId();
			logger.debug("Got session id: " + sessionId);
			Random range = new Random();
			int rand = range.nextInt(Integer.MAX_VALUE);
			otp = otpService.generateOTP(sessionId.getBytes(), new Long(rand),
					8, false, rand);
		} catch (InvalidKeyException e) {
			logger.warn(e);
			attr.addFlashAttribute("failureMsg",
					"Could not process your transaction. Please try again or contact the bank.");
			return "redirect:/home/merchant-payments";
		} catch (NoSuchAlgorithmException e) {
			logger.warn(e);
			attr.addFlashAttribute("failureMsg",
					"Could not process your transaction. Please try again or contact the bank.");
			return "redirect:/home/merchant-payments";
		}
		
		// if sender and receiver accs are equal
		if (request.getParameter("merchantAccNumber").trim().equalsIgnoreCase(
				request.getParameter("customerAccNumber").trim())) {
			attr.addFlashAttribute("failureMsg",
					"Could not process transaction. Transferring to the same account is not allowed.");
			return "redirect:/home/merchant-payments";
		}

		String senderAccountNumber = "";
		String receiverAccountNumber = "";
		if (request.getParameter("type").equalsIgnoreCase("credit")) {
			senderAccountNumber = request.getParameter("merchantAccNumber");
			receiverAccountNumber = request.getParameter("customerAccNumber");
		} else {
			senderAccountNumber = request.getParameter("customerAccNumber");
			receiverAccountNumber = request.getParameter("merchantAccNumber");
		}

		Account senderAccount = accountService
				.getAccountByNumber(senderAccountNumber);

		if (senderAccount == null) {
			attr.addFlashAttribute("failureMsg",
					"Could not process your request. Please try again or contact the bank.");
			return "redirect:/home/merchant-payments";
		}

		if (amount.compareTo(senderAccount.getBalance()) >= 0) {
			attr.addFlashAttribute(
					"failureMsg",
					"Could not process your transaction. Debit amount cannot be higher than account balance");
			return "redirect:/home/merchant-payments";
		}

		// Validate the PaymentReques model
		paymentRequest = new PaymentRequest(
				request.getParameter("merchantAccNumber"),
				request.getParameter("customerAccNumber"), null, 0, 1, amount,
				request.getParameter("type"), otp, 1, "Customer Name",
				user.getLastName(), senderAccountNumber, receiverAccountNumber);

		validator.validate(paymentRequest, result);
		if (result.hasErrors()) {
			logger.debug(result);

			// attributes for validation failures
			attr.addFlashAttribute(
					"org.springframework.validation.BindingResult.paymentrequest",
					result);
			attr.addFlashAttribute("paymentrequest", paymentRequest);
			attr.addFlashAttribute("failureMsg",
					"You have errors in your request.");

			// redirect to the credit debit view page
			return "redirect:/home/merchant-payments";
		}

		// If account is empty or null, skip the account service check
		Account merchantAccount = accountService.getValidAccountByNumber(
				request.getParameter("merchantAccNumber"), customerAccounts);

		// Exit the transaction if Account doesn't exist
		if (merchantAccount == null) {
			logger.warn("Someone tried payments functionality for some other account. Details:");
			logger.warn("Merchant Acc No: "
					+ request.getParameter("customerAccNumber"));
			logger.warn("Merchant ID: " + user.getCustomerID());
			attr.addFlashAttribute("failureMsg",
					"Could not process your request. Please try again or contact the bank.");
			return "redirect:/home/merchant-payments";
		}

		// verify that the customer account exists and is of type merchant
		Account customerAccount = accountService.getAccountByNumber(request
				.getParameter("customerAccNumber"));

		if (customerAccount != null && !customerAccount.toString().isEmpty()) {
			paymentRequest.setCustomerName(customerAccount.getUser()
					.getFirstName()
					+ " "
					+ customerAccount.getUser().getLastName());
			transactionService.initiatePayment(paymentRequest);

			logger.debug("Valid transaction");
			attr.addFlashAttribute(
					"successMsg",
					"Your payment request was made. You will a receive a notification shortly when your payment is approved.");

			return "redirect:/home/merchant-payments";

		}

		// Account doesn't exist. Mention it since the
		// merchant enters the customer account himself
		attr.addFlashAttribute("failureMsg",
				"Could not process your request. The customer account is invalid.");

		return "redirect:/home/merchant-payments";
	}

	/**
	 * Gets the payment requests for customers.
	 *
	 * @param model
	 *            the model
	 * @return view
	 */
	@RequestMapping(value = "/merchant-payment-requests", method = RequestMethod.GET)
	public String getPaymentRequestsForMerchants(ModelMap model) {
		User user = userService.getUserDetails();
		model.put("user", user);
		model.addAttribute("title", "Merchant Payment Requests");

		Set<Account> accounts = user.getAccounts();
		List<PaymentRequest> requests = new ArrayList<PaymentRequest>();

		for (Account account : accounts) {
			// get all the payment requests for accounts which have been
			// initiated by customer
			requests.addAll(transactionService.getPaymentsByAccNumber(
					account.getNumber(), 0));
		}

		// add them to the model to be displayed
		model.addAttribute("paymentrequests", requests);
		model.addAttribute("currentTime", new DateTime().toLocalDateTime());

		return "customer/merchantpaymentrequests";

	}

	@RequestMapping(value = "/merchant-payment-requests", method = RequestMethod.POST)
	public String postPaymentRequestsForMerchants(ModelMap model,
			HttpServletRequest request, RedirectAttributes attr) {
		User user = userService.getUserDetails();
		Set<Account> accounts = user.getAccounts();
		model.put("user", user);
		model.addAttribute("title", "Payment Requests");

		if (request.getParameter("otp") == null
				|| request.getParameter("otp").isEmpty()) {
			attr.addFlashAttribute("failureMsg", "OTP is required.");
			return "redirect:/home/merchant-payment-requests";
		}

		// get all the pending requests
		if (request.getParameter("paymentrequest") != null) {
			PaymentRequest paymentRequest = transactionService
					.getPaymentRequest(Integer.parseInt(request
							.getParameter("paymentrequest")));

			boolean isOTPCheck = false;
			for (Account account : accounts) {

				// check if the account in the request does indeed belong to the
				// user
				if (paymentRequest.getMerchantAccNumber().equalsIgnoreCase(
						account.getNumber())) {
					isOTPCheck = true;

					logger.debug(paymentRequest.getOtp() + " == "
							+ request.getParameter("otp"));

					// check if otp hasn't expired
					if (paymentRequest.getOTPExpiry().isAfter(
							new DateTime().toLocalDateTime())) {

						// check if otp is the same
						boolean isValidOTP = paymentRequest
								.getOtp()
								.trim()
								.equalsIgnoreCase(
										request.getParameter("otp").trim());

						if (isValidOTP) {
							// proceed to accept
							transactionService.acceptPayment(paymentRequest);
							attr.addFlashAttribute("sucessMsg",
									"Payment has been accepted and is being processed.");
							return "redirect:/home/merchant-payment-requests";
						}

					}

					break;
				}

			}

			if (isOTPCheck) {
				attr.addFlashAttribute("failureMsg",
						"OTP mismatch. Please try again.");
				logger.debug("Incorrect OTP");
				return "redirect:/home/merchant-payment-requests";

			}

			logger.debug("Account in the payment request does not belong to the user");

		}

		attr.addFlashAttribute("failureMsg",
				"Could not process your request. Please try again");
		return "redirect:/home/merchant-payment-requests";

	}

	@RequestMapping(value = "/customer-setting", method = RequestMethod.GET)
	public String getManagerSetting(ModelMap model) {
		User customer = userService.getUserDetails();
		
		if (!model.containsAttribute("user")) {
			model.addAttribute(new User());
		}
		model.addAttribute("customer", customer);
		return "customer/settings";
	}

	@RequestMapping(value = "/customer-setting", method = RequestMethod.POST)
	public String changeManagerSetting(ModelMap model,
			@ModelAttribute("user") User user, BindingResult result,
			RedirectAttributes attr) {

		User currentUser = userService.getUserDetails();

		// Validate fields
		user.setCustomerID(currentUser.getCustomerID());
		user.setEmail(currentUser.getEmail());
		user.setEmployeeOverride(user.getEmployeeOverride());
		user.setEnabled(currentUser.getEnabled());
		user.setUserType(currentUser.getUserType());
		user.setCreatedAt(new DateTime().toLocalDateTime());
		user.setLastLoginAt(currentUser.getLastLoginAt());
		user.setPassword(currentUser.getPassword());
		user.setUpdatedAt(new DateTime().toLocalDateTime());

		validator.validate(user, result);
		if (result.hasErrors()) {
			logger.debug(result);


			attr.addFlashAttribute("failureMsg",
					"You have errors in your request:");
			
			// parse errors and output
			String fieldErrors = "";
			for (FieldError error : result.getFieldErrors()) {
				fieldErrors += "<div>" + error.getField() + " " + error.getDefaultMessage() + "</div>";
			}
			
			attr.addFlashAttribute("fieldErrors", fieldErrors);

			// redirect to the credit debit view page
			return "redirect:/home/customer-setting";
		}

		ModifiedUser modifiedUser = new ModifiedUser();
		modifiedUser.setCustomerID(currentUser.getCustomerID());
		modifiedUser.setAddressLine1(user.getAddressLine1());
		modifiedUser.setAddressLine2(user.getAddressLine2());
		modifiedUser.setPassword(currentUser.getPassword());
		modifiedUser.setEmail(currentUser.getEmail());
		modifiedUser.setEnabled(currentUser.getEnabled());
		modifiedUser.setFirstName(user.getFirstName());
		modifiedUser.setLastName(user.getLastName());
		modifiedUser.setMiddleName(user.getMiddleName());
		modifiedUser.setState(user.getState());
		modifiedUser.setZipCode(user.getZipCode());
		modifiedUser.setUserType(currentUser.getUserType());
		modifiedUser.setPhone(user.getPhone());
		modifiedUser.setCreatedAt(currentUser.getCreatedAt());
		modifiedUser.setUpdatedAt(new DateTime().toLocalDateTime());
		modifiedUser.setLastLoginAt(currentUser.getLastLoginAt());
		modifiedUser.setStatus("pending");
		modifiedUser.setEmployeeOverride(user.getEmployeeOverride());
		long epoch = System.currentTimeMillis() / 1000;
		modifiedUser.setRequestid(epoch);
		modifiedService.addRequest(modifiedUser);
		
		logger.info(modifiedUser.getEmployeeOverride());

		model.addAttribute("title", "Your Settings!");

		attr.addFlashAttribute("successMsg",
				"Your request was successful. Please wait for bank approval.");

		return "redirect:/home/customer-setting";
	}
	
	@RequestMapping(value = "/password-change", method = RequestMethod.GET)
	public String getconfirmPassword(ModelMap model) {
		User customer = userService.getUserDetails();
		
		return "customer/customer_change_password";
	}
	@RequestMapping(value="/passwordSuccess", method = RequestMethod.POST)
    public String postChangePasswordMethod(ModelMap model,@ModelAttribute("changepassword") 
    ChangePassword changepassword,BindingResult result,RedirectAttributes attr) {
    	
		
		User customer = userService.getUserDetails();
		
		
		
		if(hashService.checkBCryptHash(changepassword.getCurrentpassword(), customer.getPassword())){
			
			if(changepassword.getConfirmpassword().equals(changepassword.getNewpassword())){
				
				internalUserService.updatePassword(customer.getEmail(), changepassword.getNewpassword());
				
				attr.addFlashAttribute("successMsg",
						"The password is changed successfully.Please login");
				return "redirect:/";
			}
			else{
				attr.addFlashAttribute("failureMsg",
						"The newpassword and confirm password are not same");
				return "redirect:/";
			}
		}
		else
		{
			attr.addFlashAttribute("failureMsg",
					"The currentpassword and confirm password are not same");
			return "redirect:/";
		 
		}
		
		
	}
	
	

}
