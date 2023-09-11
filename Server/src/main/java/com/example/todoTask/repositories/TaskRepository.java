package com.example.todoTask.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.todoTask.models.Task;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByUser_Id(Long id);

//    @Query(value = "SELECT T FROM Task t WHERE t.user.id = :id")
//    List<Task> findByUser_id(@Param("id") Long id);

//    @Query(value = "SELECT * FROM TB_TASK t WHERE t.user_id = :id", nativeQuery = true)
//    List<Task> findByUser_id(@Param("id") Long id);

}
