package com.demo.task.manager.validation;

import com.demo.task.manager.dto.ResponseMessageDto;

public class ValidationMessage {

    public ResponseMessageDto saveMessage(String name) {
        ResponseMessageDto repMessage = new ResponseMessageDto();
        repMessage.setStatus("ok");
        repMessage.setMessage(name + "  Save Successfully");
        return repMessage;
    }

    public ResponseMessageDto updateMessage(String name) {
        ResponseMessageDto repMessage = new ResponseMessageDto();
        repMessage.setStatus("ok");
        repMessage.setMessage(name + "  Update Successfully");
        return repMessage;
    }

    public ResponseMessageDto deleteMessage() {
        ResponseMessageDto repMessage = new ResponseMessageDto();
        repMessage.setStatus("ok");
        repMessage.setMessage( "Project Delete Successfully");
        return repMessage;
    }

    public ResponseMessageDto taskCloseStatus() {
        ResponseMessageDto repMessage = new ResponseMessageDto();
        repMessage.setStatus("failed");
        repMessage.setMessage("Task Is Closed. Can Not Update Task");
        return repMessage;
    }

    public ResponseMessageDto requiredPropertyNotFound(String name) {
        ResponseMessageDto repMessage = new ResponseMessageDto();
        repMessage.setStatus("failed");
        repMessage.setMessage(name + "  Is Required.");
        return repMessage;
    }

    public ResponseMessageDto projectNotFound() {
        ResponseMessageDto repMessage = new ResponseMessageDto();
        repMessage.setStatus("failed");
        repMessage.setMessage("Project Not Found.");
        return repMessage;
    }

    public ResponseMessageDto dataNotFound() {
        ResponseMessageDto repMessage = new ResponseMessageDto();
        repMessage.setStatus("failed");
        repMessage.setMessage("Data Not Found.");
        return repMessage;
    }

    public ResponseMessageDto nameValidation(String name) {
        ResponseMessageDto repMessage = new ResponseMessageDto();
        repMessage.setStatus("failed");
        repMessage.setMessage(name + "  Is Required.");
        return repMessage;
    }

    public ResponseMessageDto duplicateNameValidation(String name) {
        ResponseMessageDto repMessage = new ResponseMessageDto();
        repMessage.setStatus("failed");
        repMessage.setMessage(name + "  Already Exit.");
        return repMessage;
    }

    public ResponseMessageDto exceptionMessage() {
        ResponseMessageDto repMessage = new ResponseMessageDto();
        repMessage.setStatus("failed");
        repMessage.setMessage("Please Try Later.");
        return repMessage;
    }

    public ResponseMessageDto projectDeleteValidationMessage() {
        ResponseMessageDto repMessage = new ResponseMessageDto();
        repMessage.setStatus("failed");
        repMessage.setMessage("Some Task Are Remain of This Project.");
        return repMessage;
    }
}
