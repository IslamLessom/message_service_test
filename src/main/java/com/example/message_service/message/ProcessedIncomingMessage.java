package com.example.message_service.message;

import com.example.message_service.conversation.Conversation;

public record ProcessedIncomingMessage(
    Conversation conversation,
    Message message
) {

}