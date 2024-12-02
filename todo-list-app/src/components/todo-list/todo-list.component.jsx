import React from 'react';
import TodoItem from '../todo-item/todo-item.component';

const TodoList = ({ todos, updateTodo, removeTodo }) => (
  <div>
    {todos.length === 0 ? (
      <p>No todos available.</p>
    ) : (
      todos.map((todo) => (
        <TodoItem
          key={todo.id}
          todo={todo}
          updateTodo={updateTodo}
          removeTodo={removeTodo}
        />
      ))
    )}
  </div>
);

export default TodoList;
