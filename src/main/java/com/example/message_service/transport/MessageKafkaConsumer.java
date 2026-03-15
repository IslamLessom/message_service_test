package com.example.message_service.transport;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.example.message_service.message.MessageApplicationService;
import com.example.message_service.message.ProcessedIncomingMessage;
import com.example.message_service.notification.ConversationNotificationService;

@Component
public class MessageKafkaConsumer {
    private static final Logger log = LoggerFactory.getLogger(MessageKafkaConsumer.class);

    private final MessageApplicationService messageApplicationService;

    private final ConversationNotificationService conversationNotificationService;

    public MessageKafkaConsumer(
        MessageApplicationService messageApplicationService,
        ConversationNotificationService conversationNotificationService
        ) {
        this.messageApplicationService = messageApplicationService;
        this.conversationNotificationService = conversationNotificationService;
    }

    @KafkaListener(topics = "message.in", groupId = "message_service_group")
    public void consume(IncomingMessagePayload payload) {
        log.info("Received message for author {}: {}", payload.authorId(), payload.content());
       
        if (payload.authorId() == null || payload.content() == null || payload.content().isBlank()) {
            log.warn("Dropping invalid payload: {}", payload);
            return;
        }

        ProcessedIncomingMessage result = messageApplicationService
        .processIncoming(payload.authorId(), payload.content());

        conversationNotificationService.notifyMessageCreated(
            result.conversation().getId(),
            result.message().getId()
        );

        log.info("Saved message {} for conversation {}",
            result.message().getId(),
            result.conversation().getId());
    }
}