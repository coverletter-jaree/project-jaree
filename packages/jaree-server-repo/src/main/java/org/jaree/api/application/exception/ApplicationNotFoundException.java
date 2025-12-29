package org.jaree.api.application.exception;

import org.jaree.api.common.exception.BaseException;

public class ApplicationNotFoundException extends BaseException {
    public ApplicationNotFoundException() {
        super(ApplicationErrorCode.APPLICATION_NOT_FOUND);
    }
}
