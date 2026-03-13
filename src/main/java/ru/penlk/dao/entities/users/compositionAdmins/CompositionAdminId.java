package ru.penlk.dao.entities.users.compositionAdmins;

public record CompositionAdminId(long id) {
    public CompositionAdminId {
        if (id < 0) {
            throw new IllegalArgumentException("Id must be non-negative");
        }
    }

    public static CompositionAdminId defaultId() {
        return new CompositionAdminId(0);
    }
}