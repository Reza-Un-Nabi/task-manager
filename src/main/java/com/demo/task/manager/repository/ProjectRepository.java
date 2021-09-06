package com.demo.task.manager.repository;

import com.demo.task.manager.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project,Long> {
    @Query(value = "Select * from project Where name LIKE %?1%",nativeQuery = true)
    Optional<Project> findByName(String name);

    List<Project> findByUserId(Long userId);
}
