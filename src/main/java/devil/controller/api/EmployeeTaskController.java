package devil.controller.api;

import devil.common.enums.ResponseCodeEnum;
import devil.controller.request.EmployeeTaskRequest;
import devil.controller.response.ResponseBodyDto;
import devil.dto.AverageScoreDto;
import devil.dto.EmployeeTaskDto;
import devil.service.EmployeeTaskService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee-task")
@Api(tags = "Employee-Task")
public class EmployeeTaskController {
    @Autowired
    private EmployeeTaskService employeeTaskService;
    @GetMapping("/{id}")
    public ResponseEntity<ResponseBodyDto<List<EmployeeTaskDto>>> getAll(@PathVariable Long id){
        List<EmployeeTaskDto> employeeTaskDtos = employeeTaskService.getAllByEmployeeId(id);
        ResponseBodyDto response=new ResponseBodyDto(employeeTaskDtos, ResponseCodeEnum.R_200,"OK",employeeTaskDtos.size());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @PostMapping
    public ResponseEntity<ResponseBodyDto<EmployeeTaskDto>> createOrUpdate(@RequestBody EmployeeTaskRequest request){
        EmployeeTaskDto employeeTaskDto = employeeTaskService.createOrUpdate(request);
        ResponseBodyDto response=new ResponseBodyDto(employeeTaskDto, ResponseCodeEnum.R_201,"Create");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @GetMapping("/score")
    public ResponseEntity<ResponseBodyDto<List<AverageScoreDto>>> getAllAverageScore(){
        List<AverageScoreDto> averageScoreDtos = employeeTaskService.getAllAverageScore();
        ResponseBodyDto response=new ResponseBodyDto(averageScoreDtos, ResponseCodeEnum.R_200,"OK",averageScoreDtos.size());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @GetMapping("/score/{id}")
    public ResponseEntity<ResponseBodyDto<List<AverageScoreDto>>> getAverageScoreByEmployeeId(@PathVariable Long id){
        AverageScoreDto averageScoreDto = employeeTaskService.getAverageScoreByEmployeeId(id);
        ResponseBodyDto response=new ResponseBodyDto(averageScoreDto, ResponseCodeEnum.R_200,"OK");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
