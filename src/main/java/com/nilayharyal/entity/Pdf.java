package com.nilayharyal.entity;

import java.awt.Color;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

public class Pdf extends AbstractPdfView 
{

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document pdfdoc, PdfWriter pdfwriter, HttpServletRequest request,
			HttpServletResponse response) throws Exception 
	{
		Font  font = new Font(Font.TIMES_ROMAN, 18, Font.BOLDITALIC, Color.BLACK); 
		Font  headerFont = new Font(Font.TIMES_ROMAN, 12, Font.BOLDITALIC, Color.BLACK); 

		Bookings b = (Bookings) model.get("booking");
		Paragraph title = new Paragraph("MBTA travel Ticket", font);

		Phrase header = new Phrase("Bookings details are as follows:");

		Phrase footer = new Phrase("For any queries redirect to mbta444@gmail.com");

		PdfPTable table = new PdfPTable(6);
		table.setWidthPercentage(100.0f);
		table.setWidths(new float[] {3.0f,3.0f,3.0f,3.0f,3.0f,3.0f});
		table.setSpacingBefore(10);

		PdfPCell cell = new PdfPCell();
		cell.setBackgroundColor(Color.WHITE);
		cell.setPadding(5);

		cell.setPhrase(new Phrase("Arrival Station", headerFont));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Departure Station", headerFont));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Train Name", headerFont));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Travel Date", headerFont));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Passengers", headerFont));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Passenger Name", headerFont));
		table.addCell(cell);

		table.addCell(b.getArrivalStation());
		table.addCell(b.getDepartureStation());
		table.addCell(b.getTrainName());
		table.addCell(b.getTravelDate());
		table.addCell(String.valueOf(b.getTickets()));
		table.addCell(b.getUser().getFirstName());

		pdfdoc.add(title);
		pdfdoc.add(header);
		pdfdoc.add(table);
		pdfdoc.add(footer);
	}
}



