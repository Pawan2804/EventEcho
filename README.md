# 📸 EventEcho Backend

EventEcho is a full-stack event media sharing app where guests can upload photos and videos without logging in, and hosts/photographers can securely manage and download event media.

This is the **Spring Boot backend** that handles authentication, event creation, media uploads, and secure access management.

---

## 🚀 Features

- 🔐 JWT-based authentication with role-based access (HOST, PHOTOGRAPHER)
- 📅 Event creation with unique access code
- 📸 Guests upload media anonymously using access code
- 🖼️ Media supports guest name, caption, and upload timestamp
- 📥 Hosts/Photographers can list or download media
- 🧹 Hosts can delete events and clean up linked media
- 📦 ZIP download for all media in an event

---

## ⚙️ Tech Stack

- Java 17
- Spring Boot 3
- Spring Security 6
- PostgreSQL
- JWT (JJWT)
- Lombok
- Multipart upload (for media files)

---

## 🏁 Getting Started

### Prerequisites

- Java 17+
- Maven
- PostgreSQL (running locally or remotely)

### 1. Clone the repo

```bash
git clone https://github.com/your-username/EventEcho.git
cd EventEcho
```

### 2. Configure the database

Update your `application.properties` or `application.yml`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/eventecho
spring.datasource.username=your_pg_username
spring.datasource.password=your_pg_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

### 3. Run the app

```bash
./mvnw spring-boot:run
```

App will start on:  
**http://localhost:8080**

---

## 🔐 Authentication

- **Signup/Login** APIs return JWT tokens
- Use token in header for protected endpoints:

```
Authorization: Bearer <your_token>
```

User roles:
- `HOST`: Can create/delete events and view all media
- `PHOTOGRAPHER`: Can view and download media
- `GUEST`: Upload-only (no login needed)

---

## 📮 API Overview

| Method | Endpoint                            | Access             | Description                      |
|--------|-------------------------------------|--------------------|----------------------------------|
| POST   | `/api/auth/signup`                  | Public             | Create new user (HOST only)     |
| POST   | `/api/auth/login`                   | Public             | Get JWT token                    |
| POST   | `/api/events/create`                | HOST               | Create a new event               |
| GET    | `/api/events/myevents`              | HOST               | List events created by host      |
| DELETE | `/api/events/{accessCode}`          | HOST               | Delete an event and its media    |
| POST   | `/api/media/{accessCode}/upload`    | GUEST              | Upload media (multipart files)   |
| GET    | `/api/media/{accessCode}`           | HOST/PHOTOGRAPHER  | View media for event             |
| GET    | `/api/media/download/{filename}`    | HOST/PHOTOGRAPHER  | Download single file             |
| GET    | `/api/media/zip/{accessCode}`       | HOST               | Download all media as ZIP        |

---

## 📁 File Storage

Uploaded media is saved to a local `uploads/` directory.  
Ensure your application has write permissions in this folder.

---

## 📌 Notes

- Access codes are short unique strings tied to events
- Guests do not need to authenticate
- Roles and permissions enforced via Spring Security
- Ensure uploads folder exists or is auto-created by the app

---

## ✅ To Do / Optional Enhancements

- 🌐 Deploy to cloud (e.g., Railway, Render)
- 📄 Swagger/OpenAPI documentation
- 🧪 Add unit + integration tests
- 📲 Connect to Angular or mobile frontend

---

## 🧑‍💻 Author

Built with ❤️ by Pawan Vijayaraghavan  
Open source under [MIT License](LICENSE)
