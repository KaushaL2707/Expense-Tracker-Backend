# Expense Tracker Backend API Documentation

This is the backend service for the Expense Tracker application, built with Spring Boot.

## Base URL
`http://localhost:8080/api`

## Data Models

### Transaction
| Field | Type | Description |
| :--- | :--- | :--- |
| `id` | Long | Unique ID (Auto-generated) |
| `userId` | Long | ID of the user |
| `title` | String | Short description of transaction |
| `amount` | Double | Transaction amount |
| `type` | String | `income` or `expense` |
| `category` | String | e.g., "Food", "Transport", "Salary" |
| `date` | String | Format: `YYYY-MM-DD` |
| `notes` | String | Optional details |

### Budget
| Field | Type | Description |
| :--- | :--- | :--- |
| `id` | Long | Unique ID (Auto-generated) |
| `userId` | Long | ID of the user |
| `amount` | Double | Budget limit for the month |
| `month` | String | Format: `YYYY-MM` |

### User
| Field | Type | Description |
| :--- | :--- | :--- |
| `id` | Long | Unique ID |
| `name` | String | User's full name |
| `email` | String | User's email |
| `password` | String | User's password |

---

## API Endpoints

### 1. Transactions

#### Get All Transactions
Fetch all transactions for a specific user.
- **Endpoint:** `GET /transactions/{userId}`
- **Response:** `List<Transaction>`

#### Add Transaction
Create a new income or expense record.
- **Endpoint:** `POST /transactions`
- **Body:**
```json
{
  "userId": 1,
  "title": "Grocery Shopping",
  "amount": 50.0,
  "type": "expense",
  "category": "Food",
  "date": "2023-10-27",
  "notes": "Weekly groceries"
}
```

#### Update Transaction
Modify an existing transaction.
- **Endpoint:** `PUT /transactions/{id}`
- **Body:** Same as Add Transaction (fields to update).

#### Delete Transaction
Remove a transaction.
- **Endpoint:** `DELETE /transactions/{id}`

---

### 2. Analytics & Dashboard

#### Get Monthly Analytics
Get totals, balance, budget status, and breakdown for graphs.
- **Endpoint:** `GET /analytics/{userId}?month=YYYY-MM`
- **Query Param:** `month` (Optional, defaults to current month).
- **Response:**
```json
{
  "totalIncome": 5000.0,
  "totalExpense": 1200.0,
  "balance": 3800.0,
  "budgetLimit": 2000.0,
  "budgetPercentage": 60.0,
  "categoryBreakdown": {
    "Food": 500.0,
    "Transport": 200.0
  },
  "dailyBreakdown": {
    "2023-10-01": 100.0,
    "2023-10-02": 50.0
  }
}
```

---

### 3. Budgets

#### Get Monthly Budget
Check the budget set for a specific month.
- **Endpoint:** `GET /budgets/{userId}?month=YYYY-MM`
- **Response:** `Budget` object or null/empty.

#### Set/Update Budget
Set a spending limit for a month.
- **Endpoint:** `POST /budgets`
- **Body:**
```json
{
  "userId": 1,
  "amount": 2000.0,
  "month": "2023-10"
}
```

---

### 4. User Profile

#### Get User Details
- **Endpoint:** `GET /users/{id}`
- **Response:** `User` object.

#### Update User Profile
- **Endpoint:** `PUT /users/{id}`
- **Body:**
```json
{
  "name": "John Doe",
  "email": "john@example.com"
}
```

---

### 5. Reports

#### Export Transactions
Download a CSV file of transactions for a specific month.
- **Endpoint:** `GET /reports/{userId}/export?month=YYYY-MM`
- **Response:** Downloadable `.csv` file.
