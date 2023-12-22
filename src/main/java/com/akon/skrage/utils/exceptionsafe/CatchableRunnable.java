package com.akon.skrage.utils.exceptionsafe;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.Consumer;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class CatchableRunnable<X extends Throwable> implements Runnable, Catchable<Consumer<? super X>, Runnable, Runnable> {

    private final ThrowsRunnable<X> throwsRunnable;
    private Runnable caught;

    @Override
    public Runnable onCatch(@Nullable Consumer<? super X> c, @Nullable Runnable f) {
        return () -> {
            try {
                this.throwsRunnable.run();
            } catch (Throwable ex) {
                Optional.ofNullable(c).ifPresent(consumer -> consumer.accept((X)ex));
            } finally {
                Optional.ofNullable(f).ifPresent(Runnable::run);
            }
        };
    }

    @Override
    public void run() {
        Optional.ofNullable(this.caught).orElse(this.caught = this.onCatch()).run();
    }
}
