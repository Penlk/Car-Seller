# 🚀 OpenTelemetry - Пошаговое руководство запуска

## Что было сделано?

✅ **Полная интеграция OpenTelemetry** в Spring Boot 4 приложение  
✅ **Автоматическое распространение контекста** (trace ID) через REST, gRPC, Kafka  
✅ **Grafana + Tempo** для визуализации трейсов  
✅ **Нулевая конфигурация** - всё работает "из коробки"  

## 📋 Структура изменений

```
java-itmo/
├── build.gradle                          ← Зависимости OTel
├── storage/build.gradle                  ← Зависимости OTel
├── src/main/resources/
│   ├── application.yml                   ← Добавлен профиль otel
│   └── application-otel.properties       ← 🆕 OTel конфигурация
├── storage/src/main/resources/
│   ├── application.yml                   ← Добавлен профиль otel
│   └── application-otel.properties       ← 🆕 OTel конфигурация
├── src/main/java/ru/penlk/
│   └── OpenTelemetryConfig.java          ← 🆕 Spring конфиг
├── storage/src/main/java/penlk/
│   └── OpenTelemetryConfig.java          ← 🆕 Spring конфиг
├── docker-compose.yaml                   ← Обновлен (добавлены otel-collector, tempo, grafana, loki)
├── traces/
│   ├── otel-collector.yaml              ✅ Уже есть (но проверен)
│   ├── tempo.yaml                       ✅ Уже есть (но проверен)
│   ├── loki/
│   │   └── loki-config.yaml             ← 🆕 Конфиг Loki
│   └── grafana/provisioning/
│       ├── datasources/
│       │   ├── datasources.yml          ← 🆕 Provisioning config
│       │   └── tempo.yml                ← 🆕 Tempo datasource
│       └── dashboards/
│           ├── dashboards.yml           ← 🆕 Provisioning config
│           └── traces.json              ← 🆕 Dashboard
├── otel-setup.sh                         ← 🆕 Скрипт запуска
├── otel-cleanup.sh                       ← 🆕 Скрипт очистки
├── otel-logs.sh                          ← 🆕 Скрипт мониторинга
└── QUICKSTART.md                         ← 🆕 Документация
```

## ⚡ Быстрый старт (10 минут)

### Шаг 1: Сделайте скрипты исполняемыми

```bash
cd /home/penlk/projects/java-itmo
chmod +x otel-setup.sh otel-cleanup.sh otel-logs.sh
```

### Шаг 2: Запустите инициализацию

```bash
./otel-setup.sh
```

Этот скрипт:
- ✅ Поднимет все Docker сервисы
- ✅ Проверит их готовность
- ✅ Соберёт оба приложения

### Шаг 3: Запустите сервисы

**Терминал 1 - Order Service:**
```bash
./gradlew bootRun
```

**Терминал 2 - Storage Service:**
```bash
cd storage && ../gradlew bootRun
```

Приложения нужно запустить в разных терминалах, чтобы видеть логи!

### Шаг 4: Откройте Grafana

http://localhost:3000

- **Логин:** нет (автоматический вход)
- **Пароль:** нет
- Перейти: **Explore** (левое меню) → **Datasource: Tempo** → **Search**

## 📊 Как выглядит трейс?

В Grafana вы увидите такую структуру:

```
🔹 Trace ab12cd34ef56...
   ├─ ⏱️ 1.23s HTTP GET /api/orders (Order Service)
   │  ├─ ⏱️ 45ms Database SELECT (PostgreSQL)
   │  ├─ ⏱️ 567ms gRPC call CheckStorage (Storage Service)
   │  │  └─ ⏱️ 34ms Database SELECT (PostgreSQL Storage)
   │  └─ ⏱️ 123ms Kafka send order.questions
   └─ ⏱️ 89ms Kafka consume (Order Service Event Handler)
      └─ Process approved event
```

**Важно:** Все спаны имеют **одинаковый Trace ID** (ab12cd34ef56...) - это доказывает, что контекст распространяется правильно!

## 🔄 Как работает context propagation

### REST API → gRPC (Order → Storage)

```
Order Service HTTP Request
    ↓
Header: traceparent: 00-trace-id-span-id-01
    ↓
gRPC Stub (автоматически добавляет метаданные)
    ↓
Storage Service получает Trace ID в метаданных
    ↓
Таким же Trace ID продолжается спан
```

### Kafka (Outbox Pattern)

```
Order Service:
  1. Save Order → Outbox table (в одной транзакции)
  2. Scheduled task читает Outbox
  3. KafkaTemplate.send() добавляет traceparent в headers
  
Storage Service Consumer:
  @KafkaListener получает сообщение
  OpenTelemetry автоматически восстанавливает Trace ID
  Спан продолжается с тем же Trace ID
```

## 🧪 Тестирование

### 1. Создать заказ через API

```bash
# Если есть аутентификация
curl -X POST http://localhost:8080/api/orders \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_TOKEN" \
  -d '{"carId": 1, "carPartIds": [1, 2, 3]}'

# Или просто
curl -X GET http://localhost:8080/api/orders \
  -H "Authorization: Bearer YOUR_TOKEN"
```

### 2. Посмотреть трейс в Grafana

1. Откройте http://localhost:3000
2. Перейти в **Explore**
3. Выбрать **Datasource: Tempo**
4. Нажать **Search**
5. Увидеть ваш трейс

### 3. Проверить логи

```bash
# Посмотреть статус всех сервисов
./otel-logs.sh

# Следить за логами в реальном времени
./otel-logs.sh -f

# Логи конкретного сервиса
./otel-logs.sh otel-collector
./otel-logs.sh order (если есть)
```

## 📍 Портируемые URL

| Сервис | URL | Описание |
|--------|-----|---------|
| Order API | http://localhost:8080 | REST API |
| Storage API | http://localhost:8081 | Storage gRPC/HTTP |
| **Grafana** | http://localhost:3000 | **⭐ Просмотр трейсов** |
| Tempo | http://localhost:3200 | Прямой доступ к трейсам |
| OTEL Collector | http://localhost:4318 | OTLP endpoint |
| Prometheus | http://localhost:9090 | Метрики |
| Loki | http://localhost:3100 | Логи |
| Kafka | localhost:9092 | Message broker |

## 🛑 Остановка всего

```bash
# Просто остановить контейнеры (сохранить данные)
./otel-cleanup.sh

# Остановить и удалить volumes
./otel-cleanup.sh -v

# Очистить вообще всё (включая images)
./otel-cleanup.sh -all
```

## ✅ Проверочный список

- [ ] Скрипты сделаны исполняемыми (`chmod +x`)
- [ ] Запущен `./otel-setup.sh`
- [ ] Order Service запущен на порту 8080
- [ ] Storage Service запущен на порту 8081
- [ ] Grafana открывается на http://localhost:3000
- [ ] Tempo видит трейсы (Explore → Tempo → Search)
- [ ] Трейсы показывают полный путь от REST до Kafka

## 🎯 Что видно в Grafana

✅ **Трейсы (Traces)** - каждый запрос со всеми спанами  
✅ **Временные метрики** - как долго выполнялся каждый компонент  
✅ **Service dependencies** - как сервисы вызывают друг друга  
✅ **Ошибки** - если что-то сломалось, сразу видно где  
✅ **Логи** - если добавить Loki datasource  

## 💡 Важные моменты

1. **Нет ручного кода** - context пропагейтится автоматически
2. **Нет ручных хедеров** - traceparent добавляется сам
3. **Ноль конфигурации** - всё через application-otel.properties
4. **100% трейсов** - sampling = 1.0 (только для разработки!)
5. **Все компоненты** - Kafka, gRPC, HTTP, Database, Scheduler

## 🔗 Дополнительная документация

- `QUICKSTART.md` - быстрый старт
- `OPENTELEMETRY_SETUP.md` - подробное руководство
- `OTEL_CHANGES.md` - список всех изменений
- `IMPLEMENTATION_REPORT.md` - полный отчёт о реализации

## ❓ Часто задаваемые вопросы

**Q: Трейсы не видны в Grafana**
A: Проверьте `./otel-logs.sh`, убедитесь что приложения запущены и логируют

**Q: OTEL Collector не получает данные**
A: Проверьте конфигурацию `application-otel.properties`, должна быть ссылка на `http://localhost:4318`

**Q: Контекст теряется в Kafka**
A: Используйте `KafkaTemplate` для отправки, `@KafkaListener` для получения - всё работает автоматически

**Q: Порт уже занят**
A: Используйте `lsof -i :PORT` для поиска процесса и `kill -9 PID` для остановки

---

## 🚀 Готово!

Система полностью настроена. Следуйте инструкциям выше и вы сможете:
- 👀 Видеть полные трейсы запросов
- 📊 Анализировать производительность
- 🔍 Отлаживать проблемы в микросервисах
- 📈 Отслеживать метрики

**Начните с Шага 1: запустите `./otel-setup.sh`**

Good luck! 🎉
