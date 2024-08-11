#!/bin/bash

# Установите параметры подключения к базе данных
HOST="localhost"
PORT="5432"
USER="postgres"
PASSWORD="postgres"
DATABASE="postgres"

# Устанавливаем пароль для пользователя
export PGPASSWORD=$PASSWORD

# Выполняем команду для удаления схем
psql -h $HOST -p $PORT -U $USER -d $DATABASE -c "DROP SCHEMA IF EXISTS app CASCADE;"
psql -h $HOST -p $PORT -U $USER -d $DATABASE -c "DROP SCHEMA IF EXISTS liquibase CASCADE;"

# Очистка переменной окружения
unset PGPASSWORD

echo "Schemas 'app' and 'liquibase' have been dropped."
