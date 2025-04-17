Quiz Application

This is a Spring Boot application that provides a simple quiz system. It includes:
- A REST API to manage questions and answers.
- A MySQL database to store quiz data.
- A Podman Compose setup to run the application and database in containers.

---

Features

- Quiz Management: Create, read, update, and delete questions and answers.
- Database Integration: Uses MySQL for persistent data storage.
- Containerized Setup: Easily deployable using Podman Compose.

---

Requirements

- Java: JDK 17 or higher.
- Maven: For building the Spring Boot application.
- Podman: For running containers.
- Podman Compose: For managing multi-container setups.

---

Setup Instructions

1. Start the Podman service:

```bash
podman-compose up
```