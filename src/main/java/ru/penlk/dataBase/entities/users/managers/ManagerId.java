package ru.penlk.dataBase.entities.users.managers;

public record ManagerId(long id) {
    public ManagerId {
        if (id < 0) {
            throw new IllegalArgumentException("Id must be non-negative");
        }
    }

    public static ManagerId defaultId() {
        return new ManagerId(0);
    }
}