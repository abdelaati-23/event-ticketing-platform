package com.ticketing.backend.services;

import com.ticketing.backend.ai.TicketTools;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class TicketAiAgent {

    private final ChatClient chatClient;

    public TicketAiAgent(ChatClient.Builder chatClientBuilder,
                         TicketTools ticketTools,
                         ChatMemory chatMemory) {
        this.chatClient = chatClientBuilder
                .defaultSystem("""
                You are the official Ticketing Assistant.
                You act on behalf of the currently logged-in user.
                - Maintain conversation context across turns. If the user refers to "it" or "that show", resolve it from earlier messages.
                - Never ask the user for their user ID or user email; tools resolve user identity automatically.
                - To check the user's bookings, call the getMyTickets tool without arguments.
                - To book a ticket, call the bookTicket tool with eventId and price.
                - Keep answers concise, helpful, and polite.
                """)
                .defaultTools(ticketTools)
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
                .build();
    }

    public String chat(String userMessage) {
        String conversationId = getAuthenticatedUserEmail();

        return chatClient.prompt()
                .user(userMessage)
                .advisors(advisorSpec -> advisorSpec
                        .param("chat_memory_conversation_id", conversationId)
                )
                .call()
                .content();
    }

    private String getAuthenticatedUserEmail() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || "anonymousUser".equals(auth.getPrincipal())) {
            throw new AccessDeniedException("User must be authenticated to chat with the AI assistant.");
        }
        return auth.getName();
    }
}