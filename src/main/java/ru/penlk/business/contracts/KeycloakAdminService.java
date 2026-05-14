package ru.penlk.business.contracts;

import org.keycloak.representations.idm.UserRepresentation;

import java.util.List;

public interface KeycloakAdminService {
    List<UserRepresentation> getManagers();
}
