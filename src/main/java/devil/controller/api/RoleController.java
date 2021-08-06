package devil.controller.api;

import devil.common.enums.ResponseCodeEnum;
import devil.controller.request.RoleRequest;
import devil.controller.response.ResponseBodyDto;
import devil.dto.RoleDto;
import devil.service.RoleService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/role")
@Api(tags = "Role")
public class RoleController {
    @Autowired
    private RoleService roleService;
    @PostMapping
    public ResponseEntity<ResponseBodyDto<RoleDto>> create(@RequestBody RoleRequest request){
        RoleDto role = roleService.create(request);
        ResponseBodyDto response=new ResponseBodyDto(role, ResponseCodeEnum.R_201,"Create");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @GetMapping("/{name}")
    public ResponseEntity<ResponseBodyDto<RoleDto>> findByName(@PathVariable String name){
        RoleDto role = roleService.findByName(name);
        ResponseBodyDto response=new ResponseBodyDto(role, ResponseCodeEnum.R_200,"OK");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
