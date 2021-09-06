package com.demo.task.manager.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@Setter
@Getter
@NoArgsConstructor
public class ProjectDto {
    private Long id;
    private String name;
    private Long userId;

}
