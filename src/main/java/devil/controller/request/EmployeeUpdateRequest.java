package devil.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.Instant;
@Data
public class EmployeeUpdateRequest {
    private Long id;
    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("last_name")
    private String lastName;
    private String password;
    @JsonProperty("birth_day")
    private Instant birthDay;
    private String address;
    private String email;
}
