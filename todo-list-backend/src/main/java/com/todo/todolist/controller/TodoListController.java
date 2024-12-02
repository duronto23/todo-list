package com.todo.todolist.controller;

import com.todo.todolist.dto.TodoListItemDto;
import com.todo.todolist.dto.UpdateTodoItemDto;
import com.todo.todolist.model.TodoListItem;
import com.todo.todolist.service.TodoListServiceImpl;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TodoListController {
    private final TodoListServiceImpl todoListService;

    public TodoListController(TodoListServiceImpl todoListService) {
        this.todoListService = todoListService;
    }

    /**
     * Retrieves all Todo items.
     *
     * @return A list of Todo items.
     */
    @GetMapping("/api/todo")
    public ResponseEntity<List<TodoListItem>> getItems() {
        List<TodoListItem> items = todoListService.getItems();
        return ResponseEntity.ok(items);
    }

    /**
     * Adds a new Todo item to the list.
     *
     * @param item The Todo item to be added.
     * @return The added Todo item.
     */
    @PostMapping("/api/todo")
    public ResponseEntity<TodoListItem> addItem(@Valid @RequestBody TodoListItemDto item) {
        TodoListItem savedItem = todoListService.addItem(item);
        return ResponseEntity.status(201).body(savedItem);
    }

    /**
     * Updates an existing Todo item.
     *
     * @param id The ID of the Todo item to be updated.
     * @param item The updated Todo item data.
     * @return The updated Todo item.
     */
    @PutMapping("/api/todo/{id}")
    public ResponseEntity<TodoListItem> updateItem(@PathVariable @Min(1) Integer id, @Valid @RequestBody UpdateTodoItemDto item) {
        TodoListItem updatedItem = todoListService.updateItem(id, item);
        return ResponseEntity.ok(updatedItem);
    }

    /**
     * Deletes a Todo item.
     *
     * @param id The ID of the Todo item to be deleted.
     * @return A confirmation message.
     */
    @DeleteMapping("/api/todo/{id}")
    public ResponseEntity<String> deleteItem(@PathVariable @Min(1) Integer id) {
        todoListService.deleteItem(id);
        return ResponseEntity.ok("Deleted Successfully.");
    }
}
