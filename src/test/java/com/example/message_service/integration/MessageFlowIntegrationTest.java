package com.example.message_service.integration;

import com.example.message_service.message.MessageApplicationService;
import com.example.message_service.message.MessageRepository;
import com.example.message_service.conversation.ConversationRepository;
import com.example.message_service.transport.IncomingMessagePayload;
import com.example.message_service.message.ProcessedIncomingMessage;
import com.example.message_service.message.Message;
import com.example.message_service.conversation.Conversation;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class MessageFlowIntegrationTest {
    @Autowired
    private MessageApplicationService messageApplicationService;
    @Autowired
    private ConversationRepository conversationRepository;
    @Autowired
    private MessageRepository messageRepository;
    private RestTemplate restTemplate = new RestTemplate();

    @Test
    void fullMessageFlow() {
        UUID authorId = UUID.randomUUID();
        String content = "Integration test message";

        // Имитация Kafka payload
        ProcessedIncomingMessage result = messageApplicationService.processIncoming(authorId, content);
        Conversation conversation = result.conversation();
        Message message = result.message();

        // Проверка сохранения в БД
        assertTrue(conversationRepository.findByAuthorId(authorId).isPresent());
        assertTrue(messageRepository.findById(message.getId()).isPresent());

        // Получение сообщения через REST API
        ResponseEntity<String> response = restTemplate.getForEntity(
            "http://localhost:8080/messages/" + message.getId(), String.class);
        assertEquals(200, response.getStatusCode().value());
        assertTrue(response.getBody().contains(content));
    }
}
