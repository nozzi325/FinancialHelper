# FinancialHelper
The main goal of this project is to master the basic skills of working with Spring Boot and create a REST API service.

Here are the lists of REST endpoints:
<details>
<summary>Users</summary>

| Endpoint | Method | Description|
|-----:|-----------| -----| 
| **getAllUsers**| GET| Retrieving list of all users and their accounts: `/api/users`|
| **getUserById**| GET| Retrieving information about specified user by **Id** : `/api/users/{id}`|
| **createUser**| POST| Creating new user : `/api/users`<br/> *Request body*:<br/> `{"email":"dummy_user@dummy.com", "password":"password123"}`|
| **updateUser**| PUT| Updating information about existing user: `/api/users`<br/> *Request body*:<br/> `{"id":1, "email":"new_dummy_user@dummy.com", "password":"password123"}`|
| **deleteUser**| DELETE| Deleting an existing user by **Id** : `/api/users/{id}` |

</details>

<details>
<summary>Accounts</summary>

| Endpoint | Method | Description|
|-----:|-----------| -----| 
| **getAllAccounts**| GET| Retrieving list of all accounts and their ownerId: `/api/accounts`|
| **getAccountById**| GET| Retrieving information about specified user by **Id** : `/api/accounts/{id}`|
| **createAccount**| POST| Creating new account : `/api/accounts`<br/> *Request body*:<br/> `{"name":"Bank USD", "balance":"10000.00", "ownerId":3}`|
| **updateAccount**| PUT| Updating information about existing account: `/api/accounts`<br/> *Request body*:<br/> `{"id":1, "name":"Bank USD", "balance":"5000.00", "ownerId":3}`|
| **deleteAccount**| DELETE| Deleting an existing account by **Id** : `/api/accounts/{id}` |

</details>

<details>
<summary>Transactions</summary>

| Endpoint | Method | Description|
|-----:|-----------| -----| 
| **getAllTransactions**| GET| Retrieving list of all transactions: `/api/transactions`|
| **getTransactionById**| GET| Retrieving information about specified transaction by **Id** : `/api/transactions/{id}`|
| **createTransaction**| POST| Creating new transaction : `/api/transactions`<br/> *Request body*:<br/> `{"result":5000.00, "accountId":1, "categoryId": 2}`|
| **deleteTransaction**| DELETE| Deleting an existing transaction by **Id** : `/api/transactions/{id}` |

</details>

<details>
<summary>Category</summary>

| Endpoint | Method | Description|
|-----:|-----------| -----| 
| **getCategories**| GET| Retrieving list of all categories: `/api/transactions`|
| **getCategoryById**| GET| Retrieving information about specified category by **Id** : `/api/transactions/{id}`|
| **createTransaction**| POST| Creating new category : `/api/transactions`<br/> *Request body*:<br/> `{"name":"Steam"}`|
| **updateCategory**| PUT| Updating existing category: `/api/transactions`<br/> *Request body*:<br/> `{"id":8, "name":"STEAM"}`|
| **deleteTransaction**| DELETE| Deleting an existing category by **Id** : `/api/transactions/{id}` |

</details>

<details>
<summary>Transfer</summary>

| Endpoint | Method | Description|
|-----:|-----------| -----| 
| **transferMoney**| POST| Transfering money from one account to another: `/api/transactions`<br/> *Request body*:<br/> `{"senderId":1, "receiverId":2, "amount":5000.00}`|


</details>
