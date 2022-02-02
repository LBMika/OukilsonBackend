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
                    if (this.isAuthorized(username, requestURI, request))
                        filterChain.doFilter(request, response);
                    else {
                        response.setHeader("error", username+"is not allowed in "+requestURI);
                        response.sendError(HttpStatus.FORBIDDEN.value());
                    }
                }
                catch (Exception e) {
                    response.sendError(HttpStatus.FORBIDDEN.value());
                }
            }
            else
                filterChain.doFilter(request, response);
        }
    }

    private boolean isAuthorized(String username, String uri, HttpServletRequest request) {
        boolean result;
        if (uri.startsWith("/users"))
            result = this.isAuthorizedUsersPath(username, uri.substring(6), request);
        else
        if (uri.startsWith("/events"))
            result = this.isAuthorizedEventsPath(username, uri.substring(7), request);
        else
            result = true;
        return result;
    }

    private boolean isAuthorizedUsersPath(String username, String uri, HttpServletRequest request) {
        boolean result;
        String httpMethod = request.getMethod();
        if (httpMethod.equalsIgnoreCase("PUT")) {
            if (uri.startsWith("/add/" + username)
                    || uri.startsWith("/remove/" + username)
                    || uri.startsWith("/empty/" + username))
                result = true;
            else
                result = false;
        }
        else
            result = false;
        return result;
    }

    private boolean isAuthorizedEventsPath(String username, String uri, HttpServletRequest request) {
        boolean result;
        String httpMethod = request.getMethod();
        if (httpMethod.equalsIgnoreCase("PUT")) {
            if (uri.startsWith("/add_user/"+username) || uri.startsWith("/add_user/waiting/"+username)
                    || uri.startsWith("/remove_user/"+username) || uri.startsWith("/remove_user/waiting/"+username))
                result = true;
            else
                result = false;
        }
        else if (httpMethod.equalsIgnoreCase("DELETE") || httpMethod.equalsIgnoreCase("PUT")) {
            request.setAttribute("username", username);
            result = true;
        }
        else
            result = false;
        return result;
    }
}
