import React from 'react';
import { render, screen, fireEvent } from '@testing-library/react';
import TodoItem from './todo-item.component';

describe('TodoItem Component', () => {
  const mockTodo = {
    id: 1,
    title: 'Sample Todo',
    completed: false,
  };

  const mockUpdateTodo = jest.fn();
  const mockRemoveTodo = jest.fn();

  beforeEach(() => {
    mockUpdateTodo.mockClear();
    mockRemoveTodo.mockClear();
  });

  test('renders the todo item correctly', () => {
    //Arrange
    render(<TodoItem todo={mockTodo} updateTodo={mockUpdateTodo} removeTodo={mockRemoveTodo} />);

    //Assert
    expect(screen.getByText('Sample Todo')).toBeInTheDocument();
    expect(screen.getByRole('checkbox')).not.toBeChecked();
  });

  test('toggles the completion state', () => {
    // Arrange
    render(<TodoItem todo={mockTodo} updateTodo={mockUpdateTodo} removeTodo={mockRemoveTodo} />);

    // Act
    const checkbox = screen.getByRole('checkbox');
    fireEvent.click(checkbox);

    //Assert
    expect(mockUpdateTodo).toHaveBeenCalledWith({ ...mockTodo, completed: true });
  });

  test('enters edit mode and updates the title', () => {
    // Arrange
    render(<TodoItem todo={mockTodo} updateTodo={mockUpdateTodo} removeTodo={mockRemoveTodo} />);

    // Act
    const editButton = screen.getByText('Edit');
    fireEvent.click(editButton);

    // Assert
    expect(screen.getByDisplayValue('Sample Todo')).toBeInTheDocument();

    // Act
    const input = screen.getByDisplayValue('Sample Todo');
    fireEvent.change(input, { target: { value: 'Updated Todo' } });

    const updateButton = screen.getByText('Update');
    fireEvent.click(updateButton);

    // Assert
    expect(mockUpdateTodo).toHaveBeenCalledWith({ ...mockTodo, title: 'Updated Todo' });
  });

  test('deletes the todo item', () => {
    // Arange
    render(<TodoItem todo={mockTodo} updateTodo={mockUpdateTodo} removeTodo={mockRemoveTodo} />);

    // Act
    const deleteButton = screen.getByText('Delete');
    fireEvent.click(deleteButton);

    // Assert
    expect(mockRemoveTodo).toHaveBeenCalledWith(mockTodo.id);
  });
});
