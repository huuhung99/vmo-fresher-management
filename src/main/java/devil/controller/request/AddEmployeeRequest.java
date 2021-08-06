package devil.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddEmployeeRequest {
    @NotNull
    @JsonProperty("department_id")
    private Long departmentId;
    @NotNull
    @JsonProperty("employee_id")
    private Long employeeId;
    @NotNull
    private String language;
    private String specialization;
}
