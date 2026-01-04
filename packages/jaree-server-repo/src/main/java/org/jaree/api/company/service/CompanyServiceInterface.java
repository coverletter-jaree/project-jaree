package org.jaree.api.company.service;

import org.jaree.api.company.output.CompanyWithJobOpeningsOutputDTO;

public interface CompanyServiceInterface {

    CompanyWithJobOpeningsOutputDTO getCompanyInfo(String id);

}
