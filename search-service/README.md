# Search-Service Microservice

Search-Service е самостоятелен Spring Boot REST микросървис, който обработва заявки за търсене и статистики за най-често търсените думи.
Микросървисът работи независимо от основното приложение и комуникира с него чрез Feign Client.

---

## ⚙️ Технологии

- Java 17
- Spring Boot 3.4+
- Spring Web (REST API)
- Spring Data JPA
- Spring Scheduling
- PostgreSQL
- OpenFeign (използва се от Main Application)
- Maven

---

## 🗃 Модел на данните

### Entity: `SearchRecord`
- `id` (UUID)
- `word` – търсената дума
- `searchedOn` – дата и час на търсенето

---

## 🔥 REST API Endpoints

### GET Endpoints
- **GET /api/search/all**  
  Връща всички записи за търсене.

- **GET /api/search/top**  
  Връща топ 3 най-често търсени думи.

### POST Endpoints
- **POST /api/search**  
  Създава нов запис за търсене.

### DELETE Endpoints
- **DELETE /api/search/{id}**  
  Изтрива запис по ID.

---

## 🔥 Функционалности

1. Записване на заявка за търсене
2. Изтриване на запис
3. Извличане на всички записи
4. Извличане на топ 3 най-търсени думи
5. Седмично нулиране на статистиките (scheduler)

---

## 🕒 Scheduling

### Cron Job
- `resetWeeklyStats()`  
  Изтрива всички записи всяка неделя в 00:00.

### Fixed Rate Job
- `logSearchCount()`  
  Логва броя записи на всеки 30 секунди.

---

## 🧪 Testing

Микросървисът включва:

- Unit tests за service логиката
- Unit tests за exception handlers
- Unit tests за scheduler задачите
- API tests за REST контролера

---

## 📝 Logging

Използва се `@Slf4j` за логване на:

- Създаване на записи
- Изтриване на записи
- Scheduler операции
- Грешки и изключения

---

## 🚀 Стартиране
```
mvn clean install
mvn spring-boot:run
```


Микросървисът работи на собствен порт (например `localhost:8081`).

---

## 🧭 Интеграция с Main Application

Main Application използва Feign Client за достъп до микросървиса:

- POST `/api/search`
- DELETE `/api/search/{id}`
- GET `/api/search/all`
- GET `/api/search/top`

---

## 🎯 Summary

Search-Service е напълно самостоятелен REST микросървис, който покрива всички изисквания на Spring Advanced:

- Отделно приложение
- Собствена база данни
- REST API
- 1 GET + 2 POST/DELETE endpoints
- Feign интеграция
- Scheduling
- Testing
- Logging  

