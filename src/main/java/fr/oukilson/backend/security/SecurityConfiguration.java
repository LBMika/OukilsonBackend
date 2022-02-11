package fr.oukilson.backend.security;

import com.auth0.jwt.algorithms.Algorithm;
import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;
import fr.oukilson.backend.filter.CustomAuthenticationFilter;
import fr.oukilson.backend.filter.CustomAuthorizationFilter;
import fr.oukilson.backend.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final UserService userDetailsService;
    private final CustomPasswordEncoder customPasswordEncoder;
    private final Gson gson;

    private static final String[] AUTH_WHITELIST = {
            // -- Swagger UI v2
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            // -- Swagger UI v3 (OpenAPI)
            "/v3/api-docs/**",
            "/swagger-ui/**",
    };

    public SecurityConfiguration(UserService userDetailsService, CustomPasswordEncoder customPasswordEncoder, Gson gson) {
        this.userDetailsService = userDetailsService;
        this.customPasswordEncoder = customPasswordEncoder;
        this.gson = gson;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    // TODO : gitignore secretKey
    // TODO : param de lancement ???
    @Bean(name="algo")
    public Algorithm algorithm(@Value("${environment.secretKey}")String key) {
        return Algorithm.HMAC256(key);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(customPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.cors();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests().antMatchers(AUTH_WHITELIST).permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/users/*").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/users").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/games/*").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/events/*").permitAll();
        http.authorizeRequests().anyRequest().authenticated();
        Algorithm algo = getApplicationContext().getBean(Algorithm.class);
        http.addFilterBefore(new CustomAuthorizationFilter(algo), UsernamePasswordAuthenticationFilter.class);
        http.addFilter(new CustomAuthenticationFilter(authenticationManager(), algo, gson));
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(List.of("http://localhost:4200"));
        configuration.setAllowedMethods(ImmutableList.of("GET", "PUT", "OPTIONS", "POST"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(ImmutableList.of("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
