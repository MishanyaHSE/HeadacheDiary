package com.example.headachediary.data.source.network;

public interface ApiCallback<T> {
    void onSuccess(T data);
    void onError(String error);
}
