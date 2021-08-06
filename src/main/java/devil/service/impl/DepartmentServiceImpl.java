package devil.service.impl;

import devil.common.error.BadRequestException;
import devil.controller.request.AddEmployeeRequest;
import devil.controller.request.DepartmentRequest;
import devil.dto.DepartmentDto;
import devil.dto.EmployeeInfoDto;
import devil.dto.EmployeeStatisticsByDepartment;
import devil.dto.mapper.GenericMapper;
import devil.model.Company;
import devil.model.Department;
import devil.model.Employee;
import devil.model.EmployeeInfo;
import devil.repository.CompanyRepository;
import devil.repository.DepartmentRepository;
import devil.repository.EmployeeInfoRepository;
import devil.repository.EmployeeRepository;
import devil.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class DepartmentServiceImpl implements DepartmentService {
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    GenericMapper genericMapper;
    @Autowired
    private EmployeeInfoRepository employeeInfoRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private CompanyRepository companyRepository;
    @Override
    public List<DepartmentDto> getAll() {
        return genericMapper.mapToListOfType(departmentRepository.findAll(),DepartmentDto.class);
    }

    @Override
    public DepartmentDto createOrUpdate(DepartmentRequest request) {
        // id null create
        if(request.getId()==null){
            Department department = genericMapper.mapToType(request, Department.class);
            if(request.getCompanyId()==null)
                throw new BadRequestException("Company id can not be null");
            Optional<Company> opCompany = companyRepository.findById(request.getCompanyId());
            if(!opCompany.isPresent()){
                throw new BadRequestException("Company does not exit!");
            }
            department.setCompany(opCompany.get());
            if(request.getManagerId()==null){
                throw new BadRequestException("Manager id can not be null!");
            }
            Optional<Employee> opManager = employeeRepository.findById(request.getManagerId());
            if(!opManager.isPresent()){
                throw new BadRequestException("Manager does not exit!");
            }
            department.setManager(opManager.get());
            return genericMapper.mapToType(departmentRepository.save(department),DepartmentDto.class);
        }
        // update
        Optional<Department> op = departmentRepository.findById(request.getId());
        if(!op.isPresent()){
            throw new BadRequestException("department id does not exit!");
        }
        Department department = op.get();
        if(request.getManagerId()!=null){
            Optional<Employee> opEmployee = employeeRepository.findById(request.getManagerId());
            if(!op.isPresent()){
                throw new BadRequestException("manager id does not exit!");
            }
            department.setManager(opEmployee.get());
        }
        if(request.getCompanyId()!=null){
            Optional<Company> opCompany = companyRepository.findById(request.getCompanyId());
            if(!opCompany.isPresent()){
                throw new BadRequestException("Company id does not exit!");
            }
            department.setCompany(opCompany.get());
        }
        if(request.getName()!=null){
            department.setName(request.getName());
        }
        if(request.getLocations()!=null){
            department.setLocations(request.getLocations());
        }
        return genericMapper.mapToType(departmentRepository.save(department),DepartmentDto.class);
    }

    @Override
    public void deleteById(Long id) {
        Optional<Department> op = departmentRepository.findById(id);
        if(!op.isPresent()){
            throw new BadRequestException("department id does not exit!");
        }
        List<EmployeeInfo> employeeInfos = employeeInfoRepository.findEmployeeInfoByDepartmentId(op.get().getId());
        for (EmployeeInfo e : employeeInfos) {
            e.setDepartment(null);
        }
        employeeInfoRepository.saveAll(employeeInfos);
        departmentRepository.deleteById(id);
    }

    @Override
    public EmployeeInfoDto addEmployee(AddEmployeeRequest request) {
        Optional<Department> opDepartment = departmentRepository.findById(request.getDepartmentId());
        if(!opDepartment.isPresent()){
            throw new BadRequestException("department does not exit!");
        }
        EmployeeInfo employeeInfo = employeeInfoRepository.findEmployeeInfoByEmployeeId(request.getEmployeeId());
        if(employeeInfo==null){
            throw new BadRequestException("employee does not exit!");
        }
        employeeInfo.setDepartment(opDepartment.get());
        employeeInfo.setLanguage(request.getLanguage());
        if(request.getSpecialization()!=null){
            employeeInfo.setSpecialization(request.getSpecialization());
        }
        return genericMapper.mapToType(employeeInfoRepository.save(employeeInfo),EmployeeInfoDto.class);
    }

    @Override
    public EmployeeStatisticsByDepartment employeeStatisticsByDepartment(Long departmentId) {
        Optional<Department> opDepartment = departmentRepository.findById(departmentId);
        if(!opDepartment.isPresent()){
            throw new BadRequestException("department id invalid!");
        }
        DepartmentDto departmentDto = genericMapper.mapToType(opDepartment.get(), DepartmentDto.class);
        EmployeeStatisticsByDepartment employeeStatisticsByDepartment=new EmployeeStatisticsByDepartment();
        employeeStatisticsByDepartment.setDepartmentDto(departmentDto);
        List<EmployeeInfo> employeeInfos = employeeInfoRepository.findEmployeeInfoByDepartmentId(departmentId);
        if(employeeInfos.isEmpty()){
            return employeeStatisticsByDepartment;
        }
        employeeStatisticsByDepartment.setEmployeeInfoDtos(genericMapper.mapToListOfType(employeeInfos,EmployeeInfoDto.class));
        return employeeStatisticsByDepartment;
    }

    @Override
    public List<EmployeeStatisticsByDepartment> employeeStatistics() {
        Map<Department, List<EmployeeInfo>> filter = employeeInfoRepository.findAll()
                .stream().collect(Collectors.groupingBy(EmployeeInfo::getDepartment));
        List<EmployeeStatisticsByDepartment> employeeStatisticsByDepartments=new ArrayList<>();
        List<Department> departments = departmentRepository.findAll();
        for(int i=0;i<departments.size();i++){
            EmployeeStatisticsByDepartment employeeStatisticsByDepartment=new EmployeeStatisticsByDepartment();
            employeeStatisticsByDepartment.setDepartmentDto(genericMapper.mapToType(departments.get(i),DepartmentDto.class));
            if(filter.containsKey(departments.get(i))){
                employeeStatisticsByDepartment.setEmployeeInfoDtos(
                        genericMapper.mapToListOfType(filter.get(departments.get(i)),EmployeeInfoDto.class));
            }
            employeeStatisticsByDepartments.add(employeeStatisticsByDepartment);
        }
        return employeeStatisticsByDepartments;
    }
}
