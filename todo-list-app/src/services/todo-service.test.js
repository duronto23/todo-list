import * as todoService from './todo-service';

const API_BASE_URL = 'http://localhost:8080/api/todo';

describe('API functions', () => {
  beforeEach(() => {
    fetch.resetMocks();
  });

  it('should fetch todos successfully', async () => {
    //Arrange
    const mockTodos = [{ id: 1, title: 'Test Todo', completed: false }];
    fetch.mockResponseOnce(JSON.stringify(mockTodos));

    // Act
    const todos = await todoService.getTodos();

    // Assert
    expect(todos).toEqual(mockTodos);
    expect(fetch).toHaveBeenCalledWith(API_BASE_URL, {
      method: 'GET',
      headers: { 'Content-Type': 'application/json' },
    });
  });

  it('should add a new todo successfully', async () => {
    //Arrange
    const newTodo = { title: 'New Todo', completed: false };
    fetch.mockResponseOnce(JSON.stringify(newTodo));

    // Act
    const addedTodo = await todoService.addTodo(newTodo);

    // Assert
    expect(addedTodo).toEqual(newTodo);
    expect(fetch).toHaveBeenCalledWith(API_BASE_URL, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(newTodo),
    });
  });

  it('should update a todo successfully', async () => {
    //Arrange
    const updatedTodo = { id: 1, title: 'Updated Todo', completed: true };
    fetch.mockResponseOnce(JSON.stringify(updatedTodo));

    // Act
    const result = await todoService.updateTodo(updatedTodo);

    // Assert
    expect(result).toEqual(updatedTodo);
    expect(fetch).toHaveBeenCalledWith(`${API_BASE_URL}/1`, {
      method: 'PUT',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(updatedTodo),
    });
  });

  it('should delete a todo successfully', async () => {
    // Arrange
    fetch.mockResponseOnce('', { status: 200 });

    // Act
    await todoService.deleteTodo(1);

    // Assert
    expect(fetch).toHaveBeenCalledWith(`${API_BASE_URL}/1`, {
      method: 'DELETE',
      headers: { 'Content-Type': 'application/json' },
    });
  });

  it('should handle fetch errors gracefully', async () => {
    //Arrange
    fetch.mockRejectOnce(new Error('Failed to fetch todos'));

    // Act and Assert
    await expect(todoService.getTodos()).rejects.toThrow('Failed to fetch todos');
  });
});
