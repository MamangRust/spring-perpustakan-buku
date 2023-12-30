#!/bin/bash

# Menunggu layanan Kafka siap
sleep 30

# Eksekusi perintah setelah layanan Kafka siap
kafka-topics --create --bootstrap-server kafka:9092 --replication-factor 1 --partitions 1 --topic my_perpustakaan && 
echo "Perintah setelah pembuatan topik Kafka"
