package devil.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.Instant;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeCreateRequest {
    @JsonProperty("user_name")
    @NotNull
    private String userName;
    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("last_name")
    private String lastName;
    @NotNull
    private String password;
    @JsonProperty("birth_day")
    private Instant birthDay;
    private String address;
    private String email;
}
