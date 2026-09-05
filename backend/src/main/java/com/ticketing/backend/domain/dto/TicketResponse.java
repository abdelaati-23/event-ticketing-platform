package com.ticketing.backend.domain.dto;


import com.ticketing.backend.domain.enums.TicketStatus;

import java.math.BigDecimal;
import java.util.UUID;

public record TicketResponse(
        UUID id,
        UUID eventId,
        UUID userId,
        BigDecimal price,
        TicketStatus status
) {}