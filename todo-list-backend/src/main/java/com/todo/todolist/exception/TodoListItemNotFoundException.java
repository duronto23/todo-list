package com.todo.todolist.exception;

public class TodoListItemNotFoundException extends RuntimeException{
    public TodoListItemNotFoundException(String message) {
        super(message);
    }
}
