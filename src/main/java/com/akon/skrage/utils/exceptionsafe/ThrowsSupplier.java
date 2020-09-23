package com.akon.skrage.utils.exceptionsafe;

@FunctionalInterface
public interface ThrowsSupplier<T, X extends Throwable> {

    T get() throws X;

}
