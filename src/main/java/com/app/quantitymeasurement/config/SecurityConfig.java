package com.app.quantitymeasurement.config;

import com.app.quantitymeasurement.security.CustomAuthSuccessHandler;
import com.app.quantitymeasurement.security.CustomOAuth2UserService;
import com.app.quantitymeasurement.security.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final CustomAuthSuccessHandler successHandler;
    private final CustomOAuth2UserService oauth2UserService;
    private final JwtUtil jwtUtil;
    private final String[] publicPaths;

    public SecurityConfig(CustomAuthSuccessHandler successHandler,
                          CustomOAuth2UserService oauth2UserService,
                          JwtUtil jwtUtil,
                          @Value("${app.security.public-paths}") String publicPaths) {
        this.successHandler = successHandler;
        this.oauth2UserService = oauth2UserService;
        this.jwtUtil = jwtUtil;
        this.publicPaths = publicPaths.split(",");
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> {
                    for (String p : publicPaths) {
                        auth.requestMatchers(p.trim()).permitAll();
                    }
                    auth.requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/actuator/**").permitAll();
                    auth.anyRequest().authenticated();
                })
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/api/auth/login")
                        .userInfoEndpoint(u -> u.userService(oauth2UserService))
                        .successHandler(successHandler)
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtToken -> {
                            Jwt jwtObj = (Jwt) jwtToken;
                            Claims claims = jwtUtil.parseClaims(jwtObj.getTokenValue());
                            List<String> roles = jwtUtil.getRolesFromToken(jwtObj.getTokenValue());
                            var authorities = roles.stream()
                                    .map(SimpleGrantedAuthority::new)
                                    .collect(Collectors.toList());
                            return new org.springframework.security.authentication.UsernamePasswordAuthenticationToken(
                                    jwtObj.getSubject(), jwtObj, authorities);
                        }))
                );

        return http.build();
    }

    @Bean
    public JwtDecoder jwtDecoder(@Value("${app.jwt.secret}") String secret) {
        byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        SecretKey key = Keys.hmacShaKeyFor(keyBytes);
        return NimbusJwtDecoder.withSecretKey(key).build();
    }
}
