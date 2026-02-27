package ru.penlk.dataBase.entities.users.clients;

public record ClientId(long id) {
    public ClientId {
        if (id < 0) {
            throw new IllegalArgumentException("Id must be non-negative");
        }
    }

    public static ClientId defaultId() {
        return new ClientId(0);
    }
}