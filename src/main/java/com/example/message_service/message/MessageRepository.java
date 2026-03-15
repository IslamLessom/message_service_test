package com.example.message_service.message;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

public interface MessageRepository extends CrudRepository<Message, UUID> {
    @Query("""
        select id, conversation_id, content, created_at
        from messages
        where conversation_id = :conversationId
        order by created_at desc
        offset :offset limit :limit
    """)
    List<Message> findByConversationIdPaged(UUID conversationId, long offset, long limit);
}