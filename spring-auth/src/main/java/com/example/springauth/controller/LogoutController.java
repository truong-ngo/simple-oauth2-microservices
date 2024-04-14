package com.example.springauth.controller;

import com.example.springauth.service.impl.CustomJdbcOAuth2AuthorizationService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Slf4j
@RestController
@RequiredArgsConstructor
public class LogoutController {

    private final CustomJdbcOAuth2AuthorizationService oAuth2AuthorizationService;

    @GetMapping("/auth/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response, @RequestParam("client_id") String clientId) throws IOException {
        SecurityContext context = SecurityContextHolder.getContext();
        final Authentication auth = context.getAuthentication();
        if (auth != null) {
            SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
            if (oAuth2AuthorizationService.deleteByClientIdAndPrincipalName(clientId, auth.getName())) {
                log.info("Delete oauth2 authorization object successfully");
            }
            logoutHandler.logout(request, response, auth);
            auth.setAuthenticated(false);
            SecurityContextHolder.clearContext();
            SecurityContextHolder.getContext().setAuthentication(null);
        }
        Cookie cookieWithSlash = new Cookie("JSESSIONID", null);
        cookieWithSlash.setPath(request.getContextPath() + "/");
        cookieWithSlash.setDomain("auth-server");
        cookieWithSlash.setMaxAge(0);
        response.addCookie(cookieWithSlash);
        new DefaultRedirectStrategy().sendRedirect(request, response, "http://localhost:4200");
    }
}
