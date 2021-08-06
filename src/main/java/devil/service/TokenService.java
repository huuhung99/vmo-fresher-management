package devil.service;

import devil.common.enums.ResponseCodeEnum;
import devil.common.error.UnauthorizedException;
import devil.model.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
@Slf4j
public class TokenService {
    private String secretKey = "huuhung99";
    public static final String USER_NAME_KEY = "user_name";

    public String genToken(String userName, Role role) {
        try {
            long date = LocalDateTime.now().plusMinutes(60).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
            Claims claims = Jwts.claims();
            claims.setIssuedAt(new Date());
            claims.setExpiration(new Date(date));
            claims.put(USER_NAME_KEY, userName);
            claims.put("role", role.getName());

            String token = Jwts.builder()
                    .setClaims(claims)
                    .signWith(SignatureAlgorithm.HS256, secretKey)
                    .compact();
            return token;
        } catch (Exception ex) {
            log.error("ERROR: {}", ex.getMessage());
            throw new UnauthorizedException(ResponseCodeEnum.E_1003,"Fail to generate token");
        }
    }

    public String verifyToken(String token) {
        if(token==null ||token.trim().isEmpty())
            return null;
        try {
            Claims claimBody = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody();

            String userName = claimBody.get(USER_NAME_KEY, String.class);
            return userName;
        } catch (Exception ex) {
            log.error("Fail to verify token: {}", ex.getMessage());
            return null;
        }
    }

    public String getRoleFromToken(String token){
        if(token==null ||token.trim().isEmpty())
            return null;
        try {
            Claims claimBody = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody();

            String role = claimBody.get("role", String.class);
            return role;
        } catch (Exception ex) {
            log.error("Fail to get role form token: {}", ex.getMessage());
            return null;
        }
    }
}