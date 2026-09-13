package com.ticketing.backend.services;

import com.ticketing.backend.domain.dto.TicketPurchasedEvent;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationListener {
    @RabbitListener(queues = "ticket_notification_queue")
    public void handeleTicketPurchased(TicketPurchasedEvent event) {
        System.out.println("✅ ASYNC TASK TRIGGERED: Preparing to send confirmation email for Ticket ID: " + event.ticketId());
    }
}
