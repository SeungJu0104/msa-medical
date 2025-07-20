#!/bin/bash

mkdir -p "$(dirname "$LOG_FILE")"

cd /home/ubuntu/sh

set -a
. /home/ubuntu/sh/env/.runAutoProcedure.env
set +a

echo "[$(date)] ▶ 프로시저 실행 시작" >> "$LOG_FILE"

docker cp $EC2_CNF_FILE "$CONTAINER:$DOCKER_CNF_FILE"

docker exec "$CONTAINER" sh -c "mariadb --defaults-extra-file=$DOCKER_CNF_FILE $DB_NAME < $SQL_FILE_IN_CONTAINER" >> "$LOG_FILE" 2>&1

if [ $? -eq 0 ]; then
  echo "[$(date)] ✅ 실행 성공" >> "$LOG_FILE"
else
  echo "[$(date)] ❌ 실행 실패" >> "$LOG_FILE"
fi