import React, { useState } from 'react';
import './todo-input.styles.css'

const TodoInput = ({ addTodo }) => {
  const [text, setText] = useState('');

  const handleAdd = () => {
    if (text.trim()) {
      addTodo(text);
      setText('');
    }
  };

  return (
    <div className="todo-input-container">
      <input
        type="text"
        value={text}
        onChange={(e) => setText(e.target.value)}
        placeholder="Add a new task"
      />
      <button onClick={handleAdd}>Add</button>
    </div>
  );
};

export default TodoInput;
