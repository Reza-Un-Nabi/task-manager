package com.demo.task.manager.controller;

import com.demo.task.manager.model.User;
import com.demo.task.manager.repository.ProjectRepository;
import com.demo.task.manager.repository.UserRepository;
import com.demo.task.manager.service.AdminService;
import com.demo.task.manager.service.ProjectService;
import com.demo.task.manager.service.TasksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/admin/v1")
public class AdminController {

    @Autowired
    AdminService adminService;

    //Getting All Project of The Selected User.
    @GetMapping("/user/project/{id}")
    public ResponseEntity<?> userProject(@PathVariable("id") Long id) {

        return adminService.getUserProjects(id);
    }

    //Getting All Task of The Selected User.
    @GetMapping("/user/task/{id}")
    public ResponseEntity<?> userTask(@PathVariable("id") Long id) {

        return adminService.getUserTasks(id);
    }
}
