#!/bin/bash

# Установите параметры подключения к базе данных
HOST="localhost"
PORT="5432"
USER="postgres"
PASSWORD="postgres"
DATABASE="postgres"

# Устанавливаем пароль для пользователя
export PGPASSWORD=$PASSWORD

# Выполняем команду для создания схем
psql -h $HOST -p $PORT -U $USER -d $DATABASE -c "CREATE SCHEMA IF NOT EXISTS app;"
psql -h $HOST -p $PORT -U $USER -d $DATABASE -c "CREATE SCHEMA IF NOT EXISTS liquibase;"

# Очистка переменной окружения
unset PGPASSWORD

echo "Schemas 'app' and 'liquibase' have been created."

mvn luqibase:update

echo "liquibase has been updated."