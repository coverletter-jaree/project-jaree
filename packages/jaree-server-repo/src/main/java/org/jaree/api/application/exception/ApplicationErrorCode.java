package org.jaree.api.application.exception;

import org.jaree.api.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ApplicationErrorCode implements ErrorCode {
    APPLICATION_NOT_FOUND(HttpStatus.NOT_FOUND, "APPLICATION_404_1", "Application not found"),
    APPLICATION_QUESTION_NOT_FOUND(HttpStatus.NOT_FOUND, "APPLICATION_QUESTION_404_1", "Application Question not found"),
    APPLICATION_VERSION_NOT_FOUND(HttpStatus.NOT_FOUND, "APPLICATION_VERSION_404_1", "Application Version not found"),
    APPLICATION_NOT_OWNER(HttpStatus.FORBIDDEN, "APPLICATION_403_1", "User is not an owner of this application");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
