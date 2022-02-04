package fr.oukilson.backend.security;

import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import fr.oukilson.backend.filter.CustomAuthenticationFilter;
import fr.oukilson.backend.filter.CustomAuthorizationFilter;
import fr.oukilson.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final UserService userDetailsService;
    private final CustomPasswordEncoder customPasswordEncoder;
    private final Gson gson;

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
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/users/*").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/users").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/games/*").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/events/*").permitAll();
        http.authorizeRequests().anyRequest().authenticated();
        Algorithm algo = getApplicationContext().getBean(Algorithm.class);
        http.addFilterBefore(new CustomAuthorizationFilter(algo), UsernamePasswordAuthenticationFilter.class);
        http.addFilter(new CustomAuthenticationFilter(authenticationManager(), algo, gson));
    }
}
