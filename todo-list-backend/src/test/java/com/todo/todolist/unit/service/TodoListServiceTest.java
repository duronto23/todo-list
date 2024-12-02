package com.todo.todolist.unit.service;

import com.todo.todolist.dto.TodoListItemDto;
import com.todo.todolist.dto.UpdateTodoItemDto;
import com.todo.todolist.model.TodoListItem;
import com.todo.todolist.repository.TodoListRepository;
import com.todo.todolist.service.TodoListServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;

@SpringBootTest
public class TodoListServiceTest {
    @Mock
    private TodoListRepository todoListRepository;
    @InjectMocks
    private TodoListServiceImpl todoListService;

    @Test
    public void getAllItems_successfulRetrieval() {
        // Arrange
        TodoListItem todoListItem_1 = getMockItem(1);
        TodoListItem todoListItem_2 = getMockItem(2);
        List<TodoListItem> mockItems = Arrays.asList(
                todoListItem_1,
                todoListItem_2
        );
        Mockito.when(todoListRepository.findAll(Sort.by(Sort.Direction.ASC, "completed","createdAt"))).thenReturn(mockItems);

        // Act
        List<TodoListItem> items = todoListService.getItems();

        // Assert
        assertThat(items).isNotNull();
        assertThat(items.size()).isEqualTo(2);
        assertThat(items.get(0).getId()).isEqualTo(todoListItem_1.getId());
        assertThat(items.get(1).getId()).isEqualTo(todoListItem_2.getId());
    }

    @Test
    public void addItem_shouldAddNewItemSuccessfully() {
        // Arrange
        TodoListItemDto dtoItem = new TodoListItemDto("Buy groceries");

        TodoListItem addedItem = getMockItem(1, dtoItem);

        addedItem.setCompleted(false);
        addedItem.setCreatedAt(LocalDateTime.now());

        Mockito.when(todoListRepository.save(any())).thenReturn(addedItem);

        // Act
        TodoListItem item = todoListService.addItem(dtoItem);

        // Assert
        assertThat(item).isNotNull();
        assertThat(item.getTitle()).isEqualTo(dtoItem.getTitle());
        assertThat(item.getCreatedAt()).isNotNull();
        assertThat(item.getUpdatedAt()).isNull();
        assertThat(item.isCompleted()).isFalse();
    }

    @Test
    public void addItem_whenSaveFails_throwsException() {
        // Arrange
        TodoListItemDto dtoItem = new TodoListItemDto("Buy groceries");

        Mockito.when(todoListRepository.save(any())).thenThrow(new RuntimeException("Failed to add item."));

        // Act & Assert
        assertThatThrownBy(() -> todoListService.addItem(dtoItem)).isInstanceOf(RuntimeException.class).withFailMessage("Failed to add item.");
    }

    @ParameterizedTest(name = "Update successfully with valid description and completed = {0}")
    @CsvSource({
            "false",
            "true"
    })
    public void updateItem_shouldUpdateItemSuccessfully(boolean completed) {
        // Arrange
        Integer itemId = 1;
        UpdateTodoItemDto dtoItem = new UpdateTodoItemDto("Buy groceries", completed);

        TodoListItem mockItem = getMockItem(itemId, dtoItem);

        Mockito.when(todoListRepository.existsById(itemId)).thenReturn(true);
        Mockito.when(todoListRepository.findById(itemId)).thenReturn(Optional.of(mockItem));
        Mockito.when(todoListRepository.save(mockItem)).thenReturn(mockItem);

        // Act
        TodoListItem item = todoListService.updateItem(itemId, dtoItem);

        // Assert
        assertThat(item).isNotNull();
        assertThat(item.getTitle()).isEqualTo(dtoItem.getTitle());
        assertThat((item.isCompleted())).isEqualTo(completed);
        assertThat(item.getUpdatedAt()).isNotNull();
    }

    @ParameterizedTest(name = "Throws exception when item exists = {0}, successful update = {1} and completion status = {2}")
    @CsvSource({
            "false, false, false",
            "false, false, true",
            "false, true, false",
            "false, true, true",
            "true, false, false",
            "true, false, true"
    })
    public void updateItem_notSuccessful_throwsRuntimeException(Boolean itemExists, Boolean updatedSuccess, boolean completionStatus) {
        // Arrange
        Integer itemId = 1;
        UpdateTodoItemDto dtoItem = new UpdateTodoItemDto("Buy groceries", completionStatus);

        TodoListItem mockItem = getMockItem(itemId, dtoItem);

        Mockito.when(todoListRepository.existsById(itemId)).thenReturn(itemExists);
        Mockito.when(todoListRepository.findById(itemId)).thenReturn(itemExists ? Optional.of(mockItem) : Optional.empty());
        if(!updatedSuccess)
            Mockito.when(todoListRepository.save(mockItem)).thenThrow(new RuntimeException("Item update failed."));
        else
            Mockito.when(todoListRepository.save(mockItem)).thenReturn(mockItem);

        // Act & Assert
        String message = !itemExists ? "Item not found." : "Item update failed.";
        assertThatThrownBy(() -> todoListService.updateItem(itemId, dtoItem)).isInstanceOf(RuntimeException.class).hasMessage(message);

    }

    @Test
    public void deleteItem_successfulDeletion() {
        // Arrange
        Mockito.when(todoListRepository.existsById(1)).thenReturn(true);

        // Act
        todoListService.deleteItem(1);

        // Assert
        assertThatNoException();
    }

    @ParameterizedTest(name = "Throws exception when item exists = {1} and repository return {2} when remove.")
    @CsvSource({
            "1, false, false",
            "1, false, true",
            "1, true, false"
    })
    public void deleteItem_notSuccessful_throwsRuntimeException(Integer itemId, Boolean itemExists, Boolean deleteSuccess) {
        // Arrange
        Mockito.when(todoListRepository.existsById(itemId)).thenReturn(itemExists);
        if(!deleteSuccess)
            doThrow(new RuntimeException("Item delete failed.")).when(todoListRepository).deleteById(itemId);

        // Act & Assert
        String failMessage = !itemExists ? "Item not found." : "Item delete failed.";
        assertThatThrownBy(() -> todoListService.deleteItem(itemId)).isInstanceOf(RuntimeException.class).hasMessage(failMessage);
    }

    ///  Private methods
    private static TodoListItem getMockItem(Integer itemId) {
        TodoListItem mockItem = new TodoListItem();
        mockItem.setId(itemId);
        mockItem.setTitle("Buy groceries");
        return mockItem;
    }
    private static TodoListItem getMockItem(Integer itemId, TodoListItemDto dtoItem) {
        TodoListItem mockItem = new TodoListItem();
        mockItem.setId(itemId);
        mockItem.setTitle(dtoItem.getTitle());
        return mockItem;
    }

}
