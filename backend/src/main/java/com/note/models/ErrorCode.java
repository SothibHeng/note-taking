package com.note.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    MISSING_PARAMETER("missingParameter"),
    INVALID_PARAMETER("invalidParameter"),
    ALREADY_EXIST("alreadyExist"),
    EXCEED_LIMIT("exceedLimit");

    private final String code;
}
