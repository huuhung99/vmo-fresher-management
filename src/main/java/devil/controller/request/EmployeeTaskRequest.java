package devil.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


@Data
public class EmployeeTaskRequest {
    private Long id;
    @JsonProperty("employee_id")
    private Long employeeId;
    @JsonProperty("task_id")
    private Long taskId;
    private Float score;
    private String note;
}
