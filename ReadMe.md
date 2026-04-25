

### Airtel Inventory Management Project done by Emmanuel Niyonsaba and Kevine Umukurwa
##Project Description
The Airtel Inventory Management System is a reliable, user-friendly, fully offline-capable Windows-based inventory management web application designed for Airtel to track and manage end-user equipment including laptops, desktops, and mobile phones.

###Key Features

Asset Registration
 - Register devices with full specifications (brand, model, serial number, RAM, storage, processor)

Assignment Tracking
 - Assign equipment to users with purpose and expected return date

Condition Monitoring
 - Track device condition at assignment and return time

Return Management 
- Process equipment returns with condition assessment

Comprehensive Reporting 
- Generate reports by date, department, device type, and status

Search & Filter
 - Quick search functionality for equipment and assignments

Audit History
 - Complete history of all asset movements

User Management 
- Admin-only user creation with role-based access (ADMIN, MANAGER, USER)

Technology Stack

Technology	Version

Spring Boot	2.7.18

Java 21,MySQL	8.0,Thymeleaf	-Maven	

System Administrator Credentials

Use the following credentials to access the system with full administrative privileges:

Field	Value

Username:	RegNo1

Password:	RegNo2

##Note: Credentials are case-sensitive. Admin has full access including user management.

##Setup Instructions

Prerequisites

Before starting, ensure you have installed:

-Java JDK 17 or higher

-XAMPP (for MySQL database)

Maven (or use included Maven wrapper)

Step 1: Extract the Project
Extract the compressed ZIP file to your preferred location:

text
C:\airtel-inventory\
Step 2: Setup Database with XAMPP
Start XAMPP Control Panel

Click "Start" button next to MySQL service

Open phpMyAdmin by clicking "Admin" button or go to http://localhost/phpmyadmin

Create new database named airtel_inventory:

Click "New" in left sidebar

Enter database name: airtel_inventory

Select collation: utf8mb4_general_ci

Click "Create"

Step 3: Configure Application

The database configuration is already set in src/main/resources/application.properties:

properties

spring.datasource.url=jdbc:mysql://localhost:3306/airtel_inventory
spring.datasource.username=root
spring.datasource.password=

ℹ️ If your MySQL root has a password, update the spring.datasource.password field.

Step 4: Build and Run

Using Command Line:

bash

# Navigate to project directory

cd C:\airtel-inventory

# Clean and build
mvn clean

# Run the application
mvn spring-boot:run

Using IDE (Eclipse/IntelliJ):

Import as Existing Maven Project

Wait for dependencies to download

Run ImsApplication.java main class

Step 5: Access the Application
Open your web browser (Chrome, Firefox, or Edge recommended)

Go to: http://localhost:8080

Login with administrator credentials:

Username: RegNo1

Password: RegNo2

How to Navigate the System

Dashboard (Home Page)
View key statistics: total equipment, available, assigned, under maintenance, damaged

Quick action buttons for common tasks

Personalized welcome message

##Equipment Management (/equipment)

View all equipment	Click "Equipment" in sidebar

Add equipment	Click "+ Add Equipment" button

Edit equipment	Click "Edit" button next to any item

Delete equipment	Click "Delete" (only for unassigned items)

Search equipment	Use search bar at top of page

##Assignments Management (/assignments)

View active assignments	Click "Assignments" in sidebar

Assign equipment	Find equipment in list, click "Assign"

Return equipment	Go to "Active Assignments" tab, click "Return"

View overdue items	Click "Overdue" tab

##Reports (/reports)

View equipment statistics by device type and status

View department-wise assignment distribution

Export reports for printing or saving

##User Management (/users) - Admin Only

View all system users

Add new users with roles: ADMIN, MANAGER, USER

##Logout

Click "Logout" button in the top-right corner of any page

Naviga