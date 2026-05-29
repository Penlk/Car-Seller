# OpenTelemetry Distributed Tracing Setup

## 🎯 Что было сделано

Система полностью настроена для **distributed tracing** через OpenTelemetry. Все микросервисы (Order, Storage) автоматически отправляют трейсы в Tempo, где их можно просматривать через Grafana.

## ⚡ Быстрый старт (5 минут)

### 1️⃣ Запустить всё сразу (рекомендуется)

```bash
# Сделать скрипты исполняемыми
chmod +x otel-setup.sh otel-cleanup.sh otel-logs.sh

# Запустить инициализацию (поднимает Docker, собирает приложения)
./otel-setup.sh
```

### 2️⃣ В отдельных терминалах запустить сервисы

**Терминал 1 - Order Service:**
```bash
./gradlew bootRun
```

**Терминал 2 - Storage Service:**
```bash
cd storage && ../gradlew bootRun
```

### 3️⃣ Открыть Grafana и смотреть трейсы

```
http://localhost:3000
```

- Выбрать **Explore** в левом меню
- Datasource: **Tempo** 
- Нажать **Search** (или поискать по service name)

## 📊 Полный стек сервисов

| Сервис | Порт | Описание |
|--------|------|---------|
| **Order Service** | 8080 | REST API заказов |
| **Storage Service** | 8081 | gRPC + Kafka сервис хранилища |
| **Grafana** | 3000 | Визуализация трейсов ⭐ |
| **Tempo** | 3200 | Хранилище трейсов |
| **OTEL Collector** | 4318 | Сбор трейсов из приложений |
| **Prometheus** | 9090 | Метрики |
| **Loki** | 3100 | Логи |
| **Kafka** | 9092 | Message broker |
| **PostgreSQL** | 5432 | БД (2 экземпляра) |

## 🔄 Как работает tracing

```
┌─────────────────────────────────────────────────────────┐
│                                                         │
│  1. GET /api/orders (REST)                              │
│     └─ Trace ID: abc123def456                           │
│        └─ Header: traceparent: 00-abc123def456...       │
│                                                         │
│  2. gRPC Call → Storage Service (Trace ID сохраняется!) │
│     └─ Metadata: grpc-trace-bin                         │
│                                                         │
│  3. Kafka Message (Trace ID в headers!)                 │
│     └─ Kafka Consumer процесс                           │
│                                                         │
│  Все спаны объединены в ОДИН TRACE → видно в Grafana   │
│                                                         │
└─────────────────────────────────────────────────────────┘
```

## ✨ Ключевые моменты

✅ **Zero-config context propagation** - работает автоматически везде
✅ **W3C Trace Context standard** - совместимо с другими инструментами
✅ **Автоматическая инструментация** - все компоненты без ручного кода
✅ **Grafana UI** - удобная визуализация трейсов и метрик

## 🛠️ Полезные команды

```bash
# Проверить статус всех сервисов
./otel-logs.sh

# Следить за логами в реальном времени
./otel-logs.sh -f

# Логи конкретного сервиса
./otel-logs.sh otel-collector

# Остановить всё
./otel-cleanup.sh

# Остановить и очистить volumes
./otel-cleanup.sh -v

# Остановить и очистить всё (включая images)
./otel-cleanup.sh -all
```

## 📝 Тестирование

Создать тестовый заказ:

```bash
curl -X POST http://localhost:8080/api/orders \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <token>" \
  -d '{
    "carId": 1,
    "carPartIds": [1, 2, 3]
  }'
```

Затем в Grafana увидите полный трейс с временами выполнения каждого компонента.

## 📖 Документация

- **[OPENTELEMETRY_SETUP.md](./OPENTELEMETRY_SETUP.md)** - подробное руководство
- **[OTEL_CHANGES.md](./OTEL_CHANGES.md)** - список всех изменений

## 🔍 Troubleshooting

### Трейсы не видны в Grafana

1. Проверьте, что приложения запущены:
   ```bash
   ./otel-logs.sh
   ```

2. Проверьте OTEL Collector получает данные:
   ```bash
   docker-compose logs -f otel-collector
   ```

3. Проверьте, что Tempo получает трейсы:
   ```bash
   docker-compose logs -f tempo
   ```

### Контекст теряется в Kafka

Убедитесь, что используется `KafkaTemplate` для отправки и `@KafkaListener` для получения - контекст пропагейтится автоматически.

### Port занят

Если порт уже занят:
```bash
# Найти процесс на порту (например 3000)
lsof -i :3000

# Убить процесс
kill -9 <PID>
```

## ✅ Готово!

Система полностью настроена и готова к использованию. Просто следите за трейсами в Grafana и видите, как запросы проходят через все микросервисы с автоматическим сохранением контекста.

---

**Вопросы?** Смотрите подробную документацию в [OPENTELEMETRY_SETUP.md](./OPENTELEMETRY_SETUP.md)
