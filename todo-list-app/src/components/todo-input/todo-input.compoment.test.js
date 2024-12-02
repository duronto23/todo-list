import React from 'react';
import { render, screen, fireEvent } from '@testing-library/react';
import TodoInput from './todo-input.component';

describe('TodoInput Component', () => {
  it('renders input field and button', () => {
    //Arrange
    render(<TodoInput addTodo={() => {}} />);

    // Assert
    expect(screen.getByPlaceholderText('Add a new task')).toBeInTheDocument();
    expect(screen.getByRole('button', { name: /add/i })).toBeInTheDocument();
  });

  it('allows user to type in the input field', () => {
    // Arrange
    render(<TodoInput addTodo={() => {}} />);

    // Act
    const input = screen.getByPlaceholderText('Add a new task');
    fireEvent.change(input, { target: { value: 'Buy groceries' } });

    //Assert
    expect(input.value).toBe('Buy groceries');
  });

  it('calls addTodo when clicking the Add button', () => {
    // Arrange
    const mockAddTodo = jest.fn();
    render(<TodoInput addTodo={mockAddTodo} />);

    const input = screen.getByPlaceholderText('Add a new task');
    const button = screen.getByRole('button', { name: /add/i });

    // Act
    fireEvent.change(input, { target: { value: 'Buy groceries' } });
    fireEvent.click(button);

    // Assert
    expect(mockAddTodo).toHaveBeenCalledWith('Buy groceries');
    expect(mockAddTodo).toHaveBeenCalledTimes(1);
    expect(input.value).toBe('');
  });

  it('does not call addTodo for empty input', () => {
    //Arrange
    const mockAddTodo = jest.fn();
    render(<TodoInput addTodo={mockAddTodo} />);

    const button = screen.getByRole('button', { name: /add/i });

    // Act
    fireEvent.click(button);

    //Assert
    expect(mockAddTodo).not.toHaveBeenCalled();
  });
});
