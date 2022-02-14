package com.akon.skrage.utils;

public class UncheckedReflectiveOperationException extends RuntimeException {
    public UncheckedReflectiveOperationException() {
        super();
    }

    public UncheckedReflectiveOperationException(String message) {
        super(message);
    }

    public UncheckedReflectiveOperationException(String message, ReflectiveOperationException cause) {
        super(message, cause);
    }

    public UncheckedReflectiveOperationException(ReflectiveOperationException cause) {
        super(cause);
    }

    @Override
    public synchronized ReflectiveOperationException getCause() {
        return (ReflectiveOperationException)super.getCause();
    }
}
