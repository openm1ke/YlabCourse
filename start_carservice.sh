#!/usr/bin/env bash

./docker_stop.sh
./docker_run.sh
mvn liquibase:update
mvn jetty:run