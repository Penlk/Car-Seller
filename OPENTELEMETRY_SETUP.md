# OpenTelemetry Setup Guide

Этот проект использует OpenTelemetry для распределённого трейсинга (distributed tracing) между микросервисами.

## 📋 Архитектура

```
Order Service (REST API)
    ↓ gRPC
Storage Service
    ↓ Kafka
Order Service (Event Handler)
```

Каждый запрос получает **trace ID**, который распространяется:
- Через HTTP заголовки (W3C Trace Context)
- Через Kafka сообщения (контекст в message headers)
- Через gRPC метаданные

## 🚀 Запуск

### 1. Запустить Docker Compose со всеми сервисами

```bash
docker-compose up -d
```

Будут запущены:
- **Order Service** (port 8080) - основной API
- **Storage Service** (port 8081) - хранилище товаров (gRPC + Kafka)
- **PostgreSQL** базы (2 экземпляра)
- **Kafka** (port 9092)
- **OpenTelemetry Collector** (port 4317/4318)
- **Tempo** (port 3200) - хранилище трейсов
- **Grafana** (port 3000) - визуализация
- **Prometheus** (port 9090) - метрики
- **Loki** (port 3100) - логи

### 2. Собрать приложения

```bash
./gradlew build -x test
cd storage && ../gradlew build -x test && cd ..
```

### 3. Запустить сервисы

Терминал 1 - Order Service:
```bash
./gradlew bootRun
```

Терминал 2 - Storage Service:
```bash
cd storage && ../gradlew bootRun
```

## 📊 Просмотр трейсов

### Через Grafana (рекомендуется)

1. Откройте http://localhost:3000
2. Перейдите на **Explore**
3. Выберите datasource **Tempo**
4. Поищите трейсы по сервисам или trace ID

### Через Tempo напрямую

http://localhost:3200

## 🔍 Как трейсинг работает

### Пример: Создание заказа

```
1. POST /api/orders (Order Service)
   └─ Trace ID: abc123
   │  Parent Span: handle-http-request
   │
   └─ gRPC Call → Storage Service
      └─ Trace ID: abc123 (унаследован!)
      │  Span: check-availability
      │  
      └─ Kafka Event → Order Service
         └─ Trace ID: abc123 (унаследован!)
            Span: process-approved-event
```

### Что отслеживается автоматически:

✅ HTTP запросы (REST)  
✅ gRPC вызовы  
✅ Kafka сообщения  
✅ Базы данных (JDBC)  
✅ Spring Component методы  

### Без необходимости в ручном коде!

Context propagation работает **из под коробки** при использовании:
- `RestTemplate`
- `WebClient`
- `gRPC stubs`
- `KafkaTemplate`
- `@KafkaListener`

## 🔧 Конфигурация

### application-otel.properties

Содержит все настройки OpenTelemetry:

```properties
# Endpoint куда экспортируются трейсы
otel.exporter.otlp.endpoint=http://localhost:4318

# Какие компоненты инструментировать
otel.instrumentation.kafka.enabled=true
otel.instrumentation.grpc.enabled=true
otel.instrumentation.spring-web.enabled=true

# Context propagation типы
otel.propagators=tracecontext,jaeger

# Sampling 
otel.traces.sampler=always_on
```

## 📈 Метрики

Prometheus собирает метрики на http://localhost:9090

Полезные запросы:
```promql
# Количество спанов в минуту
rate(span_counter_total[1m])

# P95 latency
histogram_quantile(0.95, rate(span_duration_bucket[1m]))
```

## 🐛 Troubleshooting

### Трейсы не появляются в Grafana

1. Проверьте, что сервисы запущены и logLevel DEBUG:
```bash
docker logs <container> | grep -i otel
```

2. Проверьте, что OTLP Collector получает данные:
```bash
curl http://localhost:4318/v1/traces
```

3. Проверьте конфигурацию в application-otel.properties

### Контекст теряется при Kafka

Если контекст не распространяется через Kafka:
- Убедитесь, что используется `KafkaTemplate` (не `@KafkaListener` с ручной отправкой)
- Проверьте, что инструментация Kafka включена: `otel.instrumentation.kafka.enabled=true`

### Контекст теряется при gRPC

Если контекст не распространяется через gRPC:
- Убедитесь, что используется автоматически сгенерированные gRPC stubs
- Проверьте, что инструментация gRPC включена

## 📝 Примеры использования в коде

### Получить текущий trace ID

```java
@Autowired
private Tracer tracer;

public void myMethod() {
    Span span = tracer.spanBuilder("my-operation").startSpan();
    try (Scope scope = span.makeCurrent()) {
        // Ваш код
    } finally {
        span.end();
    }
}
```

### Добавить custom атрибут к спану

```java
Span span = Span.current();
span.setAttribute("user.id", userId);
span.setAttribute("order.id", orderId);
```

## 🔗 Полезные ссылки

- [OpenTelemetry Java документация](https://opentelemetry.io/docs/instrumentation/java/)
- [Spring Boot OpenTelemetry](https://docs.spring.io/spring-boot/reference/actuate/observability.html)
- [Grafana Tempo](https://grafana.com/docs/tempo/latest/)
- [W3C Trace Context](https://www.w3.org/TR/trace-context/)

## ✅ Checklist перед сдачей

- [ ] Все сервисы имеют OpenTelemetry зависимости
- [ ] application-otel.properties активирован
- [ ] Traces видны в Grafana Tempo
- [ ] Context ID сохраняется в цепочке REST → gRPC → Kafka
- [ ] Нет ручного выставления traceparent хедеров (работает автоматически)
- [ ] docker-compose поднимает все нужные сервисы
- [ ] Grafana connectified к Tempo
