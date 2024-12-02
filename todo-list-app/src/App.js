import React, { useState, useEffect } from 'react';
import { getTodos, addTodo, updateTodo, deleteTodo } from './services/todo-service';
import TodoInput from './components/todo-input/todo-input.component';
import TodoList from './components/todo-list/todo-list.component';
import './App.css';

const App = () => {
  const [todos, setTodos] = useState([]);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    const fetchTodos = async () => {
      setLoading(true);
      try {
        const fetchedTodos = await getTodos();
        sortTodos(fetchedTodos);
      } catch (error) {
        console.error('Error fetching todos:', error);
      } finally {
        setLoading(false);
      }
    };
    fetchTodos();
  }, []);

  const sortTodos = (todos) => {
    todos.sort((a,b) => a.completed - b.completed || new Date(a.createdAt) - new Date(b.createdAt));
    setTodos(todos);
  }


  const handleAddTodo = async (title) => {
    const newTodo = { title, description: '', completed: false };
    try {
      const addedTodo = await addTodo(newTodo);
      sortTodos([...todos, addedTodo]);
    } catch (error) {
      console.error('Error adding todo:', error);
    }
  };

  const handleupdateTodo = async (updatedTodo) => {
    try {
      const updated = await updateTodo(updatedTodo);
      sortTodos(todos.map((t) => (t.id === updatedTodo.id ? updated : t)));
    } catch (error) {
      console.error('Error updating todo:', error);
    }
  };

  const handleDeleteTodo = async (id) => {
    try {
      await deleteTodo(id);
      sortTodos(todos.filter((t) => t.id !== id));
    } catch (error) {
      console.error('Error deleting todo:', error);
    }
  };

  return (
    <div className="app-container">
      <h1>To-Do List</h1>
      <TodoInput addTodo={handleAddTodo} />
      {loading ? (
        <p>Loading...</p>
      ) : (
        <TodoList
          todos={todos}
          updateTodo={handleupdateTodo}
          removeTodo={handleDeleteTodo}
        />
      )}
    </div>
  );
};

export default App;
