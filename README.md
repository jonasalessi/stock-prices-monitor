# Stock Prices Monitor
It's a simple project just for fun using Quarkus with Kafka reactive channels and Spring Boot to provide the prices to the UI.

## Basic Idea
![Idea](/docs/idea.excalidraw.svg)

## About

[Pull API](/pull-api/) will pull stock prices by ticker and publish them to Kafka.

Reat-Time-API will expose the prices in real time using [Server-Sent Events](https://developer.mozilla.org/en-US/docs/Web/API/Server-sent_events/Using_server-sent_events). 

[Company API](/company-api/) a simple CRUD application to manage company + tickers data.

[Client k4](/client-k4/) it's a script to simulate some users requesting the endpoint.

## Running the Dependencies
```bash
 cd docker
 docker-compose -f common.yml -f kafka_cluster.yml -f database.yml up -d
```
Then, after everything is running well it's time to create the Debezium connector running (from the docker folder):
```bash
 curl -i -X POST -H "Accept:application/json" -H "Content-Type:application/json" 127.0.0.1:8083/connectors/ --data "@configs/debezium.json"
```

## Running K6 for Performance Test
Creating all companies + tickers
```bash
docker run -it --rm -v ${PWD}/clientk6:/scripts grafana/k6 run /scripts/create-companies.js
 ```

