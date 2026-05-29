#!/bin/bash

# Check OpenTelemetry stack status and logs

echo "🔍 OpenTelemetry Stack Status"
echo "================================"
echo ""

# Check Docker containers
echo "📦 Docker Containers:"
docker-compose ps

echo ""
echo "📋 Service Logs:"
echo ""

# Function to show logs with a header
show_logs() {
    local service=$1
    local lines=${2:-50}
    echo "=== $service ===" 
    docker-compose logs --tail=$lines $service 2>/dev/null | tail -$lines
    echo ""
}

# Check if we want to follow logs
if [ "$1" = "-f" ] || [ "$1" = "--follow" ]; then
    echo "Following logs (press Ctrl+C to stop)..."
    docker-compose logs -f
else
    # Show recent logs
    if [ "$1" != "" ]; then
        # Show logs for specific service
        show_logs "$1" 100
    else
        # Show logs for main services
        show_logs "otel-collector" 20
        show_logs "tempo" 20
        show_logs "grafana" 20
    fi
fi

echo ""
echo "💡 Tips:"
echo "  ./otel-logs.sh -f          (follow logs in real-time)"
echo "  ./otel-logs.sh otel-collector  (show otel-collector logs)"
echo "  ./otel-logs.sh kafka       (show kafka logs)"
echo ""

# Check connectivity
echo "🔌 Connectivity Check:"
echo ""

services=(
    "localhost:8080:Order Service"
    "localhost:8081:Storage Service"
    "localhost:4318:OTEL Collector"
    "localhost:3200:Tempo"
    "localhost:3000:Grafana"
    "localhost:9090:Prometheus"
    "localhost:3100:Loki"
    "localhost:9092:Kafka"
)

for service in "${services[@]}"; do
    IFS=':' read -r host port name <<< "$service"
    if nc -z $host $port 2>/dev/null; then
        echo "  ✓ $name ($host:$port)"
    else
        echo "  ✗ $name ($host:$port) - NOT RESPONDING"
    fi
done

echo ""
