# 🏥 Healthcare Claim Management System – Backend

A secure and scalable backend system built using **Spring Boot**, designed to manage the complete lifecycle of healthcare claim processing. The system supports **JWT authentication**, **role-based access**, modular APIs, and fully interactive **Swagger documentation**, with **PostgreSQL** as the primary database.

---

## 🚀 Features

### 🔐 Authentication & Authorization
- JWT-based secure login
- Role-based access: **Admin**, **Coder**, **Reviewer** **Auditor**
- Password encryption using BCrypt

### 📄 Claim Management
- Create and submit claims
- Update claim status
- Retrieve claim details
- Filter/search claims by status, user, and date

### 👥 User Management
- Register users with roles
- View and manage users (Admin-only)
- Token refresh support
- Enable/disable users

### 📊 Reporting
- Generate claim audit reports
- Productivity reporting
- Date and user-based filtering

### 📘 API Documentation
- Swagger UI integration
- JWT Bearer authentication support in Swagger
- Organized API grouping and schema documentation

### 🗄 Database
- PostgreSQL with Hibernate/JPA
- Auto entity-to-table mapping
- Relationship mapping for users, roles, and claims

---

## 🛠 Tech Stack

| Layer | Technology |
|-------|-------------|
| Backend | Spring Boot |
| Security | Spring Security + JWT |
| Database | PostgreSQL |
| ORM | Hibernate/JPA |
| API Docs | Swagger / SpringDoc |
| Build Tool | Maven |
| Language | Java 11 |

---

## ▶️ How to Run Locally

### 1️⃣ Clone the Repository
```bash
git clone https://github.com/Anmol17Agarwal/HealthCare-Claim-Management-System.git
cd HealthCare-Claim-Management-System
```

## Setup PostgreSQL Database
```bash
CREATE DATABASE healthcare_claim_db;
```

## Build & Run
```bash
./mvnw clean install
./mvnw spring-boot:run
```

## Future Enhancement
- React frontend dashboard (in progress)
- File upload (PDF/CSV claims)
- AWS S3 integration
- Redis caching
- Email notifications
- Docker deployment

