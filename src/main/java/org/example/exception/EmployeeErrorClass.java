package org.example.exception;

import org.springframework.http.HttpStatus;

public class EmployeeErrorClass {
    HttpStatus code;
    String message;
    long timestamp;

    public EmployeeErrorClass(){

    }
    public EmployeeErrorClass(HttpStatus code, String message) {
        this.code = code;
        this.message = message;
        this.timestamp = System.currentTimeMillis();
    }

    public HttpStatus getCode() {
        return code;
    }

    public void setCode(HttpStatus code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "EmployeeErrorClass{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
