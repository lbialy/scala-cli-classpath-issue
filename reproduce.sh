#!/bin/bash

echo "Packaging project with scala-cli! Variant: $1 acceptable arguments: <empty>, \"--standalone\", \"--assembly\""

scala-cli package . --main-class 'Reproducer' "$1" -o runner -f

echo "Setting up postgres container!"

docker run \
  --name postgres \
  -e POSTGRES_DB=postgres \
  -e POSTGRES_USER=postgres \
  -e POSTGRES_PASSWORD=postgres \
  -p 5432:5432 \
  -d postgres

echo "Waiting for postgres to start, 5s"
sleep 5

echo "Starting the packaged reproducer:"

./runner

echo ""
echo "Look for <No migrations found. Are your locations set up correctly?> in logs above. If it's present Flyway did not find migrations on classpath!"

echo "Cleaning up postgres..."
docker kill postgres > /dev/null
docker rm postgres > /dev/null
echo "Done."
