package com.ticketing.backend.controller;


import com.ticketing.backend.domain.dto.TicketPurchaseRequest;
import com.ticketing.backend.domain.dto.TicketPurchasedEvent;
import com.ticketing.backend.domain.dto.TicketResponse;
import com.ticketing.backend.domain.entity.Ticket;
import com.ticketing.backend.domain.repository.TicketRepository;
import com.ticketing.backend.services.PdfService;
import com.ticketing.backend.services.TicketService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;
    private final TicketRepository ticketRepository;
    private final PdfService pdfService;

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
    @GetMapping("/{id}/pdf")
    public ResponseEntity<byte[]> downloadTicketPdf(@PathVariable UUID id){
        Ticket ticket=ticketRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Ticket not found"));

        TicketPurchasedEvent event= new TicketPurchasedEvent(
                ticket.getId().toString(),
                ticket.getEvent().getId().toString(),
                ticket.getUser().getId().toString()
        );
        byte[] pdfBytes=pdfService.generatePdfTicket(event);
        HttpHeaders headers=new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        return new  ResponseEntity<>(pdfBytes,headers, HttpStatus.OK);


    }
}