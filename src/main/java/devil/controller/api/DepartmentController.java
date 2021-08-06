package devil.controller.api;

import devil.common.enums.ResponseCodeEnum;
import devil.controller.request.AddEmployeeRequest;
import devil.controller.request.DepartmentRequest;
import devil.controller.response.ResponseBodyDto;
import devil.dto.DepartmentDto;
import devil.dto.EmployeeInfoDto;
import devil.dto.EmployeeStatisticsByDepartment;
import devil.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/department")
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;
    @GetMapping
    public ResponseEntity<ResponseBodyDto<List<DepartmentDto>>> getAll(){
        List<DepartmentDto> departmentDtos = departmentService.getAll();
        ResponseBodyDto response= new ResponseBodyDto(departmentDtos, ResponseCodeEnum.R_200,"OK", departmentDtos.size());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @PostMapping
    public ResponseEntity<ResponseBodyDto<DepartmentDto>> createOrUpdate(@RequestBody DepartmentRequest request){
        DepartmentDto departmentDto = departmentService.createOrUpdate(request);
        ResponseBodyDto response= new ResponseBodyDto(departmentDto, ResponseCodeEnum.R_201,"Create");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @PostMapping("/add-employee")
    public ResponseEntity<ResponseBodyDto<DepartmentDto>> addEmployee(@RequestBody AddEmployeeRequest request){
        EmployeeInfoDto employeeInfoDto = departmentService.addEmployee(request);
        ResponseBodyDto response= new ResponseBodyDto(employeeInfoDto, ResponseCodeEnum.R_201,"Create");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseBodyDto<DepartmentDto>> deleteById(@PathVariable Long id){
        departmentService.deleteById(id);
        ResponseBodyDto<DepartmentDto> response=new ResponseBodyDto();
        response.setItem(null).setCode(ResponseCodeEnum.R_200).setMessage("OK");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @GetMapping("/statistics/{id}")
    public ResponseEntity<ResponseBodyDto<EmployeeStatisticsByDepartment>> employeeStatisticsByDepartment(@PathVariable Long id){
        EmployeeStatisticsByDepartment employeeStatisticsByDepartment = departmentService.employeeStatisticsByDepartment(id);
        ResponseBodyDto response= new ResponseBodyDto(employeeStatisticsByDepartment, ResponseCodeEnum.R_200,"OK");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @GetMapping("/statistics")
    public ResponseEntity<ResponseBodyDto<List<EmployeeStatisticsByDepartment>>> employeeStatistics(){
        List<EmployeeStatisticsByDepartment> employeeStatisticsByDepartments = departmentService.employeeStatistics();
        ResponseBodyDto response= new ResponseBodyDto(employeeStatisticsByDepartments, ResponseCodeEnum.R_200,"OK",employeeStatisticsByDepartments.size());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
