package org.jaree.api.jobopening.exception;

import org.jaree.api.common.exception.BaseException;

public class JobOpeningNotFoundException extends BaseException {
    public JobOpeningNotFoundException() {
        super(JobOpeningErrorCode.JOB_OPENING_NOT_FOUND);
    }
}
