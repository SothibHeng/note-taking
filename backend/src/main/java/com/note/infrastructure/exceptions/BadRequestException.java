package com.note.infrastructure.exceptions;

import com.note.models.ApiError;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class BadRequestException extends RuntimeException {

    private final List<ApiError> apiErrors;

    public BadRequestException(List<ApiError> apiErrors) {
        super("Bad Request");
        this.apiErrors = apiErrors;
    }
}
