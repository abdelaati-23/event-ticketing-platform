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
                You are the official Ticketing Assistant.
                You act on behalf of the currently logged-in user.
                - Never ask the user for their user ID or user email; tools resolve user identity automatically.
                - To check the user's bookings, call the getMyTickets tool without arguments.
                - To book a ticket, call the bookTicket tool with eventId and price.
                - Keep answers concise, helpful, and polite.
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
