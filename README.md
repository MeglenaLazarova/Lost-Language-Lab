# LostLanguageLab

LostLanguageLab е уеб приложение за събиране, описване, търсене и запазване на архаични, диалектни и исторически български думи.
Проектът е изграден като две независими Spring Boot приложения, които комуникират чрез Feign Client.

---

## ⚙️ Технологии

- Java 17
- Spring Boot 3.4+
- Spring MVC + Thymeleaf
- Spring Data JPA
- Spring Security
- Spring Cache
- Spring Scheduling
- OpenFeign
- MySQL (Main Application)
- PostgreSQL (Search Microservice)
- Maven

---

## 🧩 Архитектура

Проектът следва Feature-Based Architecture и включва:

- Main Application (UI + бизнес логика)
- Search-Service Microservice (REST API за търсене)
- Feign Client за комуникация между приложенията
- Отделни бази данни
- Слоеста архитектура: Controller → Service → Repository

---

## 🗃 Модел на данните (Main Application)

### Entities:
- User – потребител с роли (USER, ADMIN)
- UserRole – роля на потребителя
- Category – категория за думи
- ArchaicWord – архаична дума с значение, произход, пример и категория
- Comment – коментар към дума

### Relationships:
- User → Comments (One-to-Many)
- Category → Words (One-to-Many)
- Word → Comments (One-to-Many)

---

## 🗃 Модел на данните (Search Microservice)

### Entity:
- SearchRecord – записва всяка заявка за търсене

---

## 🌐 Web Pages (Main Application)

1. Home
2. Login
3. Register
4. Profile
5. Edit Profile
6. All Words
7. Word Details
8. Create Word
9. Edit Word
10. All Categories
11. Category Details
12. Create Category
13. Edit Category
14. Comments Page
15. Search Results
16. Search History

---

## 🔥 Основни функционалности (Main Application)

- Създаване, редакция и изтриване на архаични думи
- Създаване, редакция и изтриване на категории
- Добавяне и изтриване на коментари
- Регистрация и вход на потребители
- Редакция на профил
- Смяна на роли от администратор
- Търсене на думи чрез микросървиса
- История на търсенията

---

## 🔥 Функционалности (Search Microservice)

- Записване на заявка за търсене
- Изтриване на запис
- Извличане на всички записи
- Извличане на топ 3 най-търсени думи

---

## 🔐 Security

- Роли: USER, ADMIN
- Open endpoints
- Authenticated endpoints
- Authorized endpoints
- CSRF включен
- Администратор може да променя роли
- Потребител може да редактира профила си

---

## 🕒 Scheduling

### Main Application:
- Cron job: седмично изчистване на статистики
- FixedRate job: логване на броя записи на всеки 30 секунди

---

## ⚡ Caching

- Cacheable за списък с думи
- CacheEvict при създаване/редакция/изтриване

---

## 🧪 Testing

- Unit tests (services, exception handlers, scheduler)
- Integration tests
- API tests
- 70%+ coverage

---

## 📝 Logging

Всеки валиден функционален метод съдържа поне 1 лог.
Използва се SLF4J + Lombok @Slf4j.

---

## 🚀 Стартиране

### Main Application

```
mvn clean install
mvn spring-boot:run
```

### Search Microservice

```
mvn clean install
mvn spring-boot:run
```

Двете приложения работят на различни портове.

---

## 🧭 Feign Client Integration

Main app → Search-Service:

- POST /search/save
- DELETE /search/delete/{id}
- GET /search/all
- GET /search/top3

---

## 🗂 Git Commit Structure

Примерни commit-и:

- feat: implement archaic word CRUD
- feat: integrate search microservice via Feign
- test: add unit tests for exception handlers, scheduler tasks and deleteRecord logic
- refactor: remove unused code and clean up project structure
- docs: add README.md documentation

---

## 🎯 Summary

LostLanguageLab напълно покрива всички изисквания на Spring Advanced:

- 2 приложения
- Feign комуникация
- 6+ функционалности
- Security
- Scheduling
- Caching
- Testing
- Logging
- 10+ страници
- README.md
- Conventional commits
