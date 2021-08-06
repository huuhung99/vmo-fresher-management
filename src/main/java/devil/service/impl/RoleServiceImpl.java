package devil.service.impl;

import devil.common.error.BadRequestException;
import devil.controller.request.RoleRequest;
import devil.dto.RoleDto;
import devil.dto.mapper.GenericMapper;
import devil.model.Role;
import devil.repository.RoleRepository;
import devil.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private GenericMapper genericMapper;
    @Override
    public RoleDto create(RoleRequest request) {
        Role roleByName = roleRepository.findRoleByName(request.getName());
        if(roleByName!=null){
            throw new BadRequestException("Role name does exit!");
        }
        Role save = roleRepository.save(genericMapper.mapToType(request, Role.class));
        return genericMapper.mapToType(save,RoleDto.class);
    }

    @Override
    public RoleDto findByName(String name) {
        Role roleByName = roleRepository.findRoleByName(name);
        if(roleByName!=null)
            return genericMapper.mapToType(roleByName,RoleDto.class);
        return null;
    }
}
