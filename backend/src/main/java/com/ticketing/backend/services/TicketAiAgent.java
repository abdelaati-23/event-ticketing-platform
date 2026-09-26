package com.ticketing.backend.services;

import com.ticketing.backend.ai.TicketTools;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class TicketAiAgent {
    private final ChatClient chatClient;
    public TicketAiAgent(ChatClient.Builder chatClientBuilder, TicketTools ticketTools) {
        this.chatClient = chatClientBuilder
                .defaultSystem("""
                You are the official ticketing assistant.
                You can look up user bookings using getUserTickets and book tickets using bookTicket.
                When a user asks about their tickets or bookings, call getUserTickets with their user ID.
                Summarize results cleanly with event title, price, and status.
                Be concise and professional.
                """)
                .defaultTools(ticketTools)
                .build();
    }
    public String chat(String userMessage){
        return chatClient.prompt()
                .user(userMessage)
                .call()
                .content();
    }
}
