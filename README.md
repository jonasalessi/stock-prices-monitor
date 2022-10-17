# Stock Prices Monitor
It's a simple project just for fun using Quarkus with Kafka reactive channels and Spring Boot to provide the prices to the UI.

## Basic Idea
![Idea](/docs/idea.excalidraw.svg)

## About

[Pull-API](/pull-api/) will pull stock prices by ticker, persist them in some database and stream them to Kafka.

Reat-Time-API will expose the prices in real time using [Server-Sent Events](https://developer.mozilla.org/en-US/docs/Web/API/Server-sent_events/Using_server-sent_events). 

[Client k4](/client-k4/) it's a script to simule some users requesting the endpoint.

## Running the Dependencies
```bash
 docker-compose -f common.yml -f kafka_cluster.yml up -d
```

## Running K6 for Performance Test
Creating all companies + tickers
```bash
docker run -it --rm -v ${PWD}/clientk6:/scripts grafana/k6 run /scripts/companies.js
 ```