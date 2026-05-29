# OpenTelemetry Implementation Summary

## 📝 Выполненные изменения

### 1. Зависимости (build.gradle)

**Добавлены в оба сервиса:**
```gradle
implementation 'org.springframework.boot:spring-boot-starter-opentelemetry'
implementation platform('io.opentelemetry.instrumentation:opentelemetry-instrumentation-bom:2.4.0')
implementation 'io.opentelemetry.exporter:opentelemetry-exporter-otlp'
implementation 'io.opentelemetry.instrumentation:opentelemetry-spring-boot-starter'
implementation 'io.opentelemetry:opentelemetry-extension-trace-propagators'
```

**Зачем:**
- `spring-boot-starter-opentelemetry` - интеграция с Spring Boot
- `opentelemetry-instrumentation-bom` - BOM для управления версиями инструментов
- `opentelemetry-exporter-otlp` - экспорт трейсов по OTLP протоколу
- `opentelemetry-spring-boot-starter` - автоматическая инструментация всех компонентов (Kafka, gRPC, HTTP, DB)
- `opentelemetry-extension-trace-propagators` - поддержка W3C Trace Context и Jaeger propagators

### 2. Конфигурация

#### application.yml (оба сервиса)
- Добавлен профиль `spring.profiles.active: otel`
- Удалены старые конфиги OpenTelemetry (переместили в свойства)

#### application-otel.properties (новые файлы)

**Order Service:**
`src/main/resources/application-otel.properties`

**Storage Service:**
`storage/src/main/resources/application-otel.properties`

**Содержит:**
```properties
otel.instrumentation.common.default-enabled=true
otel.propagators=tracecontext,jaeger
otel.metrics.exporter=otlp
otel.traces.exporter=otlp
otel.exporter.otlp.protocol=http/protobuf
otel.instrumentation.kafka.enabled=true
otel.instrumentation.grpc.enabled=true
otel.resource.attributes=service.name=order,service.version=1.0,deployment.environment=development
otel.traces.sampler=always_on
```

**Зачем:**
- Включает автоматическую инструментацию для Kafka, gRPC, HTTP
- Настраивает W3C Trace Context propagation (работает везде автоматически)
- Сэмплирует 100% трейсов (для разработки)

### 3. OpenTelemetry конфигурационный класс

#### Order Service:
`src/main/java/ru/penlk/OpenTelemetryConfig.java`

#### Storage Service:
`storage/src/main/java/penlk/OpenTelemetryConfig.java`

**Содержит:**
```java
@Configuration
public class OpenTelemetryConfig {
    @Bean
    public Tracer tracer() {
        return GlobalOpenTelemetry.getTracer("service-name");
    }
}
```

**Зачем:**
- Предоставляет `Tracer` bean для использования в коде (опционально)
- Инициализирует глобальный OpenTelemetry

### 4. Docker-Compose обновления

**Добавлены сервисы:**
- **otel-collector** - собирает трейсы с приложений и отправляет в Tempo
- **tempo** - хранилище трейсов
- **grafana** - визуализация (с конфигурацией для Tempo, Prometheus, Loki)
- **loki** - сервис логирования (опционально, для корреляции логов и трейсов)

**Обновлено:**
- Grafana теперь использует provisioning для автоматической конфигурации datasources

### 5. Grafana конфигурация

#### traces/grafana/provisioning/datasources/
- **datasources.yml** - конфиг provisioning datasources
- **tempo.yml** - настройки Tempo datasource с поддержкой:
  - Trace search
  - Node graph visualization
  - Prometheus exemplars
  - Loki log correlation

#### traces/grafana/provisioning/dashboards/
- **dashboards.yml** - конфиг provisioning dashboards
- **traces.json** - готовый dashboard для просмотра трейсов

### 6. Loki конфигурация

**traces/loki/loki-config.yaml** - конфиг Loki для логирования

## 🔄 Как работает context propagation

### HTTP (REST API)
```
Client → Order Service
  Header: traceparent: 00-trace-id-span-id-01
         (автоматически добавляется Spring)
```

### gRPC
```
Order Service → Storage Service
  Metadata: grpc-trace-bin: <binary trace context>
           (автоматически propagated)
```

### Kafka
```
Order Service → Kafka Topic
  Message Headers: traceparent: 00-trace-id-span-id-01
                  (автоматически добавляется instrumentation)

Kafka Consumer → Storage Service
  (контекст автоматически восстанавливается)
```

## ✨ Ключевые особенности

✅ **Нет ручного кода** - context propagation работает автоматически  
✅ **Zero-config tracing** - всё настроено через application.properties  
✅ **Out-of-the-box instrumentation** - Kafka, gRPC, HTTP, JDBC, Spring компоненты  
✅ **W3C Trace Context** - стандартный формат для распространения контекста  
✅ **Visualization** - Grafana + Tempo для просмотра трейсов  

## 🧪 Тестирование

1. **Запустить docker-compose:**
   ```bash
   docker-compose up -d
   ```

2. **Запустить сервисы:**
   ```bash
   ./gradlew bootRun
   cd storage && ../gradlew bootRun
   ```

3. **Создать заказ (REST API):**
   ```bash
   curl -X POST http://localhost:8080/api/orders -H "Content-Type: application/json" -d '...'
   ```

4. **Открыть Grafana:**
   http://localhost:3000

5. **Найти трейс в Tempo:**
   - Выбрать datasource: Tempo
   - Поискать по сервису или trace ID
   - Увидеть полную цепочку вызовов

## 📊 Ожидаемый результат

В Grafana должна быть видна **полная цепочка трейса:**

```
HTTP Request (Order Service)
  └─ gRPC Call (Storage Service)
  └─ Kafka Message (Order Service Event Handler)
      └─ HTTP Response
```

Все спаны должны иметь **один и тот же Trace ID** - это доказывает, что контекст распространяется корректно.

## 🔗 Документация

Подробное руководство: [OPENTELEMETRY_SETUP.md](./OPENTELEMETRY_SETUP.md)
