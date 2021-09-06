package com.demo.task.manager.playload.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class SignupRequest {

    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private Set<String> role;
    private Long organizationId;
    private String password;
    private Long branchId;
}
