# Todo List Application

_This README provides an overview of the project. For detailed instructions and specifications, please refer to the individual README files for the backend and frontend, as they contain more in-depth information for each part of the project._

This project consists of two parts:

- **Backend**: Implemented with **Java Spring Boot**.
- **Frontend**: Implemented with **React**.

---

## Backend

The backend is a simple Java Spring Boot application that serves as the API for the Todo List application. It uses an **in-memory database** to store data. Currently, data is not persisted, and there is no authentication or authorization implemented.

### Features:
- **4 Endpoints**:
    1. `GET /api/todo` - Retrieve all todo items.
    2. `POST /api/todo` - Add a new todo item.
    3. `PUT /api/todo/{id}` - Update an existing todo item.
    4. `DELETE /api/todo/{id}` - Delete a todo item.

The backend is designed to perform basic operations on the todo list, such as adding, updating, retrieving, and deleting todo items.

---

## Frontend

The frontend is implemented with **React**. It communicates with the backend API to retrieve and display todos.

### Features:
- Displays a list of todo items fetched from the backend.
- Todos are displayed in order of creation date, with **undone todos** appearing first.
- Users can:
    - Add new todo items.
    - Mark a todo item as completed.
    - Edit a todo item.

---

## How to Run

### Backend
1. **Navigate to the `todo-list-backend` folder**:
   ```
   cd todo-list-backend
   ```

2. **Run the Spring Boot backend**:
   ```
   mvn spring-boot:run
   ```

   This will start the backend on `localhost:8080`.

### Frontend
1. **Navigate to the `todo-list-app` folder**:
   ```
   cd todo-list-app
   ```

2. **Install dependencies**:
   ```
   npm install
   ```

3. **Run the React frontend**:
   ```
   npm start
   ```

   This will start the frontend on `localhost:3000`.

---

## Application Flow

1. The frontend will fetch the list of todos from the backend API and display them.
2. You can add new todos, mark todos as completed, or update an existing todo from the frontend.
3. The todos are displayed in the order of creation date, and undone todos are listed first.
