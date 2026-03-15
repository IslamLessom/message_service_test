package com.example.message_service.conversation;

import java.time.Instant;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.mapping.Column;

@Table("conversations")
public class Conversation {
    @Id 
    private UUID id;

    @Column("author_id")
    private UUID authorId;

    @CreatedDate
    @Column("created_at")
    private Instant createdAt;

    public Conversation(UUID id, UUID authorId, Instant createdAt) {
        this.id = id;
        this.authorId = authorId;
        this.createdAt = createdAt;
    }

    public Conversation() {
        this(null, null, null);
    }

    public Conversation(UUID authorId) {
        this(null, authorId, null);
    }

    public UUID getId() {
        return id;
    }

    public UUID getAuthorId() {
        return authorId;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}