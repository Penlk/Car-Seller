#!/bin/bash

# Stop and cleanup OpenTelemetry stack

echo "🛑 Stopping OpenTelemetry stack..."

docker-compose down

if [ "$1" = "-v" ] || [ "$1" = "--volumes" ]; then
    echo "🗑️  Removing volumes..."
    docker volume rm java-itmo_data java-itmo_keycloak-data java-itmo_kafka-logs java-itmo_storage-data java-itmo_tempo-data java-itmo_loki-data 2>/dev/null
    echo "✓ Volumes removed"
fi

if [ "$1" = "-all" ]; then
    echo "🗑️  Removing all Docker containers and images..."
    docker-compose down -v
    docker system prune -f
fi

echo "✓ Cleanup complete"
