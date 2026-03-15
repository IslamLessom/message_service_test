package com.example.message_service.conversation;

import java.util.UUID;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface ConversationRepository extends CrudRepository<Conversation, UUID> {
    Optional<Conversation> findByAuthorId(UUID authorId);
}
