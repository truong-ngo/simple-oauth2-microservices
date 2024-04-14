package com.example.springauth.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;

import java.util.UUID;

@Configuration
@RequiredArgsConstructor
public class ClientConfig {

    private final PasswordEncoder passwordEncoder;

    @Bean
    RegisteredClientRepository registeredClientRepository(JdbcTemplate template) {
        RegisteredClientRepository repository = new JdbcRegisteredClientRepository(template);
        RegisteredClient uiClient = repository.findByClientId("ui-client");
        RegisteredClient gateway = repository.findByClientId("gateway");

        if (uiClient == null) {
            uiClient = RegisteredClient.withId(UUID.randomUUID().toString())
                    .clientId("ui-client")
                    .clientSecret(passwordEncoder.encode("secret"))
                    .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                    .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_POST)
                    .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                    .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                    .redirectUri("http://127.0.0.1:8082/login/oauth2/code/spring")
                    .redirectUri("http://127.0.0.1:8080/authorized")
                    .redirectUri("http://localhost:4200")
                    .scope(OidcScopes.OPENID)
                    .scope("user.read")
                    .scope("user.write")
                    .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
                    .build();
            repository.save(uiClient);
        }

        if (gateway == null) {
            gateway = RegisteredClient.withId(UUID.randomUUID().toString())
                    .clientId("gateway")
                    .clientSecret(passwordEncoder.encode("pw"))
                    .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                    .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                    .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                    .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                    .redirectUri("http://127.0.0.1:8082/login/oauth2/code/spring")
                    .scope(OidcScopes.OPENID)
                    .scope("user.read")
                    .scope("user.write")
                    .build();
            repository.save(gateway);
        }
        return repository;
    }
}
