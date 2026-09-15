package com.ticketing.backend.services;

import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import com.ticketing.backend.domain.dto.TicketPurchasedEvent;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.lowagie.text.Document;
import java.io.ByteArrayOutputStream;

@Service
@RequiredArgsConstructor
public class NotificationListener {
    private final JavaMailSender mailSender;
    @RabbitListener(queues = "ticket_notification_queue")
    public void handeleTicketPurchased(TicketPurchasedEvent event) {
        System.out.println(" ASYNC TASK TRIGGERED: Preparing to send confirmation email for Ticket ID: " + event.ticketId());
        try {
            byte[] pdfBytes=generatePdfTicket(event);
            sendEmailWithAttachment(event.userId(),"abdelatielhrech17@gmail.com",pdfBytes);
            System.out.println(" Email and PDF successfully processed and sent for Ticket: " + event.ticketId());
        }catch (Exception e){
            System.err.println(" Failed to process asynchronous notification: " + e.getMessage());
        }
    }
    private byte[] generatePdfTicket(TicketPurchasedEvent event) {
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
    private void sendEmailWithAttachment(String userId, String recipientEmail,byte[] pdfBytes) throws Exception {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper=new MimeMessageHelper(mimeMessage,true);
        helper.setTo(recipientEmail);
        helper.setSubject("Your Event Ticket Confirmation");
        helper.setText("Hello,\n\nAttached is your official event ticket PDF. Enjoy the show!\n\nBest regards,\nTicketing Team");
        helper.addAttachment("Ticket-" + userId + ".pdf", new ByteArrayResource(pdfBytes));
        mailSender.send(mimeMessage);
    }
}
