# Message Service

Мини-сервис для приема сообщений из Kafka, сохранения в PostgreSQL и отправки уведомлений через WebSocket.

---

## Стек технологий

- Java 21+
- Spring Boot
- Spring Web
- Spring Data JDBC
- Kafka (Spring Kafka)
- WebSocket (STOMP)
- PostgreSQL
- Docker Compose

---

## Быстрый старт

```bash
# Клонируйте репозиторий
 git clone <repo-url>
 cd message_service_java

# Запустите инфраструктуру
 docker-compose up -d

# Запустите приложение
 ./mvnw spring-boot:run
```

---

## Переменные окружения

Файл `.env`:

| Переменная         | Описание                |
|--------------------|------------------------|
| DB_URL             | JDBC URL PostgreSQL     |
| DB_USERNAME        | Пользователь БД         |
| DB_PASSWORD        | Пароль БД               |
| KAFKA_BOOTSTRAP_SERVERS | Адрес Kafka         |
| POSTGRES_USER           | Пользователь PostgreSQL (для контейнера) |
| POSTGRES_PASSWORD       | Пароль PostgreSQL (для контейнера) |
| POSTGRES_DB             | Имя базы данных (для контейнера) |
| KAFKA_LISTENERS         | Слушатели Kafka (для контейнера) |
| KAFKA_ADVERTISED_LISTENERS | Рекламируемые слушатели Kafka (для контейнера) |
| KAFKA_CLUSTER_ID        | ID кластера Kafka (для контейнера) |
| DB_URL                  | JDBC URL PostgreSQL (для Spring Boot) |
| DB_USERNAME             | Пользователь БД (для Spring Boot) |
| DB_PASSWORD             | Пароль БД (для Spring Boot) |
| KAFKA_BOOTSTRAP_SERVERS | Адрес Kafka (для Spring Boot) |
