# FinancialHelper

## Overview
The FinancialHelper project is a CRUD application developed with the goal of acquiring hands-on experience with Spring Boot and Spring Data JPA (Hibernate). It provides a simple REST API service for managing users, accounts, transactions, and categories.

### Features:
1. User Management
2. Account Management
3. Transaction Management
4. Categories Management
5. Money Transfer Between Accounts
6. Transaction History and Reporting

## Tech Stack

### Core:
- Spring Boot
- Spring Data JPA (Hibernate)
- PostgreSQL
- Liquibase
- Lombok

### Utility Libraries:
- Apache Commons Codec
- ModelMapper

### Testing:
- JUnit 5
- Mockito
- Testcontainers

## API

**Postman** collection with all requests:
[postman_collection.zip](https://github.com/nozzi325/FinancialHelper/files/9882494/postman_collection.zip)

Here are the lists of REST endpoints:
<details>
<summary>Users</summary>

| Endpoint | Method | Description|
|-----:|-----------| -----| 
| **getAllUsers**| GET| Retrieving list of all users and their accounts: `/api/users`|
| **getUserById**| GET| Retrieving information about specified user by **Id** : `/api/users/{id}`|
| **createUser**| POST| Creating new user : `/api/users`<br/> *Request body*:<br/> `{"email":"dummy_user@dummy.com", "password":"password123"}`|
| **updateUser**| PUT| Updating information about existing user: `/api/users/{id}`<br/> *Request body*:<br/> `{"id":1, "email":"new_dummy_user@dummy.com", "password":"password123"}`|
| **deleteUser**| DELETE| Deleting an existing user by **Id** : `/api/users/{id}` |

</details>

<details>
<summary>Accounts</summary>

| Endpoint | Method | Description                                                                                                                                         |
|-----:|-----------|-----------------------------------------------------------------------------------------------------------------------------------------------------| 
| **getAllAccounts**| GET| Retrieving list of all accounts and their ownerId: `/api/accounts`                                                                                  |
| **getAccountById**| GET| Retrieving information about specified user by **Id** : `/api/accounts/{id}`                                                                        |
| **createAccount**| POST| Creating new account : `/api/accounts`<br/> *Request body*:<br/> `{"name":"Bank USD", "balance":"10000.00", "ownerId":3}`                           |
| **updateAccount**| PUT| Updating information about existing account: `/api/accounts/{id}`<br/> *Request body*:<br/> `{"id":1, "name":"Bank USD", "balance":"5000.00", "ownerId":3}` |
| **deleteAccount**| DELETE| Deleting an existing account by **Id** : `/api/accounts/{id}`                                                                                       |

</details>

<details>
<summary>Transactions</summary>

|                       Endpoint | Method | Description                                                                                                                   |
|-------------------------------:|-----------|-------------------------------------------------------------------------------------------------------------------------------| 
|         **getAllTransactions** | GET| Retrieving list of all transactions: `/api/transactions`                                                                      |
|         **getTransactionById** | GET| Retrieving information about specified transaction by **Id** : `/api/transactions/{id}`                                       |
|    **getTransactionsByPeriod** | GET| Retrieving information about all transactions between certain dates : <br/>`/api/transactions?start=2022-10-16&end=2022-10-17` |
|  **getTransactionsByCategory** | GET| Retrieving information about all transactions with certain category : <br/>`/api/transactions?categoryId=1`                   |
| **getTransactionsByAccountId** | GET| Retrieving information about all transactions by account Id : <br/>`/api/transactions?accountId=1`                            |
|          **createTransaction** | POST| Creating new transaction : `/api/transactions`<br/> *Request body*:<br/> `{"result":5000.00, "accountId":1, "categoryId": 2}` |
|          **deleteTransaction** | DELETE| Deleting an existing transaction by **Id** : `/api/transactions/{id}`                                                         |

</details>

<details>
<summary>Category</summary>

|            Endpoint | Method | Description                                                                                               |
|--------------------:|-----------|-----------------------------------------------------------------------------------------------------------| 
|   **getCategories** | GET| Retrieving list of all categories: `/api/categories`                                                      |
| **getCategoryById** | GET| Retrieving information about specified category by **Id** : `/api/categories/{id}`                      |
|  **createCategory** | POST| Creating new category : `/api/categories`<br/> *Request body*:<br/> `{"name":"Steam"}`                  |
|  **updateCategory** | PUT| Updating existing category: `/api/categories/{id}`<br/> *Request body*:<br/> `{"id":8, "name":"STEAM"}` |
|  **deleteCategory** | DELETE| Deleting an existing category by **Id** : `/api/categories/{id}`                                        |

</details>

<details>
<summary>Transfer</summary>

| Endpoint | Method | Description|
|-----:|-----------| -----| 
| **transferMoney**| POST| Transfering money from one account to another: `/api/transfer`<br/> *Request body*:<br/> `{"senderId":1, "receiverId":2, "amount":5000.00}`|


</details>

## Getting Started
Follow these steps to run the application on your local machine:

1) #### Clone the Repository
   Clone git repository to your local machine by running the following command in your terminal:
   <br>`git clone https://github.com/nozzi325/FinancialHelper`
2) #### Set Environment Variables
   Create an `.env` file in the project's root directory to configure your database connection. You can set the `DB_USER`and `DB_PASSWORD` variables in this file. <br>Alternatively, you can update the `docker-compose.yml` file with the appropriate values for your database.

3) #### Start the Application
   Use Docker Compose to start the application and its dependencies. Run the following command:
   <br>`docker-compose up`