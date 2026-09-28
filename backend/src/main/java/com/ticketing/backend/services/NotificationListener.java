package com.ticketing.backend.services;

import com.ticketing.backend.domain.dto.TicketPurchasedEvent;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class NotificationListener {
    private final JavaMailSender mailSender;
    private final PdfService pdfService;
    @RabbitListener(queues = "ticket_notification_queue")
    public void handeleTicketPurchased(TicketPurchasedEvent event) {
        System.out.println(" ASYNC TASK TRIGGERED: Preparing to send confirmation email for Ticket ID: " + event.ticketId());
        try {
            byte[] pdfBytes=pdfService.generatePdfTicket(event);
            sendEmailWithAttachment(event.userId(),event.userEmail(),pdfBytes);
            System.out.println(" Email and PDF successfully processed and sent for Ticket: " + event.ticketId());
        }catch (Exception e){
            System.err.println(" Failed to process asynchronous notification: " + e.getMessage());
        }
    }
    private void sendEmailWithAttachment(String userId, String recipientEmail,byte[] pdfBytes) throws Exception {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper=new MimeMessageHelper(mimeMessage,true);
        helper.setFrom("noreply@ticketing.com", "Ticketing Support");
        helper.setTo(recipientEmail);
        helper.setSubject("Your Event Ticket Confirmation");
        helper.setText("Hello,\n\nAttached is your official event ticket PDF. Enjoy the show!\n\nBest regards,\nTicketing Team");
        helper.addAttachment("Ticket-" + userId + ".pdf", new ByteArrayResource(pdfBytes));
        mailSender.send(mimeMessage);
    }
}
