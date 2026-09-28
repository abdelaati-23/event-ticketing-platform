package com.ticketing.backend.controller;

import com.ticketing.backend.services.TicketAiAgent;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/ai")
@RequiredArgsConstructor
public class AiController {
    private final TicketAiAgent ticketAiAgent;
    @PostMapping("/chat")
    public ResponseEntity<String> chatWithAi(@RequestBody String message) {
        String response= ticketAiAgent.chat(message);
        return ResponseEntity.ok(response);
    }
}
