package com.ticketing.backend.controller;


import com.ticketing.backend.domain.dto.TicketPurchaseRequest;
import com.ticketing.backend.domain.dto.TicketResponse;
import com.ticketing.backend.domain.entity.Ticket;
import com.ticketing.backend.services.TicketService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;

    @PostMapping("/purchase")
    public ResponseEntity<TicketResponse> purchaseTicket(@Valid @RequestBody TicketPurchaseRequest request) {

        Ticket ticket = ticketService.purchaseTicket(
                request.eventId(),
                request.userId(),
                request.price()
        );

        // Mapping manuel de l'Entité vers le DTO de réponse
        TicketResponse response = new TicketResponse(
                ticket.getId(),
                ticket.getEvent().getId(),
                ticket.getUser().getId(),
                ticket.getPrice(),
                ticket.getStatus()
        );

        return ResponseEntity.ok(response);
    }
}