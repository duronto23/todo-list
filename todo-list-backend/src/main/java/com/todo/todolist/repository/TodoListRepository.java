package com.todo.todolist.repository;

import com.todo.todolist.model.TodoListItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoListRepository extends JpaRepository<TodoListItem, Integer> {

}
