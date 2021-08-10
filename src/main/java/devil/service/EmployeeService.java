package devil.service;

import devil.controller.request.EmployeeCreateRequest;
import devil.controller.request.EmployeeUpdateRequest;
import devil.dto.EmployeeDto;
import devil.dto.EmployeeInfoDto;
import devil.dto.SignInDto;
import devil.model.EmployeeInfo;

import java.util.List;

public interface EmployeeService {
    EmployeeDto signUp(EmployeeCreateRequest request);
    SignInDto signIn(EmployeeCreateRequest request);
    List<EmployeeInfoDto> filter(String key);
    List<EmployeeInfoDto> getAll();
    EmployeeDto update(EmployeeUpdateRequest request);
    void deletebyId(Long id);
}
