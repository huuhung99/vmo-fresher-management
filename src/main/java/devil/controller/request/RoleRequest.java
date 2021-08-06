package devil.controller.request;

import lombok.Data;

@Data
public class RoleRequest {
    private Long id;
    private String name;
    private String description;
}
