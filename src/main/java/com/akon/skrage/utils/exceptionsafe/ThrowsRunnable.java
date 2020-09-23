package com.akon.skrage.utils.exceptionsafe;

public interface ThrowsRunnable<X extends Throwable> {

    void run() throws X;

    default ThrowsRunnable<? extends Throwable> andThen(ThrowsRunnable<? extends Throwable> after) {
        return () -> {this.run(); after.run();};
    }

    default ThrowsRunnable<X> andThen(Runnable after) {
        return () -> {this.run(); after.run();};
    }

}
