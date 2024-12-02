package com.todo.todolist.service;

import com.todo.todolist.dto.TodoListItemDto;
import com.todo.todolist.dto.UpdateTodoItemDto;
import com.todo.todolist.exception.TodoListItemNotFoundException;
import com.todo.todolist.model.TodoListItem;
import com.todo.todolist.repository.TodoListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TodoListServiceImpl implements TodoListService {

    @Autowired
    private TodoListRepository todoListRepository;

    @Override
    public List<TodoListItem> getItems() {
        return todoListRepository.findAll(Sort.by(Sort.Direction.ASC, "completed","createdAt"));
        //return todoListRepository.findAll();
    }

    @Override
    public TodoListItem addItem(TodoListItemDto itemDto) {
        TodoListItem todoItem = new TodoListItem();
        todoItem.setTitle(itemDto.getTitle());
        todoItem.setCompleted(false);
        todoItem.setCreatedAt(LocalDateTime.now());

        return todoListRepository.save(todoItem);
    }

    @Override
    public TodoListItem updateItem(Integer id, UpdateTodoItemDto itemDto) {
        Optional<TodoListItem> todoItemOpt = todoListRepository.findById(id);
        return todoItemOpt.map(todoItem ->
        {
            todoItem.setTitle(itemDto.getTitle());
            todoItem.setCompleted(itemDto.getCompleted());
            todoItem.setUpdatedAt(LocalDateTime.now());
            return todoListRepository.save(todoItem);
        }).orElseThrow(() -> new TodoListItemNotFoundException("Item not found."));
    }

    @Override
    public void deleteItem(Integer id) {

        Boolean itemExists = todoListRepository.existsById(id);
        if (!itemExists) {
            throw new TodoListItemNotFoundException("Item not found.");
        }
        todoListRepository.deleteById(id);
    }
}
