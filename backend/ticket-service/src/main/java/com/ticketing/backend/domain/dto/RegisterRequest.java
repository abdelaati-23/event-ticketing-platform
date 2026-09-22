package com.ticketing.backend.domain.dto;

import com.ticketing.backend.domain.enums.Role;

public record RegisterRequest(String email, String password, Role role) {}