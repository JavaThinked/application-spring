package com.javathinked.application.spring.controller.exception;

import org.springframework.http.HttpStatus;

public class Message {

    private HttpStatus status;
    private String date;
    private String message;

    public Message(HttpStatus status, String date, String message) {
        this.status = status;
        this.date = date;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
