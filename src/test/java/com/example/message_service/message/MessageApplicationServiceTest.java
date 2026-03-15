package com.example.message_service.message;

import com.example.message_service.conversation.Conversation;
import com.example.message_service.conversation.ConversationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

class MessageApplicationServiceTest {
    private ConversationRepository conversationRepository;
    private MessageRepository messageRepository;
    private MessageApplicationService service;

    @BeforeEach
    void setUp() {
        conversationRepository = Mockito.mock(ConversationRepository.class);
        messageRepository = Mockito.mock(MessageRepository.class);
        service = new MessageApplicationService(conversationRepository, messageRepository);
    }

    @Test
    void createsConversationAndMessageForNewAuthor() {
        UUID authorId = UUID.randomUUID();
        String content = "Hello!";
        Conversation conversation = new Conversation(authorId);
        Message message = new Message(conversation.getId(), content);

        Mockito.when(conversationRepository.findByAuthorId(authorId)).thenReturn(Optional.empty());
        Mockito.when(conversationRepository.save(any())).thenReturn(conversation);
        Mockito.when(messageRepository.save(any())).thenReturn(message);

        ProcessedIncomingMessage result = service.processIncoming(authorId, content);
        assertEquals(conversation, result.conversation());
        assertEquals(message, result.message());
    }

    @Test
    void addsMessageToExistingConversation() {
        UUID authorId = UUID.randomUUID();
        String content = "Second message";
        Conversation conversation = new Conversation(authorId);
        Message message = new Message(conversation.getId(), content);

        Mockito.when(conversationRepository.findByAuthorId(authorId)).thenReturn(Optional.of(conversation));
        Mockito.when(messageRepository.save(any())).thenReturn(message);

        ProcessedIncomingMessage result = service.processIncoming(authorId, content);
        assertEquals(conversation, result.conversation());
        assertEquals(message, result.message());
    }

    @Test
    void throwsOnNullAuthorId() {
        assertThrows(NullPointerException.class, () -> service.processIncoming(null, "test"));
    }

    @Test
    void throwsOnBlankContent() {
        UUID authorId = UUID.randomUUID();
        assertThrows(IllegalArgumentException.class, () -> service.processIncoming(authorId, " "));
        assertThrows(IllegalArgumentException.class, () -> service.processIncoming(authorId, null));
    }
}
