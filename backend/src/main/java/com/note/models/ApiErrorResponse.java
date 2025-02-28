package com.note.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ApiErrorResponse {
    Integer status;
    String message;
    List<ApiError> errors;

    public ApiErrorResponse(Integer status, String message, List<ApiError> apiErrors) {
        this.status = status;
        this.message = message;
        this.errors = apiErrors;
    }
}
