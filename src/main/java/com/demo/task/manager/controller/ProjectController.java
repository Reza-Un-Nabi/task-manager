package com.demo.task.manager.controller;

import com.demo.task.manager.dto.ProjectDto;
import com.demo.task.manager.model.User;
import com.demo.task.manager.repository.UserRepository;
import com.demo.task.manager.service.ProjectService;
import com.demo.task.manager.validation.ValidationMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*",maxAge = 3600)
@RestController
@RequestMapping("/project/v1")
public class ProjectController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProjectService projectService;

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody ProjectDto projectDto){
        ValidationMessage validationMessage = new ValidationMessage();
        if(projectDto.getName().isEmpty() || projectDto.getName()== null) {
            return ResponseEntity.ok(validationMessage.nameValidation(projectDto.getName()));
        }
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(userName).get();
        return  projectService.saveProject(user,projectDto);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id){
        return  projectService.deleteProject(id);
    }

// Getting All Task That User Created.
    @GetMapping("/all")
    public ResponseEntity<?> allProject() {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(userName).get();
        return projectService.getAllProject(user);
    }
}
