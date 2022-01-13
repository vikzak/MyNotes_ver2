package ru.gb.mynotes_ver2.domain;

public interface Callback<T> {
    void onSuccess(T result);
    void onError(Throwable throwable); //error
}
