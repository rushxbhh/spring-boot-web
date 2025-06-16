package com.springbootweb.spring.boot.web.advices;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class ApiResponse<T> {

    @JsonFormat(pattern = "hh-mm-ss dd-mm-yyyy")
    private LocalDateTime timestamp;
    private T data;
    private ApiError error;

    public ApiResponse(){
        this.timestamp = LocalDateTime.now();
    }
    public ApiResponse(T data){
        this();
        this.data = data;
    }
    public ApiResponse(ApiError error){
        this();
        this.error = error;
    }


}
