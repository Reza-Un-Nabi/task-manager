package com.demo.task.manager.repository;

import com.demo.task.manager.model.ERole;
import com.demo.task.manager.model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolesRepository extends JpaRepository<Roles,Long> {
    Optional<Roles> findByName(ERole name);

}
