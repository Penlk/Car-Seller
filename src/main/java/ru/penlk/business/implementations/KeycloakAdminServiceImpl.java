package ru.penlk.business.implementations;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;
import ru.penlk.business.contracts.KeycloakAdminService;

import java.util.List;

@Service
public class KeycloakAdminServiceImpl implements KeycloakAdminService {

    @Value("${keycloak.server-url}")
    private String serverUrl;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.client-id}")
    private String clientId;

    @Value("${keycloak.username}")
    private String username;

    @Value("${keycloak.password}")
    private String password;

    private Keycloak keycloak;

    @PostConstruct
    public void init() {
        this.keycloak = KeycloakBuilder.builder()
                .serverUrl(serverUrl)
                .realm(realm)
                .clientId(clientId)
                .username(username)
                .password(password)
                .build();
    }

    @Override
    public List<UserRepresentation> getManagers() {
        UserRepresentation us;

        return keycloak.realm(realm)
                .users()
                .searchByAttributes("role:MANAGER");
    }
}