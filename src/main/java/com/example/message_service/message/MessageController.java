package com.example.message_service.message;

import java.util.UUID;
import java.util.List;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class MessageController {
    private final MessageRepository messageRepository;

    public MessageController(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @GetMapping("/messages/{messageId}")
    public MessageResponse getMessageById(@PathVariable UUID messageId) {
        Message message = messageRepository.findById(messageId)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, 
                "Message not found"));

        return toResponse(message);
    }

    @GetMapping("/conversations/{conversationId}/messages")
    public List<MessageResponse> getConversationMessages(
            @PathVariable UUID conversationId,
            @org.springframework.web.bind.annotation.RequestParam(defaultValue = "0") int page,
            @org.springframework.web.bind.annotation.RequestParam(defaultValue = "20") int size) {
        if (page < 0 || size <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid page or size");
        }
        long offset = (long) page * size;
        List<Message> messages = messageRepository.findByConversationIdPaged(conversationId, offset, size);
        return messages.stream()
            .map(this::toResponse)
            .toList();
    }

    private MessageResponse toResponse(Message message) {
        return new MessageResponse(
            message.getId(),
            message.getConversationId(),
            message.getContent(),
            message.getCreatedAt()
        );
    }
}
