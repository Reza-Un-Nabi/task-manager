package com.demo.task.manager.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class TaskDto {
    private Long id;
    private String description;
    private String status;
    private String dueDate;
    private Long projectId;
    private String ProjectName;
}
