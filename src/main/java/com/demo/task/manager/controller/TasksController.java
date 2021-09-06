package com.demo.task.manager.controller;

import com.demo.task.manager.dto.TaskDto;
import com.demo.task.manager.model.User;
import com.demo.task.manager.repository.UserRepository;
import com.demo.task.manager.service.TasksService;
import com.demo.task.manager.validation.ValidationMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/tasks/v1")
public class TasksController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    TasksService taskService;

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody TaskDto taskDto) {
        ValidationMessage valMessage = new ValidationMessage();
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(userName).get();
        if (taskDto.getDescription().isEmpty() || taskDto.getDescription() == null)
            return ResponseEntity.ok(valMessage.requiredPropertyNotFound("Task Description"));
        if (taskDto.getStatus().isEmpty() || taskDto.getStatus() == null)
            return ResponseEntity.ok(valMessage.requiredPropertyNotFound("Task Status"));
        if (taskDto.getProjectId() == 0 || taskDto.getProjectId() < 0 || taskDto.getProjectId() == null)
            return ResponseEntity.ok(valMessage.requiredPropertyNotFound("Project Id"));
        else
            return taskService.saveTask(user, taskDto);
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateTask(@RequestBody TaskDto taskDto) {
        ValidationMessage valMessage = new ValidationMessage();
        if (taskDto.getId() == 0 || taskDto.getId() < 0)
            return ResponseEntity.ok(valMessage.requiredPropertyNotFound("Task Id"));
        if (taskDto.getDescription().isEmpty() || taskDto.getDescription() == null)
            return ResponseEntity.ok(valMessage.requiredPropertyNotFound("Task Description"));
        if (taskDto.getStatus().isEmpty() || taskDto.getStatus() == null)
            return ResponseEntity.ok(valMessage.requiredPropertyNotFound("Task Status"));
        else
            return taskService.updateTask(taskDto);
    }


    @GetMapping("/single/{id}")
    public ResponseEntity<?> singleTask(@PathVariable("id") Long id) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(userName).get();
        return taskService.getSingleTask(user, id);
    }

    @GetMapping("/all")
    public ResponseEntity<?> allTask() {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(userName).get();
        return taskService.getAllTask(user);
    }

    @GetMapping("/tasks/{id}")
    public ResponseEntity<?> projectTask(@PathVariable("id") Long id) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(userName).get();
        return taskService.getProjectTasks(user, id);
    }

    @GetMapping("/tasks/status/{status}")
    public ResponseEntity<?> statusTask(@PathVariable("status") String status) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(userName).get();
        return taskService.getStatusTasks(user, status);
    }

    @GetMapping("/tasks/duedate/{date}")
    public ResponseEntity<?> dueDateTask(@PathVariable("date") String date) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(userName).get();
        return taskService.getDueDateTasks(user, date);
    }

}
