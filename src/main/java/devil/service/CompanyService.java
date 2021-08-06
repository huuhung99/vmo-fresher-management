package devil.service;

import devil.controller.request.CompanyRequest;
import devil.model.Company;

import java.util.List;

public interface CompanyService {
    List<Company> getAll();
    Company findById(Long id);
    Company createOrUpdate(CompanyRequest request);
    void deleteById(Long id);
}
