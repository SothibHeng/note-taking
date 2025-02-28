package com.note.models;

import lombok.Data;

@Data
public class ApiError {
    String code;
    String message;
    String location;

    public ApiError(ErrorCode code, String message, String location) {
        this.code = code.getCode();
        this.message = message;
        this.location = location;
    }
}
