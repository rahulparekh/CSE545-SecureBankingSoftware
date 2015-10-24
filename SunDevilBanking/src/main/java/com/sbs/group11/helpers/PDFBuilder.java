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
		
		String middlename = user.getMiddleName().isEmpty() ? "" : user.getMiddleName();
		doc.add(new Paragraph(user.getFirstName() + " " + middlename + " " + user.getLastName() ));
		
		String addressLine2 = "";
		if(user.getAddressLine2() != null && !user.getAddressLine2().isEmpty()) {
			addressLine2 = user.getAddressLine2();
		}
		
		doc.add(new Paragraph(user.getAddressLine1() + " " + addressLine2));
		doc.add(new Paragraph(user.getState() + " " + user.getZipCode() ));
		doc.add(new Paragraph(account.getBalance().toString() ));

		PdfPTable table = new PdfPTable(5);
		table.setWidthPercentage(100.0f);
		table.setWidths(new float[] { 3.0f, 2.0f, 2.0f, 2.0f, 1.0f });
		table.setSpacingBefore(10);

		// define font for table header row
		Font font = FontFactory.getFont(FontFactory.HELVETICA);
		font.setColor(BaseColor.WHITE);

		// define table header cell
		PdfPCell cell = new PdfPCell();
		cell.setBackgroundColor(BaseColor.BLACK);
		cell.setPadding(5);

		// write table header
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

		for (Transaction transaction : transactions) {
			table.addCell(DateTimeFormat.forPattern("dd MMM, yyyy").print(transaction.getCreatedAt()));
			table.addCell(transaction.getName());
			table.addCell(transaction.getType().equalsIgnoreCase("debit") ? transaction
					.getAmount().toString() : "");
			table.addCell(transaction.getType().equalsIgnoreCase("credit") ? transaction
					.getAmount().toString() : "");
			table.addCell("Balance");
		}

		doc.add(table);

	}
}