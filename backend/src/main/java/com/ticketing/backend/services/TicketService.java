package com.ticketing.backend.services;

import com.ticketing.backend.domain.entity.Event;
import com.ticketing.backend.domain.entity.Ticket;
import com.ticketing.backend.domain.entity.User;
import com.ticketing.backend.domain.enums.TicketStatus;
import com.ticketing.backend.domain.repository.EventRepository;
import com.ticketing.backend.domain.repository.TicketRepository;
import com.ticketing.backend.domain.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TicketService {
    private final EventRepository eventRepository;
    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;
    @Transactional
    public Ticket purchaseTicket(UUID eventId, UUID ticketId, BigDecimal price) {
        Event event = eventRepository.findById(eventId).orElseThrow(
                ()->new IllegalArgumentException("Event not found!")
                );
        if(event.getAvailableSeats()<=0){
            throw new IllegalStateException("Event is completely sold out!");
        }
        event.setAvailableSeats(event.getAvailableSeats()-1);
        eventRepository.save(event);
        User user = userRepository.findById(ticketId).orElseThrow(
                ()->new IllegalArgumentException("User not found!")
        );
        Ticket ticket = Ticket.builder()
                .event(event)
                .user(user)
                .price(price)
                .status(TicketStatus.PAID)
                .build();
        return ticketRepository.save(ticket);

    }
}
