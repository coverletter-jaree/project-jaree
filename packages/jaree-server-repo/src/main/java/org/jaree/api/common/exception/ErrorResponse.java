package org.jaree.api.common.exception;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class ErrorResponse {
    private final Integer statusCode;
    private final String code;
    private final String message;
    private final LocalDateTime timestamp;

    public ErrorResponse(final ErrorCode errorCode) {
        this.statusCode = errorCode.getHttpStatus().value();
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
        this.timestamp = LocalDateTime.now();
    }

    public ErrorResponse(final ErrorCode errorCode, final String message) {
        this.statusCode = errorCode.getHttpStatus().value();
        this.code = errorCode.getCode();
        this.message = (message == null || message.isBlank()) ?
            errorCode.getMessage() : message;
        this.timestamp = LocalDateTime.now();
    }
}
