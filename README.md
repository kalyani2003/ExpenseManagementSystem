# 💰 Expense Management System

A Spring Boot based REST API application that helps users manage their daily expenses efficiently. The system provides secure authentication using JWT, supports CRUD operations for expenses, integrates MongoDB for data storage, and uses Redis for caching to improve performance.

## 🚀 Features

* User Registration & Login
* JWT Authentication & Authorization
* Create Expense
* View All Expenses
* View Expense by ID
* Update Expense
* Delete Expense
* MongoDB Integration
* Redis Caching
* RESTful APIs
* Secure Endpoints
* Exception Handling
* Layered Architecture

---

## 🛠️ Tech Stack

### Backend

* Java 17+
* Spring Boot
* Spring MVC
* Spring Security
* JWT Authentication

### Database

* MongoDB

### Caching

* Redis

### Build Tool

* Maven / Gradle

### Testing

* Postman

### Version Control

* Git
* GitHub

---

## 📂 Project Structure

```text
src
├── main
│   ├── java
│   │   ├── controller
│   │   ├── service
│   │   ├── repository
│   │   ├── model
│   │   ├── security
│   │   ├── config
│   │   ├── dto
│   │   └── exception
│   │
│   └── resources
│       └── application.properties
│
└── test
```

---

## 🔐 Authentication

The application uses JWT (JSON Web Token) for authentication.

### Authentication Flow

1. User registers.
2. User logs in.
3. JWT token is generated.
4. User sends token in Authorization header.
5. Protected APIs are accessed securely.

Example:

```http
Authorization: Bearer <jwt_token>
```

---

## 📋 API Endpoints

### Authentication APIs

| Method | Endpoint       | Description   |
| ------ | -------------- | ------------- |
| POST   | /auth/register | Register User |
| POST   | /auth/login    | Login User    |

### Expense APIs

| Method | Endpoint       | Description       |
| ------ | -------------- | ----------------- |
| POST   | /expenses      | Create Expense    |
| GET    | /expenses      | Get All Expenses  |
| GET    | /expenses/{id} | Get Expense By ID |
| PUT    | /expenses/{id} | Update Expense    |
| DELETE | /expenses/{id} | Delete Expense    |

---

## ⚙️ Installation & Setup

### Clone Repository

```bash
git clone https://github.com/your-username/expense-management-system.git
```

### Navigate to Project

```bash
cd expense-management-system
```

### Configure MongoDB

Update application.properties:

```properties
spring.data.mongodb.uri=mongodb://localhost:27017/expense_db
```

### Configure Redis

```properties
spring.data.redis.host=localhost
spring.data.redis.port=6379
```

### Build Project

```bash
mvn clean install
```

### Run Application

```bash
mvn spring-boot:run
```

---

## 🧪 Testing APIs

Use Postman to test:

* Register User
* Login User
* Generate JWT Token
* Access Protected Endpoints
* Perform Expense CRUD Operations

---

## ✅ Advantages

* Secure Authentication
* Fast Performance with Redis Cache
* Scalable Architecture
* Easy Maintenance
* RESTful Design
* NoSQL Database Support
* Production Ready Structure

---

## 🔮 Future Enhancements

* Expense Categories
* Monthly Reports
* Budget Tracking
* Email Notifications
* Admin Dashboard
* Role Based Access Control
* Expense Analytics
* Export to PDF/Excel
* Docker Deployment
* Cloud Deployment

---

## 👩‍💻 Developed By

**Kalyani Mhala**

Java Developer

---

## ⭐ Project Highlights

* Spring Boot REST API
* MongoDB Integration
* Redis Caching
* JWT Authentication
* Secure CRUD Operations
* Layered Architecture
* Production Ready Design

## 📜 License

This project is developed for learning and demonstration purposes.
