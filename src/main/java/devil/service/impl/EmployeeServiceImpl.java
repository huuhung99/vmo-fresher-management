package devil.service.impl;

import devil.common.error.BadRequestException;
import devil.controller.request.EmployeeCreateRequest;
import devil.controller.request.EmployeeUpdateRequest;
import devil.dto.EmployeeDto;
import devil.dto.EmployeeInfoDto;
import devil.dto.SignInDto;
import devil.dto.enums.RoleEnum;
import devil.dto.mapper.GenericMapper;
import devil.model.Employee;
import devil.model.EmployeeInfo;
import devil.repository.EmployeeInfoRepository;
import devil.repository.EmployeeRepository;
import devil.repository.RoleRepository;
import devil.service.EmployeeService;
import devil.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private TokenService tokenService;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private EmployeeInfoRepository employeeInfoRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private GenericMapper genericMapper;
    @Override
    public EmployeeDto signUp(EmployeeCreateRequest request) {
        if(employeeRepository.findEmployeeByUserName(request.getUserName())!=null){
            throw new BadRequestException("user name does exit!");
        }
        Employee employee = genericMapper.mapToType(request, Employee.class);
        employeeRepository.save(employee);
        EmployeeInfo employeeInfo=new EmployeeInfo();
        employeeInfo.setEmployee(employee);
        employeeInfo.setRole(roleRepository.findRoleByName(RoleEnum.FRESHER.name()));
        employeeInfoRepository.save(employeeInfo);
        EmployeeDto employeeDto = genericMapper.mapToType(employee, EmployeeDto.class);
        return employeeDto;
    }

    @Override
    public SignInDto signIn(EmployeeCreateRequest request) {
        Employee employee = employeeRepository.findEmployeeByUserName(request.getUserName());
        if(employee==null){
            throw new BadRequestException("User name does not exit!");
        }
        if(!employee.getPassword().equals(request.getPassword())){
            throw new BadRequestException("Wrong password!");
        }
        EmployeeInfo employeeInfo = employeeInfoRepository.findEmployeeInfoByEmployeeId(employee.getId());
        SignInDto signInDto=new SignInDto();
        signInDto.setToken(tokenService.genToken(employee.getUserName(), employeeInfo.getRole()));
        signInDto.setEmployeeDto(genericMapper.mapToType(employeeInfo.getEmployee(),EmployeeDto.class));
        return signInDto;
    }

    @Override
    public List<EmployeeInfoDto> filter(String key) {
        List<EmployeeInfo> filter = employeeInfoRepository.filter(key);
        List<EmployeeInfoDto> employeeInfoDtos = genericMapper.mapToListOfType(filter, EmployeeInfoDto.class);
        for(int i=0;i<filter.size();i++){
            employeeInfoDtos.get(i).setEmployeeDto(genericMapper.mapToType(filter.get(i).getEmployee(),EmployeeDto.class));
        }
        return employeeInfoDtos;
    }

    @Override
    public List<EmployeeInfoDto> getAll() {
        List<EmployeeInfo> employeeInfos = employeeInfoRepository.findAll();
        List<EmployeeInfoDto> employeeInfoDtos = genericMapper.mapToListOfType(employeeInfos, EmployeeInfoDto.class);
        for(int i=0;i<employeeInfos.size();i++){
            EmployeeDto employeeDto=genericMapper.mapToType(employeeInfos.get(i).getEmployee(),EmployeeDto.class);
            employeeInfoDtos.get(i).setEmployeeDto(employeeDto);
        }
        return employeeInfoDtos;
    }

    @Override
    public EmployeeDto update(EmployeeUpdateRequest request) {
        Optional<Employee> op = employeeRepository.findById(request.getId());
        if(!op.isPresent()){
            throw new BadRequestException("Id does not exit!");
        }
        Employee employee = op.get();
        if(request.getAddress()!=null){
            employee.setAddress(request.getAddress());
        }
        if(request.getFirstName()!=null){
            employee.setFirstName(request.getFirstName());
        }
        if(request.getLastName()!=null){
            employee.setLastName(request.getLastName());
        }
        if(request.getBirthDay()!=null){
            employee.setBirthDay(request.getBirthDay());
        }
        if(request.getEmail()!=null){
            employee.setEmail(request.getEmail());
        }
        if(request.getPassword()!=null){
            employee.setPassword(request.getPassword());
        }
        Employee save = employeeRepository.save(employee);
        return genericMapper.mapToType(save,EmployeeDto.class);
    }

    @Override
    public void deletebyId(Long id) {
        Optional<Employee> op = employeeRepository.findById(id);
        if(op.isPresent()){
            employeeInfoRepository.deleteById(employeeInfoRepository.findEmployeeInfoByEmployeeId(op.get().getId()).getId());
            employeeRepository.deleteById(id);
            return;
        }
        throw new BadRequestException("Id does not exit");
    }
}
