# Serenity Mental Health Therapy Center System

This project is a digital registration and management system developed for the **Serenity Mental Health Therapy Center** in Sri Lanka. The system transitions the center from manual paper-based records to a streamlined digital solution using **Hibernate ORM** to manage patient enrollments, therapy programs, and financial transactions.

---

## 🚀 Project Overview

The Serenity Mental Health Therapy Center serves approximately 3,000 patients annually. This system is designed to handle complex workflows, including informal interviews, multi-program registrations, and secure role-based access for Admins and Receptionists.

### Key Features

* **User Role Management:** Secure authentication for Admins (managing therapists and programs) and Receptionists (managing patients and billing).


* **Therapy Program Management:** Full CRUD operations for therapy offerings, including duration and fee tracking.


* **Patient & Session Handling:** Comprehensive profiles including medical history and automated scheduling with therapist availability.


* **Billing & Invoicing:** Upfront payment processing, invoice generation, and transaction tracking.


* **Advanced Security:** Password encryption using **BCrypt** hashing and role-based access control (RBAC).



---

## 🛠️ Technical Stack

* **Language:** Java
* **ORM Framework:** Hibernate 


* **Architecture:** Layered Architecture with Design Patterns (Façade/Factory) 


* **Database Interactions:** HQL (Hibernate Query Language) and JPQL 


* **Security:** BCrypt Encryption 


* **Configuration:** Property file-based Hibernate configuration 



---

## 📊 Therapy Programs Offered

The system manages the following default programs:

| Program ID | Program Name | Duration | Fee (LKR) |
| --- | --- | --- | --- |
| MT1001 | Cognitive Behavioral Therapy | 12 weeks | 80,000.00 |
| MT1002 | Mindfulness-Based Stress Reduction | 8 weeks | 50,000.00 |
| MT1003 | Dialectical Behavior Therapy | 16 weeks | 100,000.00 |
| MT1004 | Group Therapy Sessions | 6 months | 120,000.00 |
| MT1005 | Family Counseling | 3 months | 40,000.00 |

---

## ⚙️ Core Implementation Details

### Hibernate Relationships

The system utilizes complex database relationships managed through Hibernate:

* **One-to-Many:** Therapists to Therapy Sessions.


* **Many-to-One:** Patients to Therapy Sessions.


* **Cascade Types:** Implemented to ensure data integrity across related entities.



### Data Validation

* All user inputs are validated using **Regular Expressions (RegEx)**.


* Specific validation for Email formats and country-specific Phone numbers.



### Exception Handling

A custom exception handling mechanism is implemented for:

* Registration errors (duplicates/missing fields).


* Login failures and account lockouts.


* Payment processing and scheduling conflicts.



---

## 📝 Usage Requirements

* **No Direct SQL Clients:** Management of the database must be handled via the application, not through MySQL clients.


* **No JDBC:** All database interactions must use the Hibernate framework.


* **Authentication:** Users must log in with encrypted credentials and have the ability to update their profiles.



---

**Note:** This project was developed as part of the **Graduate Diploma in Software Engineering (GDSE)** at **IJSE**.

