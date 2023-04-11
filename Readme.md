
# FlapKap-Task

This repo contains the deliverables for FlapKap task



## Tech Stack

**Backend:** Spring Boot, PostgreSQL


## Run locally

- Create a jar build
- Run: 
```bash
  docker-compose up
```
## API Reference
### User APIs
#### create a new user

```http
  POST /api/user/register
```
 ```json
  {
    "username": "anthibo",
    "role": "BUYER" // "SELLER"
  }
```

#### Update a user 

```http
  PUT /api/user/{userId}
```
 ```json
  {
    "username": "anthibo",
    "role": "BUYER" // "SELLER"
  }
```
both fields are optional

#### Delete user

```http
  DELETE /api/user/{userId}
```
#### Deposit coins

```http
  POST /api/user/deposit
```

 ```json
  {
    "coin": 5, 10, 20, 50, or 100
  }
```

| Headers | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `userId`      | `number` | **Required**. The user id |



#### reset user deposit

```http
  GET /api/user/reset
```
| Headers | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `userId`      | `number` | **Required**. The user id |



### Product APIs

#### list products

```http
  GET /api/products
```

#### create a new Product

```http
  POST /api/products
```
 ```json
 {
    "productName": "x",
    "quantity": 10,
    "cost": 20
}
```
| Headers | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `sellerId`      | `number` | **Required**. The seller id |


#### Update a product 

```http
  PUT /api/products
```
 ```json
 {
    "productName": "x",
    "quantity": 10,
    "cost": 20
}
```

all fields are optional

| Headers | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `sellerId`      | `number` | **Required**. The seller id |


#### Delete product

```http
  DELETE /api/products/{productId}
```
| Headers | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `sellerId`      | `number` | **Required**. The seller id |




#### buy product

```http
  POST /api/product/{productId}/buy
```
  ```json
    {
        "amount": 1
    }
  ```
| Headers | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `buyerId`      | `number` | **Required**. The buyer id |

## Optimizations

- Implement Authentication
- Add unit, integration and e2e tests

