# PetStore API Automation Tests

Этот проект предоставляет автоматизированные тесты для PetStore API (https://petstore.swagger.io) с использованием Java и REST Assured.

## Структура проекта

```
petstore-api-tests/
├── src/main/java/org/example/
│   ├── api/          # Классы API клиента
│   ├── model/        # Классы моделей данных
│   └── test/         # Тестовые классы
├── README.md
└── pom.xml           # Конфигурация Maven
```

## Используемые технологии

- Java 11
- JUnit 5
- REST Assured
- Lombok
- Allure Reporting

## Покрытие API

### Pet API
- Создание питомца
- Получение питомца по ID
- Обновление питомца
- Удаление питомца
- Поиск питомцев по статусу
- Обновление питомца с данными формы
- Загрузка изображения питомца

### Store API
- Создание заказа
- Получение заказа по ID
- Удаление заказа
- Получение инвентаря

### User API
- Создание пользователя
- Получение пользователя по имени
- Обновление пользователя
- Удаление пользователя
- Логин/логаут пользователя
- Создание пользователей списком/массивом

## Запуск тестов

1. Клонируйте репозиторий:
   ```bash
   git clone https://github.com/your-username/petstore-api-tests.git
   ```

2. Запустите тесты с помощью Maven:
   ```bash
   mvn clean test
   ```

## 📊 Генерация отчетов Allure

1. Установите Allure:
```bash
mvn allure:install
```

2. Соберите отчет:
```bash
mvn allure:report
```

3. Запустите сервер с отчетом:
```bash
mvn allure:serve
```