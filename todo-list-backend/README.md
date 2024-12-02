# Todo List (Backend)

## Overview

This is the backend part of the Todo List application, implemented using Java with the Spring Boot framework. It provides an API to manage the todo list items, including creating, updating, retrieving, and deleting todos. The backend uses an **in-memory database** for storage (data is not persisted).

## Technologies Used

- **Java** (JDK 21.0.5)
- **Spring Boot**
- **Maven**
- **Jakarta Validation**
- **Lombok**
- **H2 Database** (To store data In-Memory)

## API Endpoints

### 1. **Get all Todo Items**
- **URL**: `/api/todo`
- **Method**: `GET`
- **Response**: List of all todo items.
- **Description**: Fetches all todo items in the system.

### 2. **Add a New Todo Item**
- **URL**: `/api/todo`
- **Method**: `POST`
- **Request Body**:
  ```json
  {
    "title": "string"
  }
  ```
- **Response**: The newly added todo item.
- **Description**: Adds a new todo item to the list.

### 3. **Update a Todo Item**
- **URL**: `/api/todo/{id}`
- **Method**: `PUT`
- **Request Body**:
  ```json
  {
    "title": "string",
    "completed": "boolean"
  }
  ```
- **Path Variable**: `id` (The ID of the todo item to update).
- **Response**: The updated todo item.
- **Description**: Updates the title and completion status of an existing todo item.

### 4. **Delete a Todo Item**
- **URL**: `/api/todo/{id}`
- **Method**: `DELETE`
- **Path Variable**: `id` (The ID of the todo item to delete).
- **Response**: A message indicating the item was successfully deleted.
- **Description**: Deletes a todo item from the list.

## How to Run

1. Navigate to the `todo-list-backend` folder.
2. Run the following command to install dependencies:
   ```bash
   mvn install
   ```
3. Run the following command to start the Spring Boot application:
   ```
   mvn spring-boot:run
   ```
   This will start the backend server on `localhost:8080`.

## Testing

- Command to run tests:
   ```
   mvn test
   ```


## Future Work

- **Pagination for Todos**: Implement pagination to handle large datasets and provide more efficient navigation through the list of todos.

---
Kept **Persistent Database**, **Authentication** & **Authorization** out of scope for now.