# SOAP Person Service

A simple SOAP web service for managing person records with CRUD operations.

## Features

- Create, Read, Update, and Delete person records
- SOAP-based web service using JAX-WS
- Built with Java 21 and Maven
- In-memory data storage

## Prerequisites

- Java 21 or later
- Maven 3.6.0 or later

## Getting Started

1. **Build the project**
   ```bash
   mvn clean install
   ```

2. **Run the server**
   ```bash
   mvn exec:java -Dexec.mainClass="org.kgromov.Server"
   ```

3. **Run the client** (in a separate terminal)
   ```bash
   mvn exec:java -Dexec.mainClass="org.kgromov.Client"
   ```

## API Endpoint

- **WSDL**: http://localhost:8080/ws/PersonService?wsdl

## Project Structure

- `src/main/java/org/kgromov/Server.java` - SOAP service publisher
- `src/main/java/org/kgromov/PersonServiceImpl.java` - Service implementation
- `src/main/java/org/kgromov/Client.java` - Example client
- `src/main/java/org/kgromov/ws/server/` - Generated SOAP classes

## Dependencies

- Jakarta XML Web Services (JAX-WS)
- Jakarta XML Binding (JAXB)
- JAX-WS Runtime

