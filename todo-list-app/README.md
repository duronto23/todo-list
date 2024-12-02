# Todo App (Frontend)

This project is the frontend of the Todo application, developed using **React** and bootstrapped with **Create React App**. It serves as the user interface, interacting with the backend to manage the todo items.

---

## Features
- **View Todos:** Display all todo items retrieved from the backend, sort todos by creation date, with incomplete todos listed first _[Handling sorting in frontend for now, could rely only on the backend sorting in future if need to handle huge number of todos and adding pagination]_.
- **Add Todos:** Create new tasks using an intuitive input field.
- **Mark as Completed:** Toggle the completion status of a todo.
- **Update Todos:** Edit the title of an existing todo.
- **Delete Todos:** Remove todo that are no longer needed.
---

## Prerequisites
Ensure the backend is running before starting the frontend.

---

## How to run

### Install Dependencies
Run the following command to install required dependencies:
```bash
npm install
```

### Run the Application
Start the development server:
```bash
npm start
```
This will launch the app in development mode at [http://localhost:3000](http://localhost:3000).

### Run Tests
To run tests:
```bash
npm test
```

For test coverage:
```bash
npm test -- --coverage
```
---

## API Integration

The app communicates with the backend via the following API endpoints:
- **GET `/api/todo`**: Retrieve all todo items.
- **POST `/api/todo`**: Add a new todo.
- **PUT `/api/todo/:id`**: Update an existing todo by its ID.
- **DELETE `/api/todo/:id`**: Delete a todo by its ID.

These endpoints are implemented in `src/services/todo-service.js` using the `fetch` API.

*The base URL for the backend has been configured in the `.env` file located in the root directory.*

## Future Work
- **Pagination for Todos**: Implement pagination to efficiently display a large number of todos.
- **Improve UX**: Focus on refining the user interface to create a more engaging and visually appealing experience.

---
Kept **Login & Registration** out of scope for now.