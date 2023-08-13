package Mobilise.bookapi.config;


import Mobilise.bookapi.auth.JwtAuthenticationFilter;
import Mobilise.bookapi.utils.handlers.Exceptions.AuthException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final AuthException unauthorizedHandler;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(exception-> exception.authenticationEntryPoint(unauthorizedHandler))
                .sessionManagement(session-> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth->
                        auth
                                .requestMatchers(antMatcher("/api/v1/auth/**")).permitAll()
                                .requestMatchers(antMatcher(HttpMethod.POST, "/api/v1/authors")).hasAuthority("ADMIN")
                                .requestMatchers(antMatcher(HttpMethod.GET, "/api/v1/authors/**")).permitAll()
                                .requestMatchers(antMatcher(HttpMethod.PATCH, "/api/v1/authors")).hasAnyAuthority("ADMIN", "AUTHOR")
                                .requestMatchers(antMatcher(HttpMethod.DELETE, "/api/v1/authors")).hasAnyAuthority("ADMIN", "AUTHOR")
                                .requestMatchers(antMatcher(HttpMethod.POST, "/api/v1/books")).hasAuthority("ADMIN")
                                .requestMatchers(antMatcher( "/api/v1/books/**")).permitAll()
                                .requestMatchers(antMatcher( "/api/v1/users/**")).permitAll()
                                .anyRequest()
                                .authenticated()
                );

        http.authenticationProvider(authenticationProvider);
        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
