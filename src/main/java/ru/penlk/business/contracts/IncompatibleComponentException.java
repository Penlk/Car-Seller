package ru.penlk.business.contracts;

public class IncompatibleComponentException extends RuntimeException {
    public IncompatibleComponentException(String message) {
        super(message);
    }
}
