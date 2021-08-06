package devil.service;


import devil.controller.request.AddEmployeeRequest;
import devil.controller.request.DepartmentRequest;
import devil.dto.AverageScoreDto;
import devil.dto.DepartmentDto;
import devil.dto.EmployeeInfoDto;
import devil.dto.EmployeeStatisticsByDepartment;

import java.util.List;

public interface DepartmentService {
    List<DepartmentDto> getAll();
    DepartmentDto createOrUpdate(DepartmentRequest request);
    void deleteById(Long id);
    EmployeeInfoDto addEmployee(AddEmployeeRequest request);
    EmployeeStatisticsByDepartment employeeStatisticsByDepartment(Long departmentId);
    List<EmployeeStatisticsByDepartment> employeeStatistics();
}
