package devil.service;

import devil.controller.request.RoleRequest;
import devil.dto.RoleDto;

public interface RoleService {
    RoleDto create(RoleRequest request);
    RoleDto findByName(String name);
}
