package com.ticketing.backend.domain.dto;

public record TicketPurchasedEvent(String ticketId, String eventId, String userId) {}