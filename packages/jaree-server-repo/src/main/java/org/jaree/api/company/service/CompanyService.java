package org.jaree.api.company.service;

import org.jaree.api.company.dto.CompanyWithJobOpeningsOutputDTO;

public interface CompanyService {

    CompanyWithJobOpeningsOutputDTO getCompanyInfo(String id);

}
