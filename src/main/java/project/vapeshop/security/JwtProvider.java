package project.vapeshop.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import project.vapeshop.dto.user.UserDTOAfterAuthorization;
import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtProvider {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.time}")
    private String time;

    public String generatedToken(UserDTOAfterAuthorization user) {
        SecretKey secretKey= Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
        Date date = DateUtils.addMinutes(new Date(), Integer.parseInt(time));
        Claims claims = Jwts.claims().setSubject(user.getLogin());
        claims.put("role", user.getRole().getName());
        claims.put("name", user.getName());
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(date)
                .signWith(secretKey)
                .compact();
    }

    public boolean validateToken(String token) {
        SecretKey secretKey= Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
        Jws<Claims> claimsJws=Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
        return !claimsJws.getBody().getExpiration().before(new Date());
    }

    public String getLogin(String token) {
        SecretKey secretKey= Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }
}
