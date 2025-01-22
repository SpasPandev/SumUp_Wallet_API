# SumUp_Wallet_API

## Overview
SumUp Wallet API is a Spring Boot application that provides functionalities for user registration, authentication, and wallet management. It enables users to create wallets, view balances, and perform transactions such as deposits and withdrawals. The project uses JWT for secure authentication.

### Technologies Used
- Java 17
- Spring Boot 3.4.1
- Hibernate (JPA)
- MySQL
- JWT (JSON Web Tokens)
- Lombok
- ModelMapper
- Spring Security

## Setup and Run the API Locally

### Ensure you have the following installed
1. **Java 17** installed on your system.
2. **MySQL** server running locally.
3. A tool for managing dependencies, such as **Maven**.

### Steps
1. Clone the repository:
   ```bash
   git clone https://github.com/SpasPandev/SumUp_Wallet_API
   cd SumUp_Wallet_API

   ```

2. Configure the database:
    - Create a MySQL database named `sumup_wallet_api`. If the database doesn't exist, it will be created automatically when the application starts.
    - Provide the database credentials in the `application.properties` file or as environment variables:
      ```properties
      spring.datasource.username=YOUR_DB_USERNAME
      spring.datasource.password=YOUR_DB_PASSWORD
      ```

3. Update the JWT secret key:

    - Provide `JWT_SECRET_KEY` as environment variable. This key secures JSON Web Tokens (JWT).
    - Ensure the `JwtService` class uses the correct `JWT_SECRET_KEY`.
    - For development, you can use this placeholder key:
      ```plaintext
      e43e52e2a17d65ef99dca481e328c58cac994c7f82e41d1dc33a91313f159f7d
       ```


4. Build and run the application:
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

5. Access the API:
    - The API will be available at `http://localhost:8080`.


### Default Endpoints
- **Authentication**: `/api/v1/auth`
    - `POST /register` for user registration.
    - `POST /login` for user login.
- **Wallets**: `/api/v1/wallets`
    - `POST /` to create a new wallet.
    - `GET /` to retrieve all wallets.
    - `GET /{id}` to get wallet details.
    - `GET /{id}/balance` to view wallet balance.
    - `POST /{id}/deposit` to deposit funds.
    - `POST /{id}/withdraw` to withdraw funds.


---

## Testing the API

### Prerequisites
1. Ensure the application is running locally.
2. Use a tool like **Postman** to test the endpoints.


#### 1. **User Registration**
- **Endpoint**: `POST /api/v1/auth/register`
- **Request Body**:
  ```json
  {
    "username": "testuser",
    "password": "password",
    "firstName": "Test",
    "lastName": "User"
  }
  ```
- **Possible Responses**:
    - Success (201 Created):
      ```json
      {
        "message": "Register Successful"
      }
      ```
    - Conflict (409 Conflict):
      ```json
      {
        "message": "Username already exists. Please choose a different one."
      }
      ```

#### 2. **User Login**
- **Endpoint**: `POST /api/v1/auth/login`
- **Request Body**:
  ```json
  {
    "username": "testuser",
    "password": "password"
  }
  ```
- **Possible Responses**:
    - Success (200 OK):
      ```json
      {
        "jwt": "<JWT-TOKEN>"
      }
      ```
    - Unauthorized (401 Unauthorized):
      ```json
      {
        "message": "Invalid credentials"
      }
      ```


#### 2. **User Login**
- **Endpoint**: `POST /api/v1/auth/login`
- **Request Body**:
  ```json
  {
    "username": "testuser",
    "password": "password"
  }
  ```
- **Possible Responses**:
    - Success (200 OK):
      ```json
      {
        "jwt": "<JWT-TOKEN>"
      }
      ```
    - Unauthorized (401 Unauthorized):
      ```json
      {
        "message": "Invalid credentials"
      }
      ```

#### 3. **Create Wallet**
- **Endpoint**: `POST /api/v1/wallets`
- **Headers**:
  ```http
  Authorization: Bearer <JWT-TOKEN>
  ```
- **Request Body**:
  ```json
  {
    "name": "My Wallet"
  }
  ```
- **Possible Responses**:
    - Success (201 Created):
      ```json
      {
        "message": "Wallet created successfully",
        "wallet": {
          "id": 7,
          "name": "My Wallet",
          "balance": 0,
          "userDto": {
            "username": "testuser",
            "firstName": "Test",
            "lastName": "User"
          }
        }
      }
      ```
    - Bad Request (400 Bad Request):
      ```json
      {
        "message": "Wallet with this name already exists"
      }
      ```

#### 4. **Get All Wallets**
- **Endpoint**: `GET /api/v1/wallets`
- **Headers**:
  ```http
  Authorization: Bearer <JWT-TOKEN>
  ```
- **Possible Responses**:
    - Success (200 OK):
      ```json
      [
        {
          "id": 7,
          "name": "My Wallet",
          "balance": 0,
          "userDto": {
            "username": "testuser",
            "firstName": "Test",
            "lastName": "User"
          }
        }
      ]
      ```
    - Not Found (404 Not Found):
      ```json
      {
        "message": "No wallets found for the user"
      }
      ```

#### 5. **Get Wallet Details**
- **Endpoint**: `GET /api/v1/wallets/{id}`
- **Headers**:
  ```http
  Authorization: Bearer <JWT-TOKEN>
  ```
- **Possible Responses**:
    - Success (200 OK):
      ```json
      {
        "id": 7,
        "name": "My Wallet",
        "balance": 0,
        "userDto": {
          "username": "testuser",
          "firstName": "Test",
          "lastName": "User"
        }
      }
      ```
    - Forbidden (403 Forbidden):
      ```json
      {
        "message": "You do not have permission to access this wallet."
      }
      ```

#### 6. **View Wallet Balance**
- **Endpoint**: `GET /api/v1/wallets/{id}/balance`
- **Headers**:
  ```http
  Authorization: Bearer <JWT-TOKEN>
  ```
- **Possible Responses**:
    - Success (200 OK):
      ```json
      {
        "walletId": 7,
        "balance": 0
      }
      ```
    - Forbidden (403 Forbidden):
      ```json
      {
        "message": "You do not have permission to access this wallet."
      }
      ```


#### 7. **Deposit Funds**
- **Endpoint**: `POST /api/v1/wallets/{id}/deposit`
- **Headers**:
  ```http
  Authorization: Bearer <JWT-TOKEN>
  ```
- **Request Body**:
  ```json
  {
    "amount": 500
  }
  ```
- **Possible Responses**:
    - Success (200 OK):
      ```json
      {
        "walletId": 7,
        "newBalance": 500,
        "message": "Deposit successful"
      }
      ```
    - Bad Request (400 Bad Request):
      ```json
      {
        "message": "Deposit amount must be greater than zero."
      }
      ```
    - Forbidden (403 Forbidden):
      ```json
      {
        "message": "You do not have permission to access this wallet."
      }
      ```

#### 8. **Withdraw Funds**
- **Endpoint**: `POST /api/v1/wallets/{id}/withdraw`
- **Headers**:
  ```http
  Authorization: Bearer <JWT-TOKEN>
  ```
- **Request Body**:
  ```json
  {
    "amount": 200
  }
  ```
- **Possible Responses**:
    - Success (200 OK):
      ```json
      {
        "walletId": 7,
        "withdrawnAmount": 200,
        "newBalance": 300,
        "message": "Withdrawal successful"
      }
      ```
    - Bad Request (400 Bad Request):
      ```json
      {
        "message": "Insufficient balance to withdraw"
      }
      ```
    - Bad Request (400 Bad Request):
      ```json
      {
        "message": "Withdrawal amount must be greater than zero."
      }
      ```
    - Forbidden (403 Forbidden):
      ```json
      {
        "message": "You do not have permission to access this wallet."
      }
      ```

## Additional Notes
1. **Environment Variables**:
    - The API relies on the following environment variables: `DB_USERNAME`, `DB_PASSWORD`, and `JWT_SECRET_KEY`.
   
    - Ensure that:
        - `DB_USERNAME` and `DB_PASSWORD` are correctly configured in the `application.properties` file.
        - `JWT_SECRET_KEY` is properly updated in the `JwtService` class.
      
    - For development purposes, you can use the following placeholder for the `JWT_SECRET_KEY`:
       ```plaintext
       e43e52e2a17d65ef99dca481e328c58cac994c7f82e41d1dc33a91313f159f7d
        ```
2. **Database Initialization**:
    - When the application starts, it deletes existing tables and creates new ones.
   
    - The tables are initialized with default data from the `data.sql` file located in the `src/main/resources` directory.
