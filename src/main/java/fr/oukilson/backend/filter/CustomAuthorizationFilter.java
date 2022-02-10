package fr.oukilson.backend.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class CustomAuthorizationFilter extends OncePerRequestFilter {
    private final Algorithm algo;
    private Set<String> noVerificationURI;

    public CustomAuthorizationFilter(Algorithm algo) {
        this.algo = algo;
        this.computeNoVerificationURI();
    }

    private void computeNoVerificationURI() {
        this.noVerificationURI = new HashSet<>();
        this.noVerificationURI.add("/login");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String requestURI = request.getRequestURI();

        if (this.noVerificationURI.contains(requestURI)) {
            filterChain.doFilter(request, response);
        }
        else {
            String header = request.getHeader(HttpHeaders.AUTHORIZATION);
            if (header!=null && header.startsWith("Bearer ")) {
                try {
                    String token = header.substring(7);
                    JWTVerifier verifier = JWT.require(this.algo).build();
                    DecodedJWT decodedToken = verifier.verify(token);
                    String username = decodedToken.getSubject();
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>());
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    request.setAttribute("username", username);
                    filterChain.doFilter(request, response);
                }
                catch (Exception e) {
                    response.sendError(HttpStatus.FORBIDDEN.value());
                }
            }
            else
                filterChain.doFilter(request, response);
        }
    }
}
