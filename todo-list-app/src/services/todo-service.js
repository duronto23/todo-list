const API_BASE_URL = 'http://localhost:8080/api/todo';

export const getTodos = async () => {
  const response = await fetch(API_BASE_URL, {
    method: 'GET',
    headers: { 'Content-Type': 'application/json' },
  });
  if (!response.ok) {
    throw new Error('Failed to fetch todos');
  }
  return response.json();
};

export const addTodo = async (todo) => {
  const response = await fetch(API_BASE_URL, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(todo),
  });
  if (!response.ok) {
    throw new Error('Failed to add todo');
  }
  return response.json();
};

export const updateTodo = async (updatedTodo) => {
  const response = await fetch(`${API_BASE_URL}/${updatedTodo.id}`, {
    method: 'PUT',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(updatedTodo),
  });
  if (!response.ok) {
    throw new Error('Failed to update todo');
  }
  return response.json();
};

export const deleteTodo = async (id) => {
  const response = await fetch(`${API_BASE_URL}/${id}`, {
    method: 'DELETE',
    headers: { 'Content-Type': 'application/json' },
  });
  if (!response.ok) {
    throw new Error('Failed to delete todo');
  }
};
