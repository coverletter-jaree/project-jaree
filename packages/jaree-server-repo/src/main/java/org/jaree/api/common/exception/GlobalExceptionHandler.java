package org.jaree.api.common.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorResponse> handleBaseException(BaseException e) {
        ErrorCode ec = e.getErrorCode();
        ErrorResponse body = new ErrorResponse(ec);
        return ResponseEntity.status(ec.getHttpStatus()).body(body);
    }
}
