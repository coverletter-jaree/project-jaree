package org.jaree.api.company.exception;

import org.jaree.api.common.exception.BaseException;

public class CompanyNotFoundException extends BaseException {
    public CompanyNotFoundException() { super(CompanyErrorCode.COMPANY_NOT_FOUND); }
}
