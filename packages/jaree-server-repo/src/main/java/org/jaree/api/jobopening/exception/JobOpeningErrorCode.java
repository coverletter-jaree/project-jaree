package org.jaree.api.jobopening.exception;

import org.jaree.api.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum JobOpeningErrorCode implements ErrorCode {
    JOB_OPENING_NOT_FOUND(HttpStatus.NOT_FOUND, "JobOpening_404_1", "JobOening not found");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
