package ru.penlk.dataBase.repositories.interfaces.orders.commonOrder;

public class CommonOrderNotFoundException extends RuntimeException {
    public CommonOrderNotFoundException(String message) {
        super(message);
    }
}
