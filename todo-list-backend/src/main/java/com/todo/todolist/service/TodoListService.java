package com.todo.todolist.service;

import com.todo.todolist.dto.TodoListItemDto;
import com.todo.todolist.dto.UpdateTodoItemDto;
import com.todo.todolist.model.TodoListItem;

import java.util.List;

public interface TodoListService {
    List<TodoListItem> getItems();
    TodoListItem addItem(TodoListItemDto itemDto);
    TodoListItem updateItem(Integer id, UpdateTodoItemDto item);
    void deleteItem(Integer id);
}
