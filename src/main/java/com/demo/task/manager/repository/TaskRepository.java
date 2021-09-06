package com.demo.task.manager.repository;


import com.demo.task.manager.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task,Long> {
    @Query(value = "Select * from task Where name LIKE %?1%",nativeQuery = true)
    Optional<Task> findByName(String name);
    Optional<Task> findByUserIdAndId(Long userId,Long id);
    List<Task> findByUserId(Long userId);
    List<Task> findByUserIdOrderByIdDesc(Long userId);
    List<Task> findByUserIdAndProjectId(Long userId,Long projectId);
    List<Task> findByUserIdAndStatus(Long userId,String status);
    @Query(value = "select * from task where user =?1 and due_date < ?2 ",nativeQuery = true)
    List<Task> findDuedateTask(Long userId,String date);
    List<Task> findByProjectId(Long id);
}
