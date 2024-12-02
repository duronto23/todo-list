package com.todo.todolist.integration.controller;

import com.todo.todolist.dto.TodoListItemDto;
import com.todo.todolist.dto.UpdateTodoItemDto;
import com.todo.todolist.model.TodoListItem;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TodoListControllerTest {
    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;

    private String todoUrl;
    RequestEntity<List<TodoListItem>> allItemsRequest;
    ResponseEntity<TodoListItem> addedItemResponse;

    @BeforeEach
    public void init() {
         todoUrl = "http://localhost:" + port + "/api/todo";
         allItemsRequest = new RequestEntity<>(HttpMethod.GET, URI.create(todoUrl));
         addedItemResponse = addTodoItem("Buy groceries");
    }

    @AfterEach
    public void cleanup() {
        ResponseEntity<List<TodoListItem>> allItemsResponse = restTemplate.exchange(
                allItemsRequest,
                new ParameterizedTypeReference<>() {}
        );

        for (TodoListItem item : allItemsResponse.getBody().stream().toList()) {
            RequestEntity<?> deleteRequestEntity = new RequestEntity<>(HttpMethod.DELETE, URI.create(todoUrl + "/" + item.getId()));
            restTemplate.exchange(
                    deleteRequestEntity,
                    new ParameterizedTypeReference<String>() {}
            );
        }
    }

    @Test
    public void addTodoAndGetAllItemsTest() {
        // Arrange

        // Act
        ResponseEntity<List<TodoListItem>> allItemsResponse = restTemplate.exchange(
                allItemsRequest,
                new ParameterizedTypeReference<>() {}
        );

        // Assert
        assertThat(addedItemResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(addedItemResponse.getBody()).isNotNull();
        assertThat(addedItemResponse.getBody().getTitle()).isEqualTo("Buy groceries");

        assertThat(allItemsResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(allItemsResponse.getBody()).isNotNull();
        assertThat(allItemsResponse.getBody().size()).isEqualTo(1);
        assertThat(allItemsResponse.getBody().stream()).isNotNull();
        assertThat(allItemsResponse.getBody().stream().anyMatch(item -> "Buy groceries".equals(item.getTitle())));
    }

    @Test
    public void updateTodoAndGetAllItemsTest() {
        // Arrange
        UpdateTodoItemDto updateDto = new UpdateTodoItemDto("Buy groceries updated", true);
        RequestEntity<UpdateTodoItemDto> updateRequestEntity = new RequestEntity<>(updateDto, HttpMethod.PUT, URI.create(todoUrl + "/" + addedItemResponse.getBody().getId()));

        // Act
        ResponseEntity<TodoListItem> updatedItemResponse = restTemplate.exchange(
                updateRequestEntity,
                new ParameterizedTypeReference<>() {
                }
        );

        ResponseEntity<List<TodoListItem>> allItemsResponse = restTemplate.exchange(
                allItemsRequest,
                new ParameterizedTypeReference<>() {}
        );

        // Assert
        assertThat(updatedItemResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(updatedItemResponse.getBody()).isNotNull();
        assertThat(updatedItemResponse.getBody().getTitle()).isEqualTo("Buy groceries updated");

        assertThat(allItemsResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(allItemsResponse.getBody()).isNotNull();
        assertThat(allItemsResponse.getBody().size()).isEqualTo(1);
        assertThat(allItemsResponse.getBody().stream()).isNotNull();
        assertThat(allItemsResponse.getBody().stream().anyMatch(item -> "Buy groceries updated".equals(item.getTitle())));
    }

    @Test
    public void updateCompletionStatusAndGetAllItemsTest() {
        // Arrange
        UpdateTodoItemDto updateDto = new UpdateTodoItemDto("Buy groceries updated", true);
        RequestEntity<UpdateTodoItemDto> updateRequestEntity = new RequestEntity<>(updateDto, HttpMethod.PUT, URI.create(todoUrl + "/" + addedItemResponse.getBody().getId()));

        // Act
        ResponseEntity<TodoListItem> updatedItemResponse = restTemplate.exchange(
                updateRequestEntity,
                new ParameterizedTypeReference<>() {}
        );

        ResponseEntity<List<TodoListItem>> allItemsResponse = restTemplate.exchange(
                allItemsRequest,
                new ParameterizedTypeReference<>() {}
        );

        // Assert
        assertThat(updatedItemResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(updatedItemResponse.getBody().isCompleted()).isTrue();

        assertThat(allItemsResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(allItemsResponse.getBody()).isNotNull();
        assertThat(allItemsResponse.getBody().size()).isEqualTo(1);
        assertThat(allItemsResponse.getBody().stream().anyMatch(item -> true == item.isCompleted()));
    }

    @Test
    public void deleteAndGetAllItemsTest() {
        // Arrange
        RequestEntity<?> deleteRequestEntity = new RequestEntity<>(HttpMethod.DELETE, URI.create(todoUrl + "/" + addedItemResponse.getBody().getId()));

        // Act
        ResponseEntity<List<TodoListItem>> oldItemsResponse = restTemplate.exchange(
                allItemsRequest,
                new ParameterizedTypeReference<>() {}
        );

        ResponseEntity<String> deletedResponse = restTemplate.exchange(
                deleteRequestEntity,
                new ParameterizedTypeReference<>() {}
        );

        ResponseEntity<List<TodoListItem>> itemsAfterDeleteResponse = restTemplate.exchange(
                allItemsRequest,
                new ParameterizedTypeReference<>() {}
        );

        // Assert
        assertThat(deletedResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(oldItemsResponse.getBody().size()).isEqualTo(itemsAfterDeleteResponse.getBody().size() + 1);
    }

    /// Private Methods

    private ResponseEntity<TodoListItem> addTodoItem(String title) {
        TodoListItemDto dto = new TodoListItemDto(title);
        return restTemplate.exchange(
                new RequestEntity<>(dto, HttpMethod.POST, URI.create(todoUrl)),
                new ParameterizedTypeReference<>() {}
        );
    }
}
