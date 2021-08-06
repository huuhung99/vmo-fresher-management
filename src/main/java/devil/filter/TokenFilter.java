package devil.filter;

import devil.dto.enums.RoleEnum;
import devil.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static devil.service.TokenService.USER_NAME_KEY;

@Component
public class TokenFilter implements Filter {

    @Autowired
    private TokenService tokenService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest rq = (HttpServletRequest) servletRequest;
        String authorization = rq.getHeader("Authorization");
        String uri = rq.getRequestURI();
        String method = rq.getMethod();

        if (uri.contains("/employee/sign-in")||uri.contains("/employee/sign-up")
        ||uri.contains("swagger")||uri.contains("/v2/api-docs")) {
            //neu no la url cua employee/sign-in, employee/sign-up, swagger thi cho qua
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        String userName = tokenService.verifyToken(authorization);
        String role=tokenService.getRoleFromToken(authorization);
        if (userName == null||role == null) {
            this.writeFail(servletResponse, "Token failed or expired!");
            return;
        }
        if(role.equals(RoleEnum.ADMIN.name())){
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        if (StringUtils.isEmpty(authorization)) {
            this.writeFail(servletResponse, "missing token!");
            return;
        }
        if(role.equals(RoleEnum.FRESHER.name())){
            if(uri.contains("/employee")&&method.equals("GET")||method.equals("PUT")){
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            }
            this.writeFail(servletResponse, "Unauthorized!");
            return;
        }
        HttpServletResponse res = (HttpServletResponse) servletResponse;
        res.addHeader(USER_NAME_KEY, userName);
        filterChain.doFilter(servletRequest, res);
    }

    private void writeFail(ServletResponse servletResponse, String msg) {
        HttpServletResponse res = (HttpServletResponse) servletResponse;
        res.setStatus(HttpStatus.UNAUTHORIZED.value());
        res.setContentType(MediaType.APPLICATION_JSON_VALUE);
        PrintWriter out = null;
        try {
            out = res.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        out.write("{ \"ERROR\": \"" + msg + "\" }");
    }
}
