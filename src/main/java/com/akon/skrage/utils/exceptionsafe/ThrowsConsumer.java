package com.akon.skrage.utils.exceptionsafe;

import java.util.Objects;
import java.util.function.Consumer;

@FunctionalInterface
public interface ThrowsConsumer<T, X extends Throwable> {

    void accept(T t) throws X;

    default ThrowsConsumer<T, X> andThen(Consumer<? super T> after) {
        Objects.requireNonNull(after);
        return t -> {this.accept(t); after.accept(t);};
    }

    default ThrowsConsumer<T, ? extends Throwable> andThen(ThrowsConsumer<? super T, ? extends Throwable> after) {
        Objects.requireNonNull(after);
        return t -> {this.accept(t); after.accept(t);};
    }

}
