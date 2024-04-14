package com.example.springauth.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
@RequiredArgsConstructor
public class CustomJdbcOAuth2AuthorizationService {

    private final JdbcTemplate template;

    private final RegisteredClientRepository clientRepository;

    private static final String TABLE_NAME = "oauth2_authorization";

    private static final String REGISTERED_CLIENT_ID_FILTER = "registered_client_id = ?";

    private static final String PRINCIPAL_NAME_FILTER = "principal_name = ?";

    public boolean deleteByClientIdAndPrincipalName(String clientId, String principalName) {
        RegisteredClient client = clientRepository.findByClientId(clientId);
        Assert.notNull(client, "client cannot be null");
        String registeredClientId = client.getId();
        String sql = "delete from " + TABLE_NAME + " where " +  REGISTERED_CLIENT_ID_FILTER + " and " + PRINCIPAL_NAME_FILTER;
        return template.update(sql, registeredClientId, principalName) != 0;
    }
}
