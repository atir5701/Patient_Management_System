# Patient Management System

A microservices-based patient management system built with Spring Boot, featuring JWT authentication, gRPC communication, Kafka event streaming, and Docker containerization.

## Architecture

```
Client
  └── API Gateway (port 4004)
        ├── /auth/**        → Auth Service (port 4005)
        └── /api/patients/** → Patient Service (port 4000)
                                ├── gRPC → Billing Service (port 9001)
                                └── Kafka → Analytics Service
```

## Services

| Service | Port | Description |
|---|---|---|
| API Gateway | 4004 | Routes requests, validates JWT tokens |
| Patient Service | 4000 | CRUD operations for patients |
| Billing Service | 4001 / gRPC 9001 | Handles billing account creation via gRPC |
| Analytics Service | 4002 | Consumes patient events from Kafka |
| Auth Service | 4005 | JWT authentication and token validation |

## Tech Stack

- **Java 21** + **Spring Boot 4.x**
- **Spring Cloud Gateway** — API gateway with JWT filter
- **Spring Data JPA** + **PostgreSQL** — data persistence
- **gRPC** — communication between patient-service and billing-service
- **Apache Kafka** — event streaming from patient-service to analytics-service
- **JWT (jjwt)** — authentication
- **Docker** — containerization
- **Hibernate** — ORM

## Prerequisites

- Docker Desktop
- Java 21+
- Maven 3.9+

## Getting Started

### 1. Create Docker Network

```bash
docker network create pms-network
```

### 2. Start Infrastructure

**PostgreSQL (Patient Service):**
```bash
docker run -d \
  --name patient-service-db \
  --network pms-network \
  -e POSTGRES_USER=admin_user \
  -e POSTGRES_PASSWORD=password \
  -e POSTGRES_DB=db \
  -p 5432:5432 \
  postgres:16
```

**PostgreSQL (Auth Service):**
```bash
docker run -d \
  --name auth-service-db \
  --network pms-network \
  -e POSTGRES_USER=admin_user \
  -e POSTGRES_PASSWORD=password \
  -e POSTGRES_DB=db \
  -p 5433:5432 \
  postgres:16
```

**Kafka:**
```bash
docker run -d \
  --name kafka \
  --network pms-network \
  -p 9092:9092 \
  apache/kafka:latest
```

### 3. Build and Run Services

Each service has a `Dockerfile`. Build and run with:

```bash
docker build -t <service-name>:latest .
docker run -d --name <service-name> --network pms-network <env-vars> <service-name>:latest
```

#### Patient Service
```bash
docker run -d --name patient-service \
  --network pms-network \
  -e SPRING_DATASOURCE_URL=jdbc:postgresql://patient-service-db:5432/db \
  -e SPRING_DATASOURCE_USERNAME=admin_user \
  -e SPRING_DATASOURCE_PASSWORD=password \
  -e SPRING_JPA_HIBERNATE_DDL_AUTO=update \
  -e SPRING_KAFKA_BOOTSTRAP_SERVERS=kafka:9092 \
  -e BILLING_SERVICE_ADDRESS=billing-service \
  -e BILLING_SERVICE_GRPC_PORT=9001 \
  -p 4000:4000 \
  patient-service:latest
```

#### Auth Service
```bash
docker run -d --name auth-service \
  --network pms-network \
  -e SPRING_DATASOURCE_URL=jdbc:postgresql://auth-service-db:5432/db \
  -e SPRING_DATASOURCE_USERNAME=admin_user \
  -e SPRING_DATASOURCE_PASSWORD=password \
  -e SPRING_JPA_HIBERNATE_DDL_AUTO=update \
  -e JWT_SECRET=<your-base64-encoded-secret> \
  -p 4005:4005 \
  auth-service:latest
```

#### API Gateway
```bash
docker run -d --name gateway \
  --network pms-network \
  -p 4004:4004 \
  gateway:latest
```

## API Endpoints

### Authentication

| Method | Endpoint | Description | Auth Required |
|---|---|---|---|
| POST | `/auth/login` | Login and get JWT token | No |
| GET | `/auth/validate` | Validate JWT token | No |

**Login Request:**
```json
{
  "email": "testuser@test.com",
  "password": "password"
}
```

**Login Response:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9..."
}
```

### Patients

All patient endpoints require `Authorization: Bearer <token>` header.

| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/patients` | Get all patients |
| POST | `/api/patients` | Create a patient |
| PUT | `/api/patients/{id}` | Update a patient |
| DELETE | `/api/patients/{id}` | Delete a patient |

**Create Patient Request:**
```json
{
  "name": "John Doe",
  "email": "john.doe@example.com",
  "dateOfBirth": "1990-05-15",
  "registerDate": "2026-05-14"
}
```

## Authentication Flow

```
1. POST /auth/login → receive JWT token
2. Include token in all subsequent requests:
   Authorization: Bearer <token>
3. API Gateway validates token with auth-service before forwarding
```

## Inter-Service Communication

- **Patient → Billing**: gRPC call to create billing account when a patient is registered
- **Patient → Analytics**: Kafka event published to `patient` topic on patient creation

## Project Structure

```
pms/
├── patient-service/      # Patient CRUD, Kafka producer, gRPC client
├── billing-service/      # gRPC server for billing
├── analytics-service/    # Kafka consumer
├── auth-service/         # JWT auth
└── gateway/              # API Gateway with JWT filter
```

## Default Test User

The auth-service seeds a default user on startup:

| Field | Value |
|---|---|
| Email | testuser@test.com |
| Role | ADMIN |
