package devil.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentRequest {
    private Long id;
    private String name;
    private String locations;
    @JsonProperty("manager_id")
    private Long managerId;
    @JsonProperty("company_id")
    private Long companyId;
}
