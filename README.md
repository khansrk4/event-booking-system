# Event Ticket Booking System

## Description

This is an Event Ticket Booking System built with **Spring Boot**, providing RESTful APIs to manage events, bookings, and payments. Users can create, search, and manage events, as well as book and cancel tickets. The system also includes payment processing functionality and notifications for booking confirmations and cancellations.

## Features

- User management
- Event creation and management
- Booking management
- Ticket availability checking
- Payment processing
- Notification system for confirmations and cancellations
- API documentation with Swagger

## Prerequisites

- **Java 17** or later
- **Maven**
- **Git**
- **Postman** (for API testing)

## Installation

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/khansrk4/event-booking-system.git
   cd event-booking-system


2. **Build the Project: Use Maven to build the project and resolve dependencies**:
   ```bash
   mvn clean install


3. **Run the Application: To start the application, use the following command**:
   ```bash
   mvn spring-boot:run

4. **Access the Application: Once the application is running, you can access it at**:
   ```bash
   http://localhost:8080

5. **API Documentation: The API documentation is available through Swagger UI. You can access it at**:
   ```bash
   http://localhost:8080/swagger-ui.html

6. **Access the H2 Console: Open your web browser and navigate to**:
   ```bash
   http://localhost:8080/h2-console

7. **Log In to the H2 Console: You will see a login screen with the following fields**:
- **JDBC URL: This should be pre-filled. For an H2 in-memory database, it will typically look like:**
- **Username: The default username is usually sa**
- **Password: The default password is usually blank (leave it empty).**
- **Click on "Connect": After filling in the details, click on the "Connect" button to log in to the H2 console.**


Let me know if there’s anything else you would like to modify!
