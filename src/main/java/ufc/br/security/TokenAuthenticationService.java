package ufc.br.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Date;

@CrossOrigin(origins = "*", maxAge = 3600)
class TokenAuthenticationService {
    static final long EXPIRATIONTIME = 864_000_000; // 10 days
    static final String SECRET = "!@UFC#API#I9MOVE!@";
    static final String TOKEN_PREFIX = "Bearer ";
    static final String HEADER_STRING = "Authorization";

    public static void addAuthentication(HttpServletResponse res, String username) throws IOException {
        String JWT = Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
        res.getOutputStream().write((TOKEN_PREFIX + JWT).getBytes());
    }

    public static UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request,
                                                                        HttpServletResponse response) throws IOException {

        String token = request.getHeader(HEADER_STRING);
        if (token != null) {
            // parse the token.
            String user = null;
            try {
                user = Jwts.parser()
                        .setSigningKey(SECRET)
                        .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                        .getBody()
                        .getSubject();
            }catch (Exception e) {
            }
                /*
                Usuario aux = service.getByEmail(user);
                if(aux!=null) {
                	System.err.println("Usuario : "+aux.getEmail()+"\n"+aux.getAuthorities());
                }else {
                	System.err.println("vazio");
                }
                */
            if(user!= null)
                return new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList() /*aux.getAuthorities()*/);
            return null;
        }
        return null;
    }
}