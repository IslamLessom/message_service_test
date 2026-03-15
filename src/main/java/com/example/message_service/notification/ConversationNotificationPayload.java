package com.example.message_service.notification;

import java.util.UUID;

public record ConversationNotificationPayload(
    UUID messageId
) {}