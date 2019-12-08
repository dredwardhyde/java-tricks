package com.company;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

class Failure<T> extends Try<T> {
    private final Throwable e;

    Failure(Throwable e) {
        this.e = e;
    }

    @Override
    public T get() throws Throwable {
        throw e;
    }

    @Override
    public T orElse(T value) {
        return value;
    }

    @Override
    public <U> Failure<U> flatMap(Function<? super T, Try<U>> f) {
        Objects.requireNonNull(f);
        return Try.failure(e);
    }
}

class Success<T> extends Try<T> {

    private final T value;

    Success(T value) {
        this.value = value;
    }

    @Override
    public T get() {
        return value;
    }

    @Override
    public T orElse(T value) {
        return this.value;
    }

    @Override
    public <U> Try<U> flatMap(Function<? super T, Try<U>> f) {
        Objects.requireNonNull(f);
        return f.apply(value);
    }
}


public abstract class Try<T> {

    public static <U> Try<U> ofThrowable(Supplier<U> f) {
        Objects.requireNonNull(f);
        try {
            return Try.successful(f.get());
        } catch (Throwable e) {
            return Try.failure(e);
        }
    }

    public static <U> Success<U> successful(U u) {
        return new Success<>(u);
    }

    public static <U> Failure<U> failure(Throwable e) {
        return new Failure<>(e);
    }

    public abstract T get() throws Throwable;

    public abstract T orElse(T value);

    public abstract <U> Try<U> flatMap(Function<? super T, Try<U>> f);
}
