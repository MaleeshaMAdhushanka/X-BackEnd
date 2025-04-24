# Trading Platform - Spring Boot & React.js

## Project Description
This is a full-stack cryptocurrency trading platform built using **Spring Boot** for the backend and **React.js** with **Vite.js** for the frontend. The platform integrates with the **CoinGecko API** to fetch real-time cryptocurrency data, enabling users to view current prices, buy and sell assets, and manage their portfolios.

## ▶️ Project Demo (YouTube)

[![Watch the demo](https://img.youtube.com/vi/GpCEurOzY-s/0.jpg)](https://youtu.be/GpCEurOzY-s)
(https://youtu.be/GpCEurOzY-s)
## Key Features
- **Real-Time Cryptocurrency Data**: Fetch current prices and market trends using the **CoinGecko API**.
- **User Authentication**: Secure login and registration system for users.
- **Trading Dashboard**: View cryptocurrency prices, transaction history, and portfolio performance.
- **Buy/Sell Assets**: Users can buy and sell assets in real-time, with automatic price fetching.
- **Portfolio Management**: Users can track their portfolio balance and transaction history.
- **Profile Management**: Users can view and update their personal information.

## Screenshots

### Register Page
![Login Page Screenshot](./src/assets/signup.png)

### Login Page
![Login Page Screenshot](./src/assets/Login.png)

### ForgotPassword Page
![Login Page Screenshot](./src/assets/ForgotPassword.png)


### Home Page
![Home Page Screenshot](./src/assets/Home.png)

### Trading Dashboard
![Trading Dashboard Screenshot](./src/assets/treadingDashBorda.png)

### Example Coins stock chart Page
![Ethereum Dashboard Screenshot](./src/assets/EthereumCoinPage.png)

### Payment Details Page
![Payment Details Screenshot](./src/assets/payementdetails.png)



## Setup Instructions

[![SPRING](https://img.shields.io/badge/Spring_Framework-black?style=for-the-badge&logo=spring&logoColor=green)](https://spring.io/projects/spring-framework)

[![SPRING DATA JPA](https://img.shields.io/badge/Spring_Data_JPA-black?style=for-the-badge&logo=spring&logoColor=green)](https://spring.io/projects/spring-data-jpa)

[![HIBERNATE](https://img.shields.io/badge/Hibernate-black?style=for-the-badge&logo=Hibernate&logoColor=BBAE79)](https://hibernate.org/orm/)

[![MAVEN](https://img.shields.io/badge/Maven-black?style=for-the-badge&logo=Apache-Maven&logoColor=white)](https://maven.apache.org/)

[![MySQL](https://img.shields.io/badge/Mysql-black?style=for-the-badge&logo=mysql&logoColor=08668E")](https://www.mysql.com/downloads/)

[![POSTMAN](https://img.shields.io/badge/Postman-black?style=for-the-badge&logo=Postman&logoColor=FF6C37)](https://www.postman.com/downloads/)


### Prerequisites
- Java 17 or above
- Node.js and npm
- Spring Boot
- React.js with Vite.js
- MySQL or any relational database
- CoinGecko API (No API key required, but usage limits apply)

### Backend Setup (Spring Boot)

## Installation
1. Clone the repository:
   ```bash
   git clone https://github.com/MaleeshaMAdhushanka/X-BackEnd.git


2. Configure MySQL database:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/ZeeXmax
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   
   
   ```bash
     mvn clean install
   ```

4. Run the application:
   ```bash
   mvn spring-boot:run
   ```

## Project Documentation



### Class Structure
The class diagram showing the system's architecture and relationships can be found here:
[View Class Diagram](./src/assets/zeexmax.png)

## API Documentation

Detailed API documentation is available through Postman:
[View API Documentation](https://documenter.getpostman.com/view/37889199/2sB2cbaeW5)

## Frontend Repository

The frontend repository for this project can be found here:
[Zee Tradex Frontend](https://github.com/MaleeshaMAdhushanka/ZeeTradex-ForntEnd.git)

   ```