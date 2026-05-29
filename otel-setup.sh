#!/bin/bash

# OpenTelemetry Setup Script

echo "🚀 Starting OpenTelemetry Stack..."

# Colors for output
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
RED='\033[0;31m'
NC='\033[0m' # No Color

# Function to check if service is ready
check_service() {
    local service=$1
    local port=$2
    local retries=30
    
    for i in $(seq 1 $retries); do
        if nc -z localhost $port 2>/dev/null; then
            echo -e "${GREEN}✓${NC} $service is ready (port $port)"
            return 0
        fi
        echo -n "."
        sleep 1
    done
    
    echo -e "${RED}✗${NC} $service is not ready after 30 seconds"
    return 1
}

# Start docker-compose
echo "📦 Starting Docker Compose services..."
docker-compose up -d

# Wait for services
echo ""
echo "⏳ Waiting for services to be ready..."
echo ""

check_service "PostgreSQL" 5432 || true
check_service "Kafka" 9092 || true
check_service "Tempo" 3200 || true
check_service "Grafana" 3000 || true
check_service "OTEL Collector" 4318 || true
check_service "Prometheus" 9090 || true
check_service "Loki" 3100 || true

echo ""
echo -e "${GREEN}✓ All Docker services are ready!${NC}"
echo ""

# Build applications
echo "🔨 Building applications..."
./gradlew build -x test --no-daemon > /dev/null 2>&1

if [ $? -eq 0 ]; then
    echo -e "${GREEN}✓ Order Service built successfully${NC}"
else
    echo -e "${RED}✗ Failed to build Order Service${NC}"
    exit 1
fi

cd storage && ../gradlew build -x test --no-daemon > /dev/null 2>&1

if [ $? -eq 0 ]; then
    echo -e "${GREEN}✓ Storage Service built successfully${NC}"
else
    echo -e "${RED}✗ Failed to build Storage Service${NC}"
    exit 1
fi

cd ..

echo ""
echo -e "${YELLOW}📋 Services are ready:${NC}"
echo ""
echo "🌐 Application Endpoints:"
echo "  - Order Service:      http://localhost:8080"
echo "  - Storage Service:    http://localhost:8081"
echo "  - Swagger (Order):    http://localhost:8080/swagger-ui.html"
echo ""
echo "📊 Observability Stack:"
echo "  - Grafana:            http://localhost:3000"
echo "  - Prometheus:         http://localhost:9090"
echo "  - Tempo:              http://localhost:3200"
echo "  - Loki:               http://localhost:3100"
echo "  - OTEL Collector:     http://localhost:4318"
echo ""
echo -e "${YELLOW}🚀 To start the services:${NC}"
echo ""
echo "Terminal 1 (Order Service):"
echo "  ./gradlew bootRun"
echo ""
echo "Terminal 2 (Storage Service):"
echo "  cd storage && ../gradlew bootRun"
echo ""
echo -e "${YELLOW}✨ To view traces in Grafana:${NC}"
echo "  1. Open http://localhost:3000"
echo "  2. Click 'Explore'"
echo "  3. Select datasource 'Tempo'"
echo "  4. Search for traces"
echo ""
