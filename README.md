# Error Free Text

REST API для автоматического исправления орфографических ошибок в тексте с помощью Yandex Speller.

Проект выполнен в рамках тестового задания на Java-стажировку.

## Технологии

- Java 21
- Spring Boot 3
- Gradle
- PostgreSQL
- Spring Data JPA
- Flyway
- Docker / Docker Compose
- JUnit 5, Mockito

## Как работает

Пользователь отправляет текст и язык (`RU` или `EN`). Приложение создаёт задачу со статусом `NEW` и возвращает её UUID.

Scheduler каждые 5 секунд проверяет наличие новых задач и отправляет текст в Yandex Speller.

Если текст длиннее 10 000 символов, он разбивается на несколько частей. Исправления применяются на основе подсказок Yandex, после чего результат сохраняется в базе данных.

Возможные статусы:

- `NEW` — задача создана.
- `PROCESSING` — текст обрабатывается.
- `COMPLETED` — исправленный текст готов.
- `FAILED` — произошла ошибка при обработке.

При ошибке Yandex задача получает статус `FAILED` с описанием ошибки.

## Запуск

Необходимы Docker и Docker Compose, а также доступ к интернету для обращения к Yandex Speller.

В корневой папке проекта выполнить:

```bash
docker compose up -d --build
```

Приложение будет доступно по адресу `http://localhost:8080`.

PostgreSQL запускается автоматически через Docker Compose. Таблицы создаются с помощью Flyway.

## API

### Создание задачи

`POST /api/correct`

Пример запроса:

```json
{
  "text": "Превет мир",
  "language": "RU"
}
```

Ответ — `201 Created`:

```json
{
  "id": "528fc1ec-aaa0-410f-95cf-67041d352d48"
}
```

### Получение результата

`GET /api/{id}`

Если задача ещё обрабатывается:

```json
{
  "status": "PROCESSING"
}
```

Если обработка завершена:

```json
{
  "status": "COMPLETED",
  "correctedText": "Привет мир"
}
```

При ошибке:

```json
{
  "status": "FAILED",
  "errorMessage": "I/O error on POST request for \"http://localhost:9999/checkTexts\": null"
}
```

### Валидация

- Поддерживаются языки `RU` и `EN`.
- Минимальная длина текста — 3 символа.
- Текст должен содержать хотя бы одну букву.

При некорректном запросе возвращается ошибка в формате:

```json
{
  "errorMessage": "Text must contain at least one letter",
  "errorCode": 40001,
  "timestamp": "2026-10-12T16:00:00",
  "path": "/api/correct"
}
```

## Тесты

В проекте используются JUnit 5 и Mockito. Есть unit-тесты для обработки текста, разбиения на части, вычисления параметров Yandex Speller, сервисов и Scheduler.

Запуск тестов:

```bash
./gradlew test
```

На Windows:

```powershell
.\gradlew.bat test
```

Интеграционный тест Yandex Speller требует подключения к интернету.