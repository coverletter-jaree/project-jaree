package org.jaree.api.user.exception;

import org.jaree.api.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserErrorCode implements ErrorCode {
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER_404_1", "User not found");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
