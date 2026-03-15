# Техническое задание

## Проект: Message Service (мини-версия)

## Контекст

В нашей системе используется микросервисная архитектура. Один из
сервисов получает сообщения от ботов и виджетов и отправляет их в Kafka.

Твоя задача --- реализовать мини-версию сервиса сообщений, который: 1.
Получает сообщения из Kafka 2. Сохраняет их в базе данных 3. Отправляет
уведомление через WebSocket 4. Предоставляет базовый REST API для
получения сообщений

## Общая архитектура

Сервис должен быть реализован как отдельный микросервис, готовый к
интеграции в микросервисную архитектуру.

Основной поток обработки: 1. Сообщение приходит из Kafka топика
`message.in` 2. Сервис обрабатывает сообщение 3. Создает/обновляет
сущности в БД 4. Отправляет уведомление через WebSocket 5. Предоставляет
API для чтения сообщений

## Входящее сообщение (Kafka)

**Топик:** `message.in`

Формат сообщения:

``` json
{
  "authorId": "uuid",
  "content": "string"
}
```

## Доменная модель --- Conversation

Чат пользователя.

Правила: - 1 conversation на 1 автора - conversation может содержать
много сообщений

Структура:

    Conversation
    id: UUID
    authorId: UUID
    createdAt: timestamp

## Доменная модель --- Message

Сообщение внутри conversation.

    Message
    id: UUID
    conversationId: UUID
    content: string
    createdAt: timestamp

## Связи

    Conversation (1) ---- (N) Message

## Логика обработки сообщения

Когда сервис получает сообщение из Kafka:

1.  Проверить существует ли `Conversation` для `authorId`
2.  Если нет --- создать новую
3.  Создать `Message`
4.  Сохранить сообщение в БД
5.  Отправить WebSocket событие

## WebSocket

После создания сообщения сервис должен отправить событие на WebSocket.

**Destination:**

    /queue/conversations/{conversationId}

Payload:

``` json
{
  "messageId": "uuid"
}
```

## REST API

Получить сообщение по id:

    GET /messages/{messageId}

Получить все сообщения conversation:

    GET /conversations/{conversationId}/messages

## Технические требования

Рекомендуемый стек:

-   Java 21+
-   Spring Boot
-   Spring Web
-   Spring Data JDBC
-   Kafka (Spring Kafka)
-   WebSocket (STOMP)
-   PostgreSQL

## Бонус (необязательно)

Будет плюсом, если реализовано:

-   pagination для получения сообщений
-   unit тесты
-   docker-compose (Kafka + Postgres)
-   логирование

## Критерии оценки

Мы оцениваем:

-   архитектуру
-   читаемость кода
-   использование Spring
-   работу с Kafka
-   работу с WebSocket
