package ru.penlk.dataBase.basicConfigurations;

public record BasicConfigurationId(long id) {
    public BasicConfigurationId {
        if (id < 0) {
            throw new IllegalArgumentException("Id must be non-negative");
        }
    }

    public static BasicConfigurationId defaultId() {
        return new BasicConfigurationId(0);
    }
}
