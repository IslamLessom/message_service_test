package com.example.message_service.transport;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.example.message_service.conversation.Conversation;
import com.example.message_service.message.Message;
import com.example.message_service.message.MessageApplicationService;
import com.example.message_service.message.ProcessedIncomingMessage;
import com.example.message_service.notification.ConversationNotificationService;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class MessageKafkaConsumerTest {

    private MessageApplicationService messageApplicationService;
    private ConversationNotificationService conversationNotificationService;
    private MessageKafkaConsumer consumer;

    @BeforeEach
    void setUp() {
        messageApplicationService = Mockito.mock(MessageApplicationService.class);
        conversationNotificationService = Mockito.mock(ConversationNotificationService.class);
        consumer = new MessageKafkaConsumer(messageApplicationService, conversationNotificationService);
    }

    @Test
    void consume_validPayload_processesAndNotifies() {
        UUID authorId = UUID.randomUUID();
        String content = "Hello";
        IncomingMessagePayload payload = new IncomingMessagePayload(authorId, content);

        UUID conversationId = UUID.randomUUID();
        UUID messageId = UUID.randomUUID();
        Conversation conversation = new Conversation(conversationId, authorId, null);
        Message message = new Message(messageId, conversationId, content, null);
        ProcessedIncomingMessage result = new ProcessedIncomingMessage(conversation, message);

        when(messageApplicationService.processIncoming(authorId, content)).thenReturn(result);

        consumer.consume(payload);

        verify(messageApplicationService).processIncoming(authorId, content);
        verify(conversationNotificationService).notifyMessageCreated(conversationId, messageId);
    }

    @Test
    void consume_nullAuthorId_dropsPayloadAndDoesNotProcess() {
        IncomingMessagePayload payload = new IncomingMessagePayload(null, "content");

        consumer.consume(payload);

        verify(messageApplicationService, never()).processIncoming(Mockito.any(), Mockito.any());
        verify(conversationNotificationService, never()).notifyMessageCreated(Mockito.any(), Mockito.any());
    }

    @Test
    void consume_nullContent_dropsPayloadAndDoesNotProcess() {
        UUID authorId = UUID.randomUUID();
        IncomingMessagePayload payload = new IncomingMessagePayload(authorId, null);

        consumer.consume(payload);

        verify(messageApplicationService, never()).processIncoming(Mockito.any(), Mockito.any());
        verify(conversationNotificationService, never()).notifyMessageCreated(Mockito.any(), Mockito.any());
    }

    @Test
    void consume_blankContent_dropsPayloadAndDoesNotProcess() {
        UUID authorId = UUID.randomUUID();
        IncomingMessagePayload payload = new IncomingMessagePayload(authorId, "   ");

        consumer.consume(payload);

        verify(messageApplicationService, never()).processIncoming(Mockito.any(), Mockito.any());
        verify(conversationNotificationService, never()).notifyMessageCreated(Mockito.any(), Mockito.any());
    }

    @Test
    void consume_emptyContent_dropsPayloadAndDoesNotProcess() {
        UUID authorId = UUID.randomUUID();
        IncomingMessagePayload payload = new IncomingMessagePayload(authorId, "");

        consumer.consume(payload);

        verify(messageApplicationService, never()).processIncoming(Mockito.any(), Mockito.any());
        verify(conversationNotificationService, never()).notifyMessageCreated(Mockito.any(), Mockito.any());
    }
}
