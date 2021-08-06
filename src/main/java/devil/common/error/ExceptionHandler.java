package devil.common.error;

import devil.common.enums.ResponseCodeEnum;
import devil.controller.response.ResponseBodyDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class ExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler(BadRequestException.class)
    public @ResponseBody
    ResponseEntity<ResponseBodyDto<Object>> handleBadRequestException(Exception ex,
                                                                      WebRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ResponseBodyDto<Object> dtoResult = new ResponseBodyDto<Object>();
        dtoResult.setCode(ResponseCodeEnum.R_400);
        dtoResult.setMessage(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(headers).body(dtoResult);
    }
    @org.springframework.web.bind.annotation.ExceptionHandler(NotFoundException.class)
    public @ResponseBody ResponseEntity<ResponseBodyDto<Object>> handleNotFoundException(Exception ex) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ResponseBodyDto<Object> dtoResult = new ResponseBodyDto<>();
        dtoResult.setCode(ResponseCodeEnum.R_404);
        dtoResult.setMessage(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).headers(headers).body(dtoResult);
    }
    @org.springframework.web.bind.annotation.ExceptionHandler(UnauthorizedException.class)
    public @ResponseBody ResponseEntity<ResponseBodyDto<Object>> handleUnauthorizedException(UnauthorizedException ex) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ResponseBodyDto<Object> dtoResult = new ResponseBodyDto<>();
        dtoResult.setCode(ex.getCode());
        dtoResult.setMessage(ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).headers(headers).body(dtoResult);
    }
}
