package com.akon.skrage.utils.exceptionsafe;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class CatchablePredicate<T, X extends Throwable> implements Predicate<T>, Catchable<Predicate<? super X>, Consumer<? super T>, Predicate<T>> {

    private final ThrowsPredicate<T, X> throwsPredicate;
    private Predicate<T> caught;

    @Override
    public Predicate<T> onCatch(@Nullable Predicate<? super X> c, @Nullable Consumer<? super T> f) {
        return t -> {
            try {
                return this.throwsPredicate.test(t);
            } catch (Throwable ex) {
                return Optional.ofNullable(c).map(predicate -> predicate.test((X)ex)).orElse(false);
            } finally {
                Optional.ofNullable(f).ifPresent(consumer -> consumer.accept(t));
                ExceptionSafe.closeResource(t);
            }
        };
    }

    public Predicate<T> caught(@Nullable Consumer<? super X> c, @Nullable Consumer<T> f) {
        return this.onCatch(Optional.ofNullable(c).map(consumer -> (Predicate<X>) t -> {
            consumer.accept(t);
            return false;
        }).orElse(null), f);
    }

    public Predicate<T> caught(@Nullable Consumer<? super X> c) {
        return this.caught(c, null);
    }

    @Override
    public boolean test(T t) {
        return Optional.ofNullable(this.caught).orElse(this.caught = this.onCatch()).test(t);
    }
}
