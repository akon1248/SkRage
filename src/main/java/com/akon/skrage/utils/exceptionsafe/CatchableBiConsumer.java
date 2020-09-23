package com.akon.skrage.utils.exceptionsafe;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class CatchableBiConsumer<T, U, X extends Throwable> implements BiConsumer<T, U>, Catchable<Consumer<? super X>, BiConsumer<? super T, ? super U>, BiConsumer<T, U>> {

    private final ThrowsBiConsumer<T, U, X> throwsBiConsumer;
    private BiConsumer<T, U> caught;

    @Override
    public BiConsumer<T, U> caught(@Nullable Consumer<? super X> c, @Nullable BiConsumer<? super T, ? super U> f) {
        return (t, u) -> {
            try {
                this.throwsBiConsumer.accept(t, u);
            } catch (Throwable ex) {
                Optional.ofNullable(c).ifPresent(consumer -> consumer.accept((X)ex));
            } finally {
                Optional.ofNullable(f).ifPresent(biConsumer -> biConsumer.accept(t, u));
                ExceptionSafe.closeResource(t);
                ExceptionSafe.closeResource(u);
            }
        };
    }

    @Override
    public void accept(T t, U u) {
        Optional.ofNullable(this.caught).orElse(this.caught = this.caught()).accept(t, u);
    }
}
