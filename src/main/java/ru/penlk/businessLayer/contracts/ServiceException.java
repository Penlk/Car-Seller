package ru.penlk.businessLayer.contracts;

public class ServiceException extends RuntimeException {
    public ServiceException(String message) {
        super(message);
    }
}
