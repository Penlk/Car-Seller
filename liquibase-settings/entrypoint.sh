#!/bin/bash

liquibase update --changelog-file="changelog.yaml" \
    --url "jdbc:postgresql://db:5432/$POSTGRES_DB?sslmode=disable" \
    --username $POSTGRES_USER \
    --password $POSTGRES_PASSWORD