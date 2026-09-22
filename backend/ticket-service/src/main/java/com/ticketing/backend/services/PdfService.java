package com.ticketing.backend.services;

import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import com.ticketing.backend.domain.dto.TicketPurchasedEvent;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Service
public class PdfService {

    public byte[] generatePdfTicket(TicketPurchasedEvent event) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, out);
            document.open();
            document.add(new Paragraph("====================================="));
            document.add(new Paragraph("         OFFICIAL EVENT TICKET            "));
            document.add(new Paragraph("=========================================="));
            document.add(new Paragraph("Ticket ID : " + event.ticketId()));
            document.add(new Paragraph("Event ID  : " + event.eventId()));
            document.add(new Paragraph("User ID   : " + event.userId()));
            document.add(new Paragraph("Status    : CONFIRMED & PAID"));
            document.add(new Paragraph("------------------------------------------"));
            document.add(new Paragraph("Thank you for your purchase!"));
            document.close();

        }catch (Exception e){
            throw new RuntimeException("Error generating PDF",e);
        }
        return out.toByteArray();
    }
}
