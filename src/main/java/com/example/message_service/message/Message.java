package com.example.message_service.message;

import java.time.Instant;
import java.util.UUID;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("messages")
public class Message {

    @Id
    private UUID id;

    @Column("conversation_id")
    private UUID conversationId;

    @Column("content")
    private String content;

    @CreatedDate
    @Column("created_at")
    private Instant createdAt;

    public Message() {
    }

    public Message(UUID id, UUID conversationId, String content, Instant createdAt) {
        this.id = id;
        this.conversationId = conversationId;
        this.content = content;
        this.createdAt = createdAt;
    }

    public Message(UUID conversationId, String content) {
        this(null, conversationId, content, null);
    }

    public UUID getId() {
        return id;
    }

    public UUID getConversationId() {
        return conversationId;
    }

    public String getContent() {
        return content;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

}