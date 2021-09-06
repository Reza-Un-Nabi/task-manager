package com.demo.task.manager.service;

import com.demo.task.manager.repository.ProjectRepository;
import com.demo.task.manager.repository.TaskRepository;
import com.demo.task.manager.validation.ValidationMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class AdminService {

    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    TaskRepository taskRepository;

    public ResponseEntity<?> getUserProjects(Long id) {
        ValidationMessage valMessage = new ValidationMessage();
        try {
            List<Map<String, Object>> projectTaskList = projectRepository.findByUserId(id).
                    stream().map(val -> {
                        Map<String, Object> map = new HashMap<>();
                        map.put("id", val.getId());
                        map.put("name", val.getName());
                        return map;
                    }).collect(Collectors.toList());

            return ResponseEntity.ok(projectTaskList);
        } catch (Exception e) {
            return ResponseEntity.ok(valMessage.exceptionMessage());
        }
    }

    public ResponseEntity<?> getUserTasks(Long id) {
        ValidationMessage valMessage = new ValidationMessage();
        try {
            List<Map<String, Object>> projectTaskList = taskRepository.findByUserId(id).
                    stream().map(val -> {
                        Map<String, Object> map = new HashMap<>();
                        map.put("id", val.getId());
                        map.put("description", val.getDescription());
                        map.put("dueDate", val.getDueDate());
                        map.put("status", val.getStatus());
                        map.put("projectId", val.getProject().getId());
                        map.put("projectName", val.getProject().getName());
                        return map;
                    }).collect(Collectors.toList());

            return ResponseEntity.ok(projectTaskList);
        } catch (Exception e) {
            return ResponseEntity.ok(valMessage.exceptionMessage());
        }
    }
}
