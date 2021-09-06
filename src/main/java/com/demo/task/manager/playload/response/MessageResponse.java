package com.demo.task.manager.playload.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MessageResponse {

    String message;
    String type;
    String status;
    Long id;
}
