import React, { useState } from 'react';

import './todo-item.styles.css'

const TodoItem = ({ todo, updateTodo, removeTodo }) => {
  
  const [edit, setEdit] = useState(false);
  const [item, setItem] = useState(todo);

  return (
    <div className={`todo-item ${todo.completed ? 'completed' : ''}`}>
    <div>
      <input
        type="checkbox"
        checked={todo.completed}
        onChange={() => {
          const completed = !todo.completed;
          updateTodo({...todo, completed});
          setItem({...item, completed});
        }}
      />
      { edit ?
        <input
          type="text"
          defaultValue={todo.title}
          onChange={(e) => setItem({...item, title: e.target.value})}
        />
      : <span>{todo.title}</span>}
    </div>
    <div>
      {
        edit ? <button className='button-update' onClick={()=>{
          updateTodo(item);
          setEdit(false);
        }}>Update</button> : <button className='button-edit' onClick={() => setEdit(true)}>Edit</button>
      }
      <button onClick={() => removeTodo(todo.id)}>Delete</button>
    </div>
  </div>
  );
};

export default TodoItem;
