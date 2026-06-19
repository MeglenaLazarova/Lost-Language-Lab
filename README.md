# LostLanguageLab

LostLanguageLab е уеб приложение за събиране, описване и запазване на архаични, диалектни и исторически български думи.

## Технологии

- Java 17
- Spring Boot
- Spring Data JPA
- Spring Security (за потребители и роли)
- H2/MySQL база данни
- HTML5, CSS3
- (по желание) Thymeleaf

## Модел на данните

- User – потребител с роли (ADMIN, USER), коментари.
- Category – категория за думи, с име, тип (enum CategoryType) и описание.
- ArchaicWord – архаична дума с значение, произход, категория и коментари.
- Comment – коментар към дума, автор и дата.

## Основни функционалности

- Създаване, списък и детайли на архаични думи.
- Създаване и управление на категории.
- Регистрация и вход на потребители.
- Добавяне на коментари към думи.
- Валидация на формите (HTML5 + backend).

## Структура на проекта

- `src/main/java/bg/LostLanguageLab/...` – ентитита, услуги, контролери.
- `src/main/resources/templates` – HTML страници.
- `src/main/resources/static/css` – стилове.
- `src/main/resources/static/img` – изображения.

## Стартиране

1. Конфигурирай базата данни в `application.properties`.
2. Стартирай Spring Boot приложението.
3. Отвори `http://localhost:8080` в браузър.

