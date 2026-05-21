package com.app.quantitymeasurement.security;

import com.app.quantitymeasurement.model.User;
import com.app.quantitymeasurement.repository.UserRepository;
import com.app.quantitymeasurement.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@Component
public class CustomAuthSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final UserService userService;

    public CustomAuthSuccessHandler(JwtUtil jwtUtil, UserRepository userRepository, UserService userService) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        Object principal = authentication.getPrincipal();
        if (!(principal instanceof OAuth2User)) {
            sendError(response, "Invalid authentication principal");
            return;
        }

        OAuth2User o = (OAuth2User) principal;
        String email = safeGet(o, "email");
        String name = safeGet(o, "name");

        if (email == null || email.isBlank()) {
            sendError(response, "Email not provided by identity provider");
            return;
        }

        // Ensure user exists and fetch roles
        User user = userRepository.findByEmail(email).orElseGet(() -> userService.createOrUpdateUser(email, name, "google"));
        List<String> roles = user.getRoles() == null ? List.of("ROLE_USER") : List.copyOf(user.getRoles());

        String token = jwtUtil.generateToken(email, name, roles);

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try (PrintWriter w = response.getWriter()) {
            w.write("{\"jwt\":\"" + token + "\"}");
            w.flush();
        }
    }

    private String safeGet(OAuth2User u, String key) {
        Object v = u.getAttributes().get(key);
        return v == null ? null : v.toString();
    }

    private void sendError(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try (PrintWriter w = response.getWriter()) {
            w.write("{\"error\":\"" + message + "\"}");
            w.flush();
        }
    }
}
