package com.ticketing.backend.ai;

import com.ticketing.backend.domain.entity.Ticket;
import com.ticketing.backend.domain.repository.TicketRepository;
import com.ticketing.backend.services.TicketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
@Component
@RequiredArgsConstructor
@Slf4j
public class TicketTools {
    private final TicketService ticketService;
    private final TicketRepository ticketRepository;
    public record PurchaseTicketInput(UUID eventId, UUID userId, BigDecimal price) {}
    public record PurchaseTicketOutput(UUID ticketId, String status, BigDecimal price) {}
    public record UserTicketDetails(UUID ticketId, String eventTitle, BigDecimal price, String status) {}

    @Tool(description = "Purchases a ticket for an event on behalf of a user. Deducts available seats and publishes a confirmation event.")
    public PurchaseTicketOutput bookTicket(PurchaseTicketInput input) {
        log.info("Agent executing ticket purchase for user {} at event {}", input.userId(), input.eventId());

        Ticket ticket = ticketService.purchaseTicket(
                input.eventId(),
                input.userId(),
                input.price()
        );

        return new PurchaseTicketOutput(
                ticket.getId(),
                ticket.getStatus().name(),
                ticket.getPrice()
        );
    }

    @Tool(description = "Retrieves all booked tickets and their event names for a specific user ID.")
    public List<UserTicketDetails> getUserTickets(UUID userId) {
        log.info("Agent fetching tickets for user {}", userId);
        return ticketRepository.findAll().stream()
                .filter(t -> t.getUser() != null && t.getUser().getId().equals(userId))
                .map(t -> new UserTicketDetails(
                        t.getId(),
                        t.getEvent() != null ? t.getEvent().getTitle() : "Unknown Event",
                        t.getPrice(),
                        t.getStatus() != null ? t.getStatus().name() : "PAID"
                ))
                .toList();
    }

}
