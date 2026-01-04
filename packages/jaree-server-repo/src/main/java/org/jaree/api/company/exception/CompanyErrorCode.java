package org.jaree.api.company.exception;

import org.jaree.api.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CompanyErrorCode implements ErrorCode {
    COMPANY_NOT_FOUND(HttpStatus.NOT_FOUND, "COMPANY_404_1", "Company not found");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
