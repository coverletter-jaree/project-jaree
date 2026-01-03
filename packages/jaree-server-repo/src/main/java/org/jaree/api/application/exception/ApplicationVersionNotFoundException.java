package org.jaree.api.application.exception;

import org.jaree.api.common.exception.BaseException;

public class ApplicationVersionNotFoundException extends BaseException {
    public ApplicationVersionNotFoundException() {
        super(ApplicationErrorCode.APPLICATION_VERSION_NOT_FOUND);
    }
}
