package ru.penlk.business.contracts;

public class ServiceException extends RuntimeException {
    public ServiceException(String message) {
        super(message);
    }
}
