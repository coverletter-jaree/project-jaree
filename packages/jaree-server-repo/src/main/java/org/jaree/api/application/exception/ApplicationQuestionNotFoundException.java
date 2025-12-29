package org.jaree.api.application.exception;

import org.jaree.api.common.exception.BaseException;

public class ApplicationQuestionNotFoundException extends BaseException {
    public ApplicationQuestionNotFoundException() {
        super(ApplicationErrorCode.APPLICATION_QUESTION_NOT_FOUND);
    }
}
