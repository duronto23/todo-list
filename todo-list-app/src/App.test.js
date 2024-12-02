import React from 'react';
import { render, screen, fireEvent, waitFor } from '@testing-library/react';
import App from './App';
import { getTodos, addTodo, updateTodo, deleteTodo } from './services/todo-service';

jest.mock('./services/todo-service');

describe('App Component', () => {
  beforeEach(() => {
    jest.clearAllMocks();
  });

  test('renders loading state initially and fetches todos', async () => {
    //Arange
    getTodos.mockResolvedValueOnce([
      { id: 1, title: 'Buy groceries', completed: false, createdAt: new Date().toISOString() },
      { id: 2, title: 'Clean home', completed: true, createdAt: new Date().toISOString() },
    ]);

    //Act
    render(<App />);

    // Assert
    expect(screen.getByText(/loading/i)).toBeInTheDocument();

    await waitFor(() => expect(screen.getByText('Buy groceries')).toBeInTheDocument());
    expect(screen.getByText('Clean home')).toBeInTheDocument();
    expect(getTodos).toHaveBeenCalledTimes(1);
  });

  test('adds a new todo item', async () => {
    //Arrange
    getTodos.mockResolvedValueOnce([]);
    addTodo.mockResolvedValueOnce({
      id: 3,
      title: 'New Todo',
      completed: false,
      createdAt: new Date().toISOString(),
    });

    // Act
    render(<App />);

    //Assert
    await waitFor(() => expect(getTodos).toHaveBeenCalledTimes(1));

    fireEvent.change(screen.getByPlaceholderText(/add a new task/i), { target: { value: 'New Todo' } });
    fireEvent.click(screen.getByText(/add/i));

    await waitFor(() => expect(screen.getByText('New Todo')).toBeInTheDocument());
    expect(addTodo).toHaveBeenCalledTimes(1);
    expect(addTodo).toHaveBeenCalledWith({ title: 'New Todo', description: '', completed: false });
  });
});
