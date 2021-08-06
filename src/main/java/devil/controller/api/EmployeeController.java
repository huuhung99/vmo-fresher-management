package devil.controller.api;

import devil.common.enums.ResponseCodeEnum;
import devil.controller.request.EmployeeCreateRequest;
import devil.controller.request.EmployeeUpdateRequest;
import devil.controller.response.ResponseBodyDto;
import devil.dto.EmployeeDto;
import devil.dto.EmployeeInfoDto;
import devil.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;
    @GetMapping
    public ResponseEntity<ResponseBodyDto<List<EmployeeInfoDto>>> getAll(){
        List<EmployeeInfoDto> employeeInfoDtos = employeeService.getAll();
        ResponseBodyDto response= new ResponseBodyDto(employeeInfoDtos, ResponseCodeEnum.R_200,"OK", employeeInfoDtos.size());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @PostMapping("/sign-in")
    public ResponseEntity<ResponseBodyDto<String>> signIn(@RequestBody EmployeeCreateRequest request){
        String s = employeeService.signIn(request);
        ResponseBodyDto response= new ResponseBodyDto(s,ResponseCodeEnum.R_200,"OK");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @PostMapping("/sign-up")
    public ResponseEntity<ResponseBodyDto<EmployeeDto>> signUp(@RequestBody EmployeeCreateRequest request){
        EmployeeDto employeeDto = employeeService.signUp(request);
        ResponseBodyDto response= new ResponseBodyDto(employeeDto,ResponseCodeEnum.R_201,"Create");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @PutMapping
    public ResponseEntity<ResponseBodyDto<EmployeeDto>> update(@RequestBody EmployeeUpdateRequest request){
        EmployeeDto employeeDto = employeeService.update(request);
        ResponseBodyDto response= new ResponseBodyDto(employeeDto,ResponseCodeEnum.R_200,"OK");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseBodyDto<EmployeeDto>> delete(@PathVariable Long id){
        employeeService.deletebyId(id);
        ResponseBodyDto<EmployeeDto> response=new ResponseBodyDto<>();
        response.setItem(null).setCode(ResponseCodeEnum.R_200).setMessage("OK");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @GetMapping("/{key}")
    public ResponseEntity<ResponseBodyDto<List<EmployeeInfoDto>>> filter(@PathVariable String key){
        List<EmployeeInfoDto> employeeInfoDtos = employeeService.filter(key);
        ResponseBodyDto response= new ResponseBodyDto(employeeInfoDtos, ResponseCodeEnum.R_200,"OK", employeeInfoDtos.size());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
