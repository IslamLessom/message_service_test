package com.example.message_service.message;

import java.util.UUID;
import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.message_service.conversation.Conversation;
import com.example.message_service.conversation.ConversationRepository;

@Service 
public class MessageApplicationService {
    private final ConversationRepository conversationRepository;
    private final MessageRepository messageRepository;

    public MessageApplicationService(
        ConversationRepository conversationRepository,
        MessageRepository messageRepository
    ) {
        this.conversationRepository = conversationRepository;
        this.messageRepository = messageRepository;
    }

    @Transactional
    public ProcessedIncomingMessage processIncoming(UUID authorId, String content) {
        Objects.requireNonNull(authorId, "authorId must not be null");
        if (content == null || content.isBlank()) {
            throw new IllegalArgumentException("content must not be null or blank");
        }
        Conversation conversation = conversationRepository.findByAuthorId(authorId)
        .orElseGet(() -> conversationRepository.save(new Conversation(authorId)));

        Message message = messageRepository.save(new Message(conversation.getId(), content));
        return new ProcessedIncomingMessage(conversation, message);
    }

}