# IBMapp

## Overview

IBMapp is a Spring Boot backend application developed for a technical exercise focused on comment to support ticket triage.

The application accepts user comments, analyzes their content using the Hugging Face Inference API, and determines whether the comment represents a real support issue. If an issue is detected, the system automatically creates a support ticket containing structured information such as title, category, priority, and summary.

The goal of the project is to demonstrate a clean backend architecture, API design, external service integration, and structured decision logic supported by the Hugging Face Inference API.

## Main Features

- Submit comments through a REST API
- Store comments in a database
- Analyze comments using the Hugging Face API
- Automatically decide whether a support ticket should be created
- Generate ticket data including title, category, priority, and summary
- Store tickets linked to the original comment
- Retrieve all tickets
- Retrieve a specific ticket by id
- Return proper error responses when a ticket is not found

## Tech Stack

- Java 17
- Spring Boot
- Spring Web
- Spring Data JPA
- H2 in memory database
- Lombok
- Hugging Face Inference API

## Project Structure

The project follows a layered architecture.

api  
REST controllers responsible for handling HTTP requests.

service  
Business logic and workflow orchestration.

domain  
Entities and enums used by the application.

repository  
Spring Data JPA repositories for database access.

integration  
Hugging Face client and AI related models.

config  
Configuration classes.

exception  
Custom exceptions and global error handling.

## How the System Works

1. A user submits a comment using the API.
2. The comment is stored in the database.
3. The text of the comment is sent to the Hugging Face Inference API.
4. The AI returns structured information describing whether a support ticket should be created.
5. If a ticket is required, the system generates a ticket and links it to the original comment.
6. If the comment does not represent a support issue, the comment is stored without creating a ticket.
7. If the AI request fails, the comment is stored with a failed triage state instead of crashing the request.

## Comment States

The comment lifecycle is represented by the following states.

RECEIVED  
UNDER_REVIEW  
NO_TICKET_CREATED  
TICKET_CREATED  
TRIAGE_FAILED

## Ticket Categories

BUG  
FEATURE  
BILLING  
ACCOUNT  
OTHER

## Ticket Priorities

LOW  
MEDIUM  
HIGH

## Prerequisites

Before running the project make sure the following tools are installed.

Java 17  
Maven or Maven Wrapper  
Internet connection for Hugging Face API access

## Hugging Face API Token Configuration

The application communicates with the Hugging Face Inference API.  
For security reasons the API token is not stored in the repository.

The application expects an environment variable named:

HF_API_TOKEN

### PowerShell (Windows)

```
$env:HF_API_TOKEN="your_token_here"
```

### Linux or macOS

```
export HF_API_TOKEN=your_token_here
```

### IntelliJ Run Configuration

Open Run → Edit Configurations and add the environment variable:

```
HF_API_TOKEN=your_token_here
```

The application reads the value through:

```
hf.api.token=${HF_API_TOKEN}
```

## Running the Application

From the project root run:

Windows

```
mvnw.cmd spring-boot:run
```

Linux or macOS

```
./mvnw spring-boot:run
```

The application will start at:

```
http://localhost:8080
```

## H2 Database Console

The H2 console can be accessed at:

```
http://localhost:8080/h2-console
```

Connection settings

JDBC URL: jdbc:h2:mem:ibmapp  
Username: sa  
Password: leave empty

## API Endpoints

### Create Comment

POST /comments

Example request

```json
{
  "authorHandle": "user1",
  "body": "I was charged twice and cannot access my invoice."
}
```

Possible results

If the comment describes a real issue a support ticket will be created.
If the comment is general feedback no ticket will be created.
If the AI analysis fails the comment will be stored with TRIAGE_FAILED state.

### Get All Comments

GET /comments

Returns all stored comments.

### Get All Tickets

GET /tickets

Returns all created tickets.

### Get Ticket by ID

GET /tickets/{ticketId}

Returns a specific ticket.

If the ticket does not exist the API returns HTTP 404.

## Quick API Test

Example request

POST /comments

```json
{
  "authorHandle": "user1",
  "body": "I was charged twice and cannot access my invoice."
}
```

Example response

```json
{
  "id": 1,
  "authorHandle": "user1",
  "body": "I was charged twice and cannot access my invoice.",
  "state": "TICKET_CREATED",
  "linkedTicketId": 1
}
```

Retrieve tickets

GET /tickets

Example response

```json
{
  "id": 1,
  "title": "Duplicate charge and invoice access problem",
  "summary": "User reports being charged twice and being unable to access the invoice.",
  "category": "BILLING",
  "priority": "HIGH"
}
```

Example curl request

```
curl -X POST http://localhost:8080/comments \
-H "Content-Type: application/json" \
-d '{
  "authorHandle": "user1",
  "body": "I was charged twice and cannot access my invoice."
}'
```

## Limitations

The application currently uses an in memory H2 database so data is lost after restart.

AI output quality depends on the external model response.

The project focuses on backend functionality and does not include a frontend interface.

## Possible Future Improvements

Replace H2 with PostgreSQL  
Add automated tests  
Add retry logic for external AI failures  
Add a frontend interface  
Deploy the application to a cloud environment

## Author

Giorgi Tabatadze