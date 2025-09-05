# School Management System

A Java application demonstrating a 3-layer architecture with Hibernate and MySQL.

## Prerequisites

- Java 21 or later
- Maven 3.6 or later
- MySQL 8.0 or later

## Setup

1. Create a MySQL database named `school_management`:
   ```sql
   CREATE DATABASE school_management;
   ```

2. Update the database connection settings in `src/main/resources/META-INF/persistence.xml` if needed:
   - Update `jakarta.persistence.jdbc.url` with your MySQL server details
   - Update `jakarta.persistence.jdbc.user` and `jakarta.persistence.jdbc.password` with your MySQL credentials

3. Build the project:
   ```bash
   mvn clean install
   ```

## Running the Application

Run the `MainApp` class to execute the demo:
```bash
mvn exec:java -Dexec.mainClass="vn.edu.eiu.MainApp"
```

## Project Structure

```
src/main/java/vn/edu/eiu/
├── MainApp.java                 # Main application class
├── model/                      # Entity classes
│   ├── School.java
│   ├── Major.java
│   ├── Student.java
│   └── Gender.java
├── dao/                        # Data Access Object interfaces
│   ├── SchoolDao.java
│   ├── MajorDao.java
│   └── StudentDao.java
├── dao/impl/                   # DAO implementations
│   ├── SchoolDaoImpl.java
│   ├── MajorDaoImpl.java
│   └── StudentDaoImpl.java
├── service/                    # Service interfaces
│   ├── SchoolService.java
│   ├── MajorService.java
│   └── StudentService.java
├── service/impl/               # Service implementations
│   ├── SchoolServiceImpl.java
│   ├── MajorServiceImpl.java
│   └── StudentServiceImpl.java
└── util/                       # Utility classes
    └── HibernateUtil.java
```

## Features

- Complete CRUD operations for Schools, Majors, and Students
- Proper entity relationships with Hibernate/JPA annotations
- Transaction management
- Logging with SLF4J and Logback

## Database Schema

The application will automatically create the following tables:
- `schools`
- `majors`
- `students`

## Notes

- The application is configured to automatically update the database schema (`hibernate.hbm2ddl.auto=update`)
- All SQL statements are logged to the console for debugging purposes
