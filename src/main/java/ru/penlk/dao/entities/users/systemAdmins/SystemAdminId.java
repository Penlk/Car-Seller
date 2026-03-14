package ru.penlk.dao.entities.users.systemAdmins;

public record SystemAdminId(long id) {
    public SystemAdminId {
        if (id < 0) {
            throw new IllegalArgumentException("Id must be non-negative");
        }
    }

    public static SystemAdminId defaultId() {
        return new SystemAdminId(0);
    }
}