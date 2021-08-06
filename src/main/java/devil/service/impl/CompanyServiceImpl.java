package devil.service.impl;

import devil.common.error.BadRequestException;
import devil.controller.request.CompanyRequest;
import devil.dto.mapper.GenericMapper;
import devil.model.Company;
import devil.repository.CompanyRepository;
import devil.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CompanyServiceImpl implements CompanyService {
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private GenericMapper genericMapper;
    @Override
    public List<Company> getAll() {
        return companyRepository.findAll();
    }

    @Override
    public Company findById(Long id) {
        Optional<Company> op = companyRepository.findById(id);
        if(!op.isPresent())
            throw new BadRequestException("Company id does not exit!");
        return op.get();
    }

    @Override
    public Company createOrUpdate(CompanyRequest request) {
        // id nulll create
        if(request.getId()==null){
            return companyRepository.save(genericMapper.mapToType(request,Company.class));
        }
        Optional<Company> op = companyRepository.findById(request.getId());
        if(!op.isPresent())
            throw new BadRequestException("Company id does not exit!");
        Company company = op.get();
        if(request.getAddress()!=null){
            company.setAddress(request.getAddress());
        }
        if(request.getName()!=null){
            company.setName(request.getName());
        }
        return companyRepository.save(company);
    }

    @Override
    public void deleteById(Long id) {
        Optional<Company> op = companyRepository.findById(id);
        if(!op.isPresent())
            throw new BadRequestException("Company id does not exit!");
        companyRepository.deleteById(id);
    }
}
