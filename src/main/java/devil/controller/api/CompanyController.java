package devil.controller.api;

import devil.common.enums.ResponseCodeEnum;
import devil.controller.request.CompanyRequest;
import devil.controller.response.ResponseBodyDto;
import devil.model.Company;
import devil.service.CompanyService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/company")
@Api(tags = "Company")
public class CompanyController {
    @Autowired
    private CompanyService companyService;
    @GetMapping
    public ResponseEntity<ResponseBodyDto<List<Company>>> getAll(){
        List<Company> companies = companyService.getAll();
        ResponseBodyDto response=new ResponseBodyDto(companies, ResponseCodeEnum.R_200,"OK",companies.size());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ResponseBodyDto<Company>> findById(@PathVariable Long id){
        Company company = companyService.findById(id);
        ResponseBodyDto response=new ResponseBodyDto(company, ResponseCodeEnum.R_200,"OK");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @PostMapping
    public ResponseEntity<ResponseBodyDto<Company>> createOrUpdate(@RequestBody CompanyRequest request){
        Company company = companyService.createOrUpdate(request);
        ResponseBodyDto response=new ResponseBodyDto(company,ResponseCodeEnum.R_201,"Create");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseBodyDto<Company>> deleteById(@PathVariable Long id){
        companyService.deleteById(id);
        ResponseBodyDto response=new ResponseBodyDto();
        response.setItem(null).setCode(ResponseCodeEnum.R_200).setMessage("OK");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
