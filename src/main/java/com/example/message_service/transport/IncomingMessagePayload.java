package com.example.message_service.transport;

import java.util.UUID;

public record IncomingMessagePayload(
    UUID authorId,
    String content
) {}

