package devil.controller.api;

import devil.common.enums.ResponseCodeEnum;
import devil.controller.response.ResponseBodyDto;
import devil.model.Task;
import devil.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/task")
public class TaskController {
    @Autowired
    private TaskService taskService;
    @GetMapping
    public ResponseEntity<ResponseBodyDto<List<Task>>> getAll(){
        List<Task> tasks = taskService.getAll();
        ResponseBodyDto response= new ResponseBodyDto(tasks, ResponseCodeEnum.R_200,"OK",tasks.size());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @PostMapping
    public ResponseEntity<ResponseBodyDto<Task>> createOrUpdate(@RequestBody Task request){
        Task task = taskService.createOrUpdate(request);
        ResponseBodyDto response= new ResponseBodyDto(task, ResponseCodeEnum.R_201,"Create");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ResponseBodyDto<Task>> findById(@PathVariable Long id){
        Task task = taskService.findById(id);
        ResponseBodyDto response= new ResponseBodyDto(task, ResponseCodeEnum.R_200,"OK");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
