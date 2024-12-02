package com.todo.todolist.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TodoListItemDto {
    @NotBlank(message = "Title cannot be empty")
    @Size(max = 50, message = "Title must be less than 50 characters")
    private String title;
}