package fr.oukilson.backend.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import fr.oukilson.backend.dto.user.UserConnection;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final Gson gson;
    private final Algorithm algo;

    public CustomAuthenticationFilter(AuthenticationManager authenticationManager, Algorithm algo, Gson gson) {
        this.authenticationManager = authenticationManager;
        this.algo = algo;
        this.gson = gson;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response)
            throws AuthenticationException {
        UsernamePasswordAuthenticationToken token;

        // Login is a POST method, create Authentication object with username and password from the body
        if (request.getMethod().equals("POST")) {
            String body;
            try {
                body = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
            } catch (IOException e) {
                body = null;
            }
            if (body!=null) {
                UserConnection data = gson.fromJson(body, UserConnection.class);
                token = new UsernamePasswordAuthenticationToken(data.getNickname(), data.getPassword());
            }
            else
                token = new UsernamePasswordAuthenticationToken("", "");
        }
        else
            token = new UsernamePasswordAuthenticationToken("", "");
        return authenticationManager.authenticate(token);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain, Authentication authResult)
            throws IOException, ServletException {

        // Create token
        User user = (User) authResult.getPrincipal();
        String token = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(Date.from(LocalDateTime.now().plusDays(1).toInstant(ZoneOffset.UTC)))
                .withIssuer(request.getRequestURL().toString())
                .withClaim("roles",
                            user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining()))
                .sign(this.algo);

        // Put it in the body
        Map<String, String> data = new HashMap<>();
        data.put("token", token);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), data);
    }
}
