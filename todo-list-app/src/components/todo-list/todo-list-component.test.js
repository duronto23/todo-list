import React from 'react';
import { render, screen } from '@testing-library/react';
import TodoList from './todo-list.component';
import TodoItem from '../todo-item/todo-item.component';

jest.mock('../todo-item/todo-item.component', () => jest.fn(() => <div>Mocked TodoItem</div>)); // Mock TodoItem

describe('TodoList Component', () => {
  const mockUpdateTodo = jest.fn();
  const mockRemoveTodo = jest.fn();

  const todos = [
    { id: 1, title: 'Buy groceries', completed: true },
    { id: 2, title: 'Buy more groceries', completed: false },
  ];

  beforeEach(() => {
    TodoItem.mockClear();
  });

  test('renders the correct number of TodoItem components', () => {
    //Arrange
    render(
      <TodoList
        todos={todos}
        updateTodo={mockUpdateTodo}
        removeTodo={mockRemoveTodo}
      />
    );

    // Assert
    expect(TodoItem).toHaveBeenCalledTimes(todos.length);
  });

  test('renders "No todos available." when todos list is empty', () => {
    // Arrange
    render(
      <TodoList
        todos={[]}
        updateTodo={mockUpdateTodo}
        removeTodo={mockRemoveTodo}
      />
    );

    // Assert
    expect(TodoItem).not.toHaveBeenCalled();
    expect(screen.queryByText('No todos available.')).toBeInTheDocument();
  });
});
