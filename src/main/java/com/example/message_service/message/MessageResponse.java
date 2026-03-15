package com.example.message_service.message;

import java.util.UUID;
import java.time.Instant;

public record MessageResponse(
    UUID id,
    UUID conversationId,
    String content,
    Instant createdAt
) {}