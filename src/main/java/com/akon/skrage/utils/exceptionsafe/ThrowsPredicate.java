package com.akon.skrage.utils.exceptionsafe;

import java.util.Objects;
import java.util.function.Predicate;

@FunctionalInterface
public interface ThrowsPredicate<T, X extends Throwable> {

    boolean test(T t) throws X;

    default ThrowsPredicate<T, ? extends Throwable> and(ThrowsPredicate<? super T, ? extends Throwable> other) {
        Objects.requireNonNull(other);
        return t -> this.test(t) && other.test(t);
    }

    default ThrowsPredicate<T, X> and(Predicate<? super T> other) {
        Objects.requireNonNull(other);
        return t -> this.test(t) && other.test(t);
    }

    default ThrowsPredicate<T, X> negate() {
        return t -> !this.test(t);
    }

    default ThrowsPredicate<T, ? extends Throwable> or(ThrowsPredicate<? super T, ? extends Throwable> other) {
        Objects.requireNonNull(other);
        return t -> this.test(t) || other.test(t);
    }

    default ThrowsPredicate<T, X> or(Predicate<? super T> other) {
        Objects.requireNonNull(other);
        return t -> this.test(t) || other.test(t);
    }

}
