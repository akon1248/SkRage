package com.akon.skrage.utils.exceptionsafe;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.Consumer;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class CatchableConsumer<T, X extends Throwable> implements Consumer<T>, Catchable<Consumer<? super X>, Consumer<? super T>, Consumer<T>> {

    private final ThrowsConsumer<T, X> throwsConsumer;
    private Consumer<T> caught;

    @Override
    public Consumer<T> caught(@Nullable Consumer<? super X> c, @Nullable Consumer<? super T> f) {
        return t -> {
            try {
                this.throwsConsumer.accept(t);
            } catch (Throwable ex) {
                Optional.ofNullable(c).ifPresent(consumer -> consumer.accept((X)ex));
            } finally {
                Optional.ofNullable(f).ifPresent(consumer -> consumer.accept(t));
                ExceptionSafe.closeResource(t);
            }
        };
    }

    @Override
    public void accept(T t) {
        Optional.ofNullable(this.caught).orElse(this.caught = this.caught()).accept(t);
    }
}
