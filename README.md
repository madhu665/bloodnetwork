# 🩸 Blood Response Network

> **A Real-World Full-Stack Emergency Blood Donation & Dispatch Platform**

Blood Response Network connects **Voluntary Donors**, **Patients / Recipients**, **Hospitals**, and **Blood Banks** into an emergency response ecosystem. It includes live donor matching, hospital request triage, real-time Google Maps live donor tracking, and instant alerts.

---

## 🚀 Key Features

- **📍 Google Maps Live Donor Tracking (`/track`)**: Real-time GPS location tracking of assigned blood donors dispatched to hospitals with turn-by-turn routing via Google Maps.
- **🚨 Emergency Blood Request Dispatch (`/requests`, `/request-blood`)**: Automated blood compatibility matching (ABO/Rh factor) and dispatch alerts.
- **🔍 Voluntary Donor Registry (`/donors`)**: Filter eligible, verified donors by blood group and city.
- **🏥 Regional Blood Bank Directory & Live Inventory (`/blood-banks`)**: Real-time stock visibility for all 8 blood groups (Optimal, Low Stock, Out of Stock).
- **📊 Role-Based Dashboards (`/dashboard`)**:
  - **Donors**: Track donation history, toggle availability, broadcast live GPS location.
  - **Hospitals / Recipients**: Monitor emergency blood requests and track responding donors.
  - **Admins**: Platform audit logs and user verification.
- **🔔 Live Alerts & Notification Center (`/notifications`)**: Dynamic notifications with 1-click links to tracking.
- **🔐 Secure Authentication (`/login`, `/register`)**: Role-based access control, BCrypt password hashing, and JWT tokens.

---

## 🛠️ Tech Stack

### Frontend
- **Framework**: React (Vite)
- **Routing**: React Router v6
- **Icons**: React Icons (FontAwesome)
- **HTTP Client**: Axios with JWT Interceptors
- **Mapping**: Google Maps Embed & React Google Maps API

### Backend
- **Framework**: Java 21+ / Spring Boot 3
- **Security**: Spring Security 6 with Stateless JWT Authentication
- **ORM / Persistence**: Spring Data JPA & Hibernate
- **Database**: MySQL 8+
- **Build Tool**: Maven Wrapper (`mvnw`)

---

## ⚙️ Getting Started

### 1. Prerequisites
- **Java**: JDK 21 or later
- **Node.js**: v18+ & npm
- **MySQL**: Running on port `3306`

### 2. Configure Database
In `backend/src/main/resources/application.properties`, update your MySQL credentials:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/blood_network_db?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=your_password
```

### 3. Run Backend (Spring Boot)
```bash
cd backend
./mvnw clean spring-boot:run
# Windows PowerShell:
.\mvnw.cmd spring-boot:run
```
*The backend API runs on `http://localhost:8080`.*

### 4. Run Frontend (React / Vite)
```bash
cd frontend
npm install
npm run dev
```
*The frontend application runs on `http://localhost:5173`.*

---

## 👥 Seed Accounts (Pre-configured for Testing)

| Role | Email | Password |
| :--- | :--- | :--- |
| **Admin** | `admin@bloodnetwork.org` | `Admin@123` |
| **Hospital** | `apollo@hospitals.org` | `Hospital@123` |
| **Blood Bank** | `redcross@bloodbank.org` | `Bank@123` |
| **Donor** | `sneha.donor@example.com` | `Donor@123` |
| **Donor** | `rajesh.donor@example.com` | `Donor@123` |
