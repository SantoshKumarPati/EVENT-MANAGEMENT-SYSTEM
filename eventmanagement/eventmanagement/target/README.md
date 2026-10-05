# Event Management System

## Project Overview

The Event Management System is a full-stack web application developed to manage events and event registrations.

The application allows users to view available events and register for them. Administrators can create, update, delete, and manage events and registrations.

The project is developed using Java Spring Boot for the backend, MySQL for database management, and HTML, CSS, and JavaScript for the frontend.

---

## Features

### Event Management

- View all events
- View event details
- Search events by name
- Filter events by status
- Filter events by date
- Add new events
- Edit existing events
- Delete events
- Manage total and available seats

### Event Registration

- Register for an event
- Enter full name, email, and phone number
- Select an event
- Validate required registration fields
- Validate email
- Prevent duplicate registration for the same event
- Prevent registration when no seats are available
- Automatically decrease available seats after registration
- Generate a registration ID
- Cancel registrations
- Automatically increase available seats after cancellation

### Registration Management

- View all registrations
- Search registrations by name or email
- Filter registrations by event
- View registration status
- Cancel registration
- Update registration status

### Dashboard

The dashboard displays:

- Total Events
- Upcoming Events
- Total Registrations
- Available Seats
- Cancelled Registrations

Dashboard values are retrieved dynamically from the backend database.

---

## Technologies Used

### Backend

- Java 23
- Spring Boot 4.1.1
- Spring Web
- Spring Data JPA
- Hibernate
- Maven

### Frontend

- HTML5
- CSS3
- JavaScript
- Fetch API
- VS Code Live Server

### Database

- MySQL 

