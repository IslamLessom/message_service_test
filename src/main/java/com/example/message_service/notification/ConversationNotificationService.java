package com.example.message_service.notification;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.messaging.simp.SimpMessagingTemplate;

@Service
public class ConversationNotificationService {
    private final SimpMessagingTemplate messagingTemplate;

   public ConversationNotificationService(SimpMessagingTemplate messagingTemplate) {
    this.messagingTemplate = messagingTemplate;
}

public void notifyMessageCreated(UUID conversationId, UUID messageId) {
    ConversationNotificationPayload payload = new ConversationNotificationPayload(messageId);
    messagingTemplate.convertAndSend("/queue/conversations/" + conversationId, payload);
}
}