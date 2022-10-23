# Stock Prices Monitor
It's a simple project just for fun using Quarkus with Kafka reactive channels and Spring Boot to provide the prices to the UI.

## Basic Idea
![Idea](/docs/idea.excalidraw.svg)

## About

[Pull API](/pull-api/) will pull stock prices by ticker and publish them to Kafka.

Reat-Time-API will expose the prices in real time using [Server-Sent Events](https://developer.mozilla.org/en-US/docs/Web/API/Server-sent_events/Using_server-sent_events). 

[Company API](/company-api/) a simple CRUD application to manage company + tickers data.

[Client k6](/clientk6/) it's a script to simulate some users requesting the endpoint.

## Running the Dependencies
Inside docker folder:
```bash
cd docker
docker-compose -f common.yml -f kafka.yml -f database.yml up -d
```
After everything is running well it's time to create the Debezium connector running (from the docker folder):
```bash
 curl -i -X POST -H "Accept:application/json" -H "Content-Type:application/json" 127.0.0.1:8083/connectors/ --data "@configs/debezium.json"
```

Then, it's necessary initiate the database structure running the command below inside company-api folder:
```bash
cd company-api
gradle update
```
## Running K6 for Performance Test
[K6 Installation](https://k6.io/docs/getting-started/installation/)

Creating all companies + tickers
```bash
cd clientk6
k6 run create-companies.js
 ```

