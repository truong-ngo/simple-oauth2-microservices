package com.example.springauth.config;

import com.example.springauth.model.Role;
import com.example.springauth.model.User;
import com.example.springauth.service.core.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Consumer;

@Component
@RequiredArgsConstructor
public class UserRepositoryOAuth2UserHandler implements Consumer<OAuth2User> {

    @Value("${user.default_password}")
    private String defaultPassword;

    private final PasswordEncoder passwordEncoder;

    private final UserService userService;

    @Override
    public void accept(OAuth2User oAuth2User) {

        if (this.userService.loadUserByUsername(oAuth2User.getName()) == null) {
            System.out.println("Saving first-time user: name=" + oAuth2User.getName() + ", claims=" + oAuth2User.getAttributes() + ", authorities=" + oAuth2User.getAuthorities());
            User user = User.builder()
                    .username(oAuth2User.getName())
                    .password(passwordEncoder.encode(defaultPassword))
                    .roles(List.of(Role.builder().id(1L).build()))
                    .build();
            userService.createUser(user);
        }
    }
}
