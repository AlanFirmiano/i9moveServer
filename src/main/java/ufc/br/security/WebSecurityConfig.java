package ufc.br.security;


import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.filter.GenericFilterBean;

import ufc.br.model.Papel;
import ufc.br.model.Usuario;
import ufc.br.repository.UsuarioRepository;
import ufc.br.service.UsuarioService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UsuarioDetailsService usuarioDetailsService;
    
    @Bean
    public PasswordEncoder passwordEncoder(){
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder;
    }
    /*.antMatchers(HttpMethod.POST,"/responsible").hasRole("ADMINISTRADOR")
    .antMatchers(HttpMethod.PUT,"/responsible").hasRole("ADMINISTRADOR")
    .antMatchers(HttpMethod.DELETE,"/responsible").hasRole("ADMINISTRADOR")
    
    .antMatchers(HttpMethod.POST,
    		"/patients","/grasp",
    		"/exercises","/level",
    		"/permitions","/recommendation",
    		"/series"
    		).hasAnyRole("ADMINISTRADOR","FISIOTERAPEUTA")
    .antMatchers(HttpMethod.PUT,
    		"/patients","/grasp",
    		"/exercises","/level",
    		"/permitions","/recommendation",
    		"/series"
    		).hasAnyRole("ADMINISTRADOR","FISIOTERAPEUTA")
    .antMatchers(HttpMethod.DELETE,
    		"/patients","/grasp",
    		"/exercises","/level",
    		"/permitions","/recommendation",
    		"/series"
    		).hasAnyRole("ADMINISTRADOR","FISIOTERAPEUTA")
    */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
        /*
        .antMatchers(HttpMethod.POST,"/responsible").hasRole(Papel.ADMINISTRADOR.getAuthority())
        .antMatchers(HttpMethod.PUT,"/responsible").hasRole(Papel.ADMINISTRADOR.getAuthority())
        .antMatchers(HttpMethod.DELETE,"/responsible").hasRole(Papel.ADMINISTRADOR.getAuthority())
        .antMatchers(HttpMethod.POST,
        		"/patients","/grasp",
        		"/exercises","/levels",
        		"/permitions","/recommendation",
        		"/series"
        		).hasRole(Papel.FISIOTERAPEUTA.getAuthority())
        
        .antMatchers(HttpMethod.PUT,
        		"/patients","/grasp",
        		"/exercises","/level",
        		"/permitions","/recommendation",
        		"/series"
        		).hasAnyRole(Papel.ADMINISTRADOR.getAuthority(),Papel.FISIOTERAPEUTA.getAuthority())
        .antMatchers(HttpMethod.DELETE,
        		"/patients","/grasp",
        		"/exercises","/level",
        		"/permitions","/recommendation",
        		"/series"
        		).hasAnyRole(Papel.ADMINISTRADOR.getAuthority(),Papel.FISIOTERAPEUTA.getAuthority())
        */
        
        
        		.antMatchers(HttpMethod.POST, "/login").permitAll()
                .antMatchers("/video/**").permitAll()
                
                .anyRequest().authenticated()
                
                .and()
                // We filter the api/login requests
                .addFilterBefore(new JWTLoginFilter("/login", authenticationManager()),
                        UsernamePasswordAuthenticationFilter.class)
                // And filter other requests to check the presence of JWT in header
                .addFilterBefore(new JWTAuthenticationFilter(),
                        UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String result = encoder.encode("admini9move");
        System.err.println(result);
        // Create a default account
        auth.inMemoryAuthentication()
                .withUser("admin@ufc.com")
                .password(result)
                .roles("ADM");
        auth.userDetailsService(usuarioDetailsService).passwordEncoder(passwordEncoder());
    }

    @Component
    public class UsuarioDetailsService implements UserDetailsService {
        @Autowired
        //private ResponsibleRepository repository;
        private UsuarioRepository repository;
        @Override
        public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
            return this.repository.findByEmail(email);
        }
    }

    @CrossOrigin(origins = "*", maxAge = 3600)
    static class TokenAuthenticationService {
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

    public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {

        public JWTLoginFilter(String url, AuthenticationManager authManager) {
            super(new AntPathRequestMatcher(url));
            setAuthenticationManager(authManager);
        }

        @Override
        public org.springframework.security.core.Authentication attemptAuthentication(
                HttpServletRequest req, HttpServletResponse res)
                throws AuthenticationException, IOException, ServletException {

            //Responsible creds = new Responsible();
        	Usuario creds = new Usuario();
            try {
                //creds = new ObjectMapper().readValue(req.getReader(), Responsible.class);
            	creds = new ObjectMapper().readValue(req.getReader(), Usuario.class);
            }catch (Exception e){

            }
            System.err.println(creds.getUsername()+" logou ");
            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            creds.getUsername(),
                            creds.getPassword(),
                            new ArrayList<>()
                    )
            );
        }

        @Override
        protected void successfulAuthentication(
                HttpServletRequest req,
                HttpServletResponse res, FilterChain chain,
                Authentication auth) throws IOException, ServletException {
            if (!req.getMethod().equalsIgnoreCase("OPTIONS")){
                TokenAuthenticationService
                        .addAuthentication(res, auth.getName());
            }
        }
    }

    @Component
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public class JWTAuthenticationFilter extends GenericFilterBean {
    
        @Override
        public void doFilter(ServletRequest request,
                             ServletResponse response,
                             FilterChain filterChain)
                throws IOException, ServletException {

            HttpServletResponse res = (HttpServletResponse) response;
            res.setHeader("Access-Control-Allow-Origin", "*");
            res.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE, PATCH");
            res.setHeader("Access-Control-Max-Age", "3600");
            res.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
            res.setHeader("Access-Control-Expose-Headers", "Location");

            final HttpServletRequest req = (HttpServletRequest) request;
            if (!req.getMethod().equalsIgnoreCase("OPTIONS")) {
                Authentication authentication = TokenAuthenticationService
                        .getAuthentication(req, res);
                SecurityContextHolder.getContext()
                        .setAuthentication(authentication);
                filterChain.doFilter(request, res);
            } else {
                //
            }

        }
    }
}
