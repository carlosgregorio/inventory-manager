# Architecture

## API
  * OpenAPI

## Backend
  * Java 17
  * Maven 3.8.5
  * Spring boot 3.0.5
    *  Spring Rest
    *  Spring Data with MongoDB
    *  Spring Actuator
    *  MockMvc for integration tests 

## Database
  * MongoDB (local env)
  * CosmosDB with MongoDB API (cloud env)

# Getting Started

# Development

## Source Code
  * https://github.com/carlosgregorio/inventory-manager

## OpenAPi
  * It's possible to check and test the specification on:
    > https://editor.swagger.io/?url=https://inventory-manager-app.azurewebsites.net/api/openapi.yaml
## build backend docker image
  > docker build . -t inventorymanagerregistry.azurecr.io/inventory-manager-back

## push backend docker image
  > docker push inventorymanagerregistry.azurecr.io/inventory-manager-back

It's needed to login to the registry first
  > docker login -u «user» -p «password» inventorymanagerregistry.azurecr.io

## Run locally
  > docker-compose up

This command will build and start a backend and a containerized mongoDB instance
The application will be available on http://localhost:8080/api

## Run on the cloud
The application is currently deployed on Azure and available on https://inventory-manager-app.azurewebsites.net/api
It's possible to test the api directly by using the swagger editor https://editor.swagger.io/?url=https://inventory-manager-app.azurewebsites.net/api/openapi.yaml
