package com.todo.todolist.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateTodoItemDto extends TodoListItemDto{
    private Boolean completed;

    public UpdateTodoItemDto(String title, Boolean completed) {
        super(title);
        this.completed = completed;
    }
}
