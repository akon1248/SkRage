package com.akon.skrage.utils.exceptionsafe;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class CatchableSupplier<T, X extends Throwable> implements Supplier<T>, Catchable<Function<? super X, T>, Runnable, Supplier<Optional<T>>> {

    private final ThrowsSupplier<T, X> throwsSupplier;
    private Supplier<Optional<T>> caught;

    @Override
    public Supplier<Optional<T>> caught(@Nullable Function<? super X, T> c, @Nullable Runnable f) {
        return () -> {
            try {
                return Optional.ofNullable(this.throwsSupplier.get());
            } catch (Throwable ex) {
                return Optional.ofNullable(c).map(func -> func.apply((X)ex));
            } finally {
                Optional.ofNullable(f).ifPresent(Runnable::run);
            }
        };
    }

    public Supplier<Optional<T>> caught(@Nullable Consumer<? super X> c, @Nullable Runnable f) {
        return this.caught(Optional.ofNullable(c).map(consumer -> (Function<X, T>)t -> {
            consumer.accept(t);
            return null;
        }).orElse(null), f);
    }

    public Supplier<Optional<T>> caught(@Nullable Consumer<? super X> c) {
        return this.caught(c, null);
    }

    public Optional<T> getOptional() {
        return Optional.ofNullable(this.caught).orElse(this.caught = this.caught()).get();
    }

    @Nullable
    @Override
    public T get() {
        return this.getOptional().orElse(null);
    }
}
