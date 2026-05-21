package com.app.quantitymeasurement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    // Redirects to Spring Security's OAuth2 authorization endpoint for Google
    @GetMapping("/api/auth/login")
    public String login() {
        return "redirect:/oauth2/authorization/google";
    }

    // Optional callback endpoint (Spring handles the exchange). Keep for clarity.
    @GetMapping("/api/auth/callback/google")
    public String callback() {
        // After successful login, CustomAuthSuccessHandler returns JSON with JWT.
        // This endpoint can be used by frontends if needed.
        return "redirect:/";
    }
}
