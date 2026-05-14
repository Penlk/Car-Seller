package ru.penlk.business.implementations.orders.strategies;

import org.keycloak.representations.idm.UserRepresentation;

import java.util.List;
import java.util.Optional;

public interface ManagerSelectionStrategy {
    Optional<String> findManager(List<UserRepresentation> managers);
}
