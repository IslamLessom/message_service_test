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

---