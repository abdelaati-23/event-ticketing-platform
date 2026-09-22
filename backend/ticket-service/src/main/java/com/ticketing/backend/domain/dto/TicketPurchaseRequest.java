package com.ticketing.backend.domain.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record TicketPurchaseRequest(
        UUID eventId,
        UUID userId,
        BigDecimal price
) {}