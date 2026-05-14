package ru.penlk.business.contracts.configurations;

public interface ConfiguratorSecurity {
    Boolean isOwner(Long configuratorId);
}
