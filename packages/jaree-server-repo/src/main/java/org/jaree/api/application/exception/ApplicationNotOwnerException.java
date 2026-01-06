package org.jaree.api.application.exception;

import org.jaree.api.common.exception.BaseException;

public class ApplicationNotOwnerException extends BaseException {
    public ApplicationNotOwnerException() { super(ApplicationErrorCode.APPLICATION_NOT_OWNER); }
}
