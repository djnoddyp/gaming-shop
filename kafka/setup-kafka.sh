#!/usr/bin/env bash

DIR="kafka_2.12-2.5.0"

# download kafka if not already downloaded
if [[ -d "$DIR" ]]; then
  echo "Kafka installation found, aborting setup."
  exit 1
else
  echo "Kafka not found, installing"
  curl -o kafka.tgz https://downloads.apache.org/kafka/2.5.0/kafka_2.12-2.5.0.tgz
  tar zxf kafka.tgz
  echo "Kafka downloaded"
  cd kafka_2.12-2.5.0 || exit
  echo "Starting Zookeeper"
  nohup ./bin/zookeeper-server-start.sh config/zookeeper.properties &>/dev/null &
  sleep 5
  echo "Starting Kafka"
  nohup ./bin/kafka-server-start.sh config/server.properties &>/dev/null &
  sleep 5
  echo "Configuring server"
  nohup ./bin/kafka-topics.sh --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 \
    --topic gs-inventory &>/dev/null &
  sleep 5
  echo "Kafka up and running"
  rm kafka.tgz
fi
