package com.app.quantitymeasurement.security;

import com.app.quantitymeasurement.model.User;
import com.app.quantitymeasurement.service.UserService;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserService userService;

    public CustomOAuth2UserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        OAuth2User user = super.loadUser(userRequest);
        // Persist or update user in DB (non-blocking for auth flow)
        Map<String, Object> attrs = user.getAttributes();
        String email = (String) attrs.get("email");
        String name = (String) attrs.get("name");
        if (email != null) {
            userService.createOrUpdateUser(email, name, userRequest.getClientRegistration().getRegistrationId());
        }
        return user;
    }
}
