package com.dk.springbootwebtutorial.Advices;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class APIResponse<T>{

    @JsonFormat(pattern = "hh:mm:ss dd-MM-yyyy") // initially it is in time zone format
    private LocalDateTime timeStamp;
    private T data;
    private APIError error;

    public APIResponse() {
        this.timeStamp = LocalDateTime.now();
    }

    public APIResponse(T data) {
        this();   // calling default constructor
        this.data = data;
    }

    public APIResponse(APIError error) {
        this();
        this.error = error;
    }
}
