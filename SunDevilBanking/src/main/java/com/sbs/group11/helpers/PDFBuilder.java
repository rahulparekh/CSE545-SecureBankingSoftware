package com.sbs.group11.helpers;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.format.DateTimeFormat;
import org.springframework.ui.ModelMap;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.sbs.group11.model.Account;
import com.sbs.group11.model.Transaction;
import com.sbs.group11.model.User;

/**
 * Generates PDF on the fly
 */
public class PDFBuilder extends AbstractITextPdfView {

	@SuppressWarnings("unchecked")
	@Override
	protected void buildPdfDocument(Map<String, Object> viewModel, Document doc,
			PdfWriter writer, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		// get data model which is passed by the Spring container
		ModelMap model = (ModelMap) viewModel.get("model");
		
		List<Transaction> transactions = (List<Transaction>) model
				.get("transactions");
		
		User user = (User) model.get("user");
		
		Account account = (Account) model.get("account");

		doc.add(new Paragraph("Account Statement: " + (String) model.get("statementName")));
		
		doc.add(new Paragraph(user.getFirstName() + " " + user.getLastName() ));
		
		String addressLine2 = "";
		if(user.getAddressLine2() != null && !user.getAddressLine2().isEmpty()) {
			addressLine2 = user.getAddressLine2();
		}
		
		doc.add(new Paragraph(user.getAddressLine1() + " " + addressLine2));
		doc.add(new Paragraph(user.getState() + " " + user.getZipCode() ));
		doc.add(new Paragraph("Balance: " + account.getBalance().toString() ));
		doc.add(new Paragraph("Acc Number: " + account.getNumber()));

		PdfPTable table = new PdfPTable(6);
		table.setWidthPercentage(100.0f);
		table.setWidths(new float[] { 3.0f, 2.0f, 2.0f, 2.0f, 2.0f, 2.0f });
		table.setSpacingBefore(10);

		// define font for table header row
		Font font = FontFactory.getFont(FontFactory.HELVETICA);
		font.setColor(BaseColor.WHITE);

		// define table header cell
		PdfPCell cell = new PdfPCell();
		cell.setBackgroundColor(BaseColor.BLACK);
		cell.setPadding(2);

		// write table header
		cell.setPhrase(new Phrase("Txn ID", font));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Date", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Description", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Debit", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Credit", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Balance", font));
		table.addCell(cell);
		
		String balance = "";
		for (Transaction transaction : transactions) {			
			if (transaction.getBalance() != null) {
				balance = transaction.getBalance().toString();
			}
			table.addCell(transaction.getTransactionID());
			table.addCell(DateTimeFormat.forPattern("dd MMM, yyyy").print(transaction.getCreatedAt()));
			table.addCell(transaction.getName());
			table.addCell(transaction.getType().equalsIgnoreCase("debit") ? transaction
					.getAmount().toString() : "");
			table.addCell(transaction.getType().equalsIgnoreCase("credit") ? transaction
					.getAmount().toString() : "");
			table.addCell(balance);
		}

		doc.add(table);

	}
}