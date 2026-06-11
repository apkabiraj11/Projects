# Employee Management System (Java Swing + JDBC)

A desktop-based Employee Management System built using **Java (Swing GUI)** and **JDBC for database connectivity**. This project provides a complete solution for managing employee records with authentication and profile management features.

The system includes login authentication, employee CRUD operations, and user profile customization features such as password change and profile picture update.

## Features

### 🔐 Authentication System

* User Login system
* Secure session access
* Splash screen before application start

### 👨‍💼 Employee Management (CRUD)

* Add new employee details
* View employee information
* Update employee records
* Remove employee from system

### 👤 Profile Management

* Edit user profile information
* Change password
* Change profile picture

### 🏠 Dashboard

* Home dashboard after login
* Navigation to all major features

### 🗄️ Database Integration

* JDBC connection using `Conn.java`
* Persistent storage for employee and user data

## Project Modules

* `Login.java` → Handles user authentication
* `Splash.java` → Loading screen before login
* `Home.java` → Main dashboard
* `AddEmployee.java` → Add new employee data
* `ViewEmployee.java` → View employee records
* `UpdateEmployee.java` → Update existing employee data
* `RemoveEmployee.java` → Delete employee records
* `EditProfile.java` → Edit user profile
* `ChangePassword.java` → Password update module
* `ChangeProfilePicture.java` → Profile image update
* `Conn.java` → Database connection handler

## OOP Concepts Used

* Classes and Objects
* Encapsulation
* Abstraction
* Modular Programming
* GUI Event Handling (Swing)
* Database Connectivity (JDBC)

## Technologies Used

* Java (Swing GUI)
* JDBC
* MySQL (or any configured relational database)
