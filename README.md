# Weather Service
Сервис "Погодный радар". Cервис предоставдяет REST API для получения погодных условий

## Пользовательская история:
- все запросы к API защищены (JWT)
- пользователь может получить погоду, указав локацию (город, город и страна)
- история запросов пользователя хранится в базе
- пользователь может получить свою историю запросов
- пользователь может добавить локацию в избранное
- пользователь может получить список избранных локаций

## Содержание
- [Технологии](#технологии)
- [Использование](#использование)
- [Endpoints](#endpoints)

## Технологии
- [Java 18](https://www.gatsbyjs.com/)
- [Spring Boot 6.0](https://www.typescriptlang.org/)
- [Spring Web](https://www.typescriptlang.org/)
- [Spring Data](https://www.typescriptlang.org/)
- [Spring Security](https://www.typescriptlang.org/)
- [PostgreSQL](https://www.typescriptlang.org/)
- [OpenAPI](https://www.typescriptlang.org/)
- [Docker](https://www.typescriptlang.org/)

## Использование
Указываем API KEY для доступа к [Weather API](https://openweathermap.org) в файле application.properties

Для локального запуска указываем профиль -local

Для Docker используется профиль default.

## Endpoints:

- POST /api/v1/user/register

@RequestParam String name - имя пользователя для регистрации

Используется для получения JWT Token.

- GET /api/v1/user/history

Используется для получения истории пользователя. 

- POST /api/v1/user/favorite

Используется для добавления новой локации в избранное.

- GET /api/v1/user/favorite

Используется для получения списка избранных локаций. 

- GET api/v1/weather/

@RequestParam city String - для получения погоды по городу

@RequestParam country String (required = false) - можем указать для точного выбора города  

Используется для получения погодных условий в нужной локации.


## FAQ 
Если потребители вашего кода часто задают одни и те же вопросы, добавьте ответы на них в этом разделе.
