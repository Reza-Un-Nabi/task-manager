package com.demo.task.manager.service;

import com.demo.task.manager.dto.ProjectDto;
import com.demo.task.manager.model.Project;
import com.demo.task.manager.model.Task;
import com.demo.task.manager.model.User;
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
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProjectService {

    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    TaskRepository taskRepository;

    public ResponseEntity<?> saveProject(User user, ProjectDto projectDto) {
        ValidationMessage valMessage = new ValidationMessage();
        try {

            //getting data by project name.
            Optional<Project> projectOpt = projectRepository.findByName(projectDto.getName().trim());
            // checking project name exit or name
            if (projectOpt.isPresent()) {
                return ResponseEntity.ok(valMessage.duplicateNameValidation(projectDto.getName().trim()));
            } else {
                Project project = new Project();
                project.setName(projectDto.getName().trim());
                project.setUser(user);
                projectRepository.save(project);
                return ResponseEntity.ok(valMessage.saveMessage("Project"));
            }
        } catch (Exception e) {
            return ResponseEntity.ok(valMessage.exceptionMessage());
        }
    }


    public ResponseEntity<?> getAllProject(User user) {
        ValidationMessage valMessage = new ValidationMessage();
        try {
            List<Map<String, Object>> projectListMap = projectRepository.findByUserId(user.getId()).stream().map(val -> {
                Map<String, Object> map = new HashMap<>();
                map.put("id", val.getId());
                map.put("name", val.getName());
                return map;
            }).collect(Collectors.toList());


            return ResponseEntity.ok(projectListMap);
        } catch (Exception e) {
            return ResponseEntity.ok(valMessage.exceptionMessage());
        }
    }


    public ResponseEntity<?> deleteProject(Long id) {
        ValidationMessage valMessage = new ValidationMessage();
        try {

            List<Task> taskList = taskRepository.findByProjectId(id);
            if (taskList.size() > 0) return ResponseEntity.ok(valMessage.projectDeleteValidationMessage());

            projectRepository.deleteById(id);
            return ResponseEntity.ok(valMessage.deleteMessage());
        } catch (Exception e) {
            return ResponseEntity.ok(valMessage.exceptionMessage());
        }
    }
}
