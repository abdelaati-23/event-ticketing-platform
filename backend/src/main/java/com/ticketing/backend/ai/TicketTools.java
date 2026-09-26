package com.ticketing.backend.ai;

import com.ticketing.backend.domain.entity.Ticket;
import com.ticketing.backend.domain.entity.User;
import com.ticketing.backend.domain.repository.TicketRepository;
import com.ticketing.backend.domain.repository.UserRepository;
import com.ticketing.backend.services.TicketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final UserRepository userRepository;

    // Notice: No userId required in the prompt or input schema
    public record PurchaseTicketInput(UUID eventId, BigDecimal price) {}
    public record PurchaseTicketOutput(UUID ticketId, String status, BigDecimal price) {}
    public record UserTicketDetails(UUID ticketId, String eventTitle, BigDecimal price, String status) {}

    @Tool(description = "Purchases a ticket for the currently logged-in user. Requires only the eventId and price.")
    public PurchaseTicketOutput bookTicket(PurchaseTicketInput input) {
        User currentUser = getAuthenticatedUser();
        log.info("AI Tool booking ticket for authenticated user: {} ({})", currentUser.getEmail(), currentUser.getId());

        Ticket ticket = ticketService.purchaseTicket(
                input.eventId(),
                currentUser.getId(),
                input.price()
        );

        return new PurchaseTicketOutput(
                ticket.getId(),
                ticket.getStatus().name(),
                ticket.getPrice()
        );
    }

    @Tool(description = "Retrieves all booked tickets for the currently logged-in user. Requires no arguments.")
    public List<UserTicketDetails> getMyTickets() {
        User currentUser = getAuthenticatedUser();
        log.info("AI Tool querying tickets for authenticated user: {} ({})", currentUser.getEmail(), currentUser.getId());

        return ticketRepository.findAll().stream()
                .filter(t -> t.getUser() != null && t.getUser().getId().equals(currentUser.getId()))
                .map(t -> new UserTicketDetails(
                        t.getId(),
                        t.getEvent() != null ? t.getEvent().getTitle() : "Unknown Event",
                        t.getPrice(),
                        t.getStatus() != null ? t.getStatus().name() : "PAID"
                ))
                .toList();
    }

    private User getAuthenticatedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || "anonymousUser".equals(auth.getPrincipal())) {
            throw new AccessDeniedException("User must be authenticated to use ticketing tools.");
        }

        return userRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new AccessDeniedException("Authenticated user not found in database: " + auth.getName()));
    }
}