package com.demo.task.manager.service;

import com.demo.task.manager.dto.TaskDto;
import com.demo.task.manager.model.Project;
import com.demo.task.manager.model.Task;
import com.demo.task.manager.model.User;
import com.demo.task.manager.repository.ProjectRepository;
import com.demo.task.manager.repository.TaskRepository;
import com.demo.task.manager.utility.Utility;
import com.demo.task.manager.validation.ValidationMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class TasksService {
    @Autowired
    TaskRepository taskRepository;
    @Autowired
    ProjectRepository projectRepository;

    public ResponseEntity<?> saveTask(User user, TaskDto taskDto) {
        ValidationMessage valMessage = new ValidationMessage();
        try {
            Optional<Project> projectOpt = projectRepository.findById(taskDto.getProjectId());
            if (!projectOpt.isPresent()) return ResponseEntity.ok(valMessage.projectNotFound());
            Task task = new Task();
            task.setDescription(taskDto.getDescription().trim());
            task.setStatus(taskDto.getStatus());
            if (!taskDto.getDueDate().isEmpty() || taskDto.getDueDate() != null) {
                task.setDueDate(Utility.stringToDate(taskDto.getDueDate()));
            }
            task.setUser(user);
            task.setProject(projectOpt.get());
            taskRepository.save(task);
            return ResponseEntity.ok(valMessage.saveMessage("Task"));
        } catch (Exception e) {
            return ResponseEntity.ok(valMessage.exceptionMessage());
        }

    }

    public ResponseEntity<?> getAllTask(User user) {
        ValidationMessage valMessage = new ValidationMessage();
        try {
            List<Map<String, Object>> taskListMap = taskRepository.findByUserIdOrderByIdDesc(user.getId()).stream().map(val -> {
                Map<String, Object> map = new HashMap<>();
                map.put("id", val.getId());
                map.put("description", val.getDescription());
                map.put("status", val.getStatus());
                map.put("dueDate", val.getDueDate());
                map.put("projectId", val.getProject().getId());
                map.put("projectName", val.getProject().getName());
                return map;
            }).collect(Collectors.toList());
            return ResponseEntity.ok(taskListMap);
        } catch (Exception e) {
            return ResponseEntity.ok(valMessage.exceptionMessage());
        }
    }

    public ResponseEntity<?> getSingleTask(User user, Long id) {
        ValidationMessage valMessage = new ValidationMessage();
        try {
            Optional<Task> taskOpt = taskRepository.findByUserIdAndId(user.getId(), id);
            if (!taskOpt.isPresent()) {
                return ResponseEntity.ok(valMessage.dataNotFound());
            } else {
                TaskDto taskDto = new TaskDto();
                taskDto.setId(taskOpt.get().getId());
                taskDto.setDescription(taskOpt.get().getDescription());
                taskDto.setStatus(taskOpt.get().getStatus());
                taskDto.setDueDate(taskOpt.get().getDueDate().toString());
                taskDto.setProjectId(taskOpt.get().getProject().getId());
                taskDto.setProjectName(taskOpt.get().getProject().getName());
                return ResponseEntity.ok(taskDto);
            }

        } catch (Exception e) {
            return ResponseEntity.ok(valMessage.exceptionMessage());
        }
    }

    public ResponseEntity<?> updateTask(TaskDto taskDto) {
        ValidationMessage valMessage = new ValidationMessage();
        try {
            Optional<Task> taskOpt = taskRepository.findById(taskDto.getId());
            //checking data found or not
            if (!taskOpt.isPresent()) return ResponseEntity.ok(valMessage.dataNotFound());
            //checking task status
            if (taskOpt.get().getStatus().equals("close")) return ResponseEntity.ok(valMessage.taskCloseStatus());
            Task task = taskOpt.get();
            task.setDescription(taskDto.getDescription().trim());
            task.setStatus(taskDto.getStatus());
            if (!taskDto.getDueDate().isEmpty() || taskDto.getDueDate() != null) {
                task.setDueDate(Utility.stringToDate(taskDto.getDueDate()));
            }

            taskRepository.save(task);
            return ResponseEntity.ok(valMessage.updateMessage("Task"));
        } catch (Exception e) {
            return ResponseEntity.ok(valMessage.exceptionMessage());
        }
    }

    public ResponseEntity<?> getProjectTasks(User user, Long id) {
        ValidationMessage valMessage = new ValidationMessage();
        try {
            List<Map<String, Object>> projectTaskList = taskRepository.findByUserIdAndProjectId(user.getId(), id).
                    stream().map(val -> {
                        Map<String, Object> map = new HashMap<>();
                        map.put("id", val.getId());
                        map.put("description", val.getDescription());
                        map.put("status", val.getStatus());
                        map.put("dueDate", val.getDueDate());
                        map.put("projectId", val.getProject().getId());
                        map.put("projectName", val.getProject().getName());
                        return map;
                    }).collect(Collectors.toList());

            return ResponseEntity.ok(projectTaskList);
        } catch (Exception e) {
            return ResponseEntity.ok(valMessage.exceptionMessage());
        }
    }

    public ResponseEntity<?> getStatusTasks(User user, String status) {
        ValidationMessage valMessage = new ValidationMessage();
        try {
            List<Map<String, Object>> projectTaskList = taskRepository.findByUserIdAndStatus(user.getId(), status).
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

    public ResponseEntity<?> getDueDateTasks(User user, String date) {
        ValidationMessage valMessage = new ValidationMessage();
        try {
            List<Map<String, Object>> projectTaskList = taskRepository.findDuedateTask(user.getId(), date).
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
