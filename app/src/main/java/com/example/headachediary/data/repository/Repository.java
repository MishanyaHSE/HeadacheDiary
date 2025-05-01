package com.example.headachediary.data.repository;

import android.content.Context;

import com.example.headachediary.data.source.network.ApiCallback;
import com.example.headachediary.data.source.network.HeadacheAPI;
import com.example.headachediary.data.source.network.RetrofitCallback;
import com.example.headachediary.data.source.network.RetrofitClient;
import com.example.headachediary.domain.auth.TokenResponse;
import com.example.headachediary.domain.auth.UserAuth;
import com.example.headachediary.domain.auth.UserCreate;
import com.example.headachediary.domain.auth.UserResponse;

import retrofit2.Callback;


public class Repository {

    private Context context;
    private final HeadacheAPI apiService;

    public Repository(Context context) {
        this.apiService = RetrofitClient.getApiService(context);
        this.context = context;
    }



    public void registerUser(UserCreate user, Callback<UserResponse> call){
        apiService.registerUser(user).enqueue(call);
    }

    public void registerUser1(UserCreate user, ApiCallback<UserResponse> call){
        apiService.registerUser(user).enqueue(new RetrofitCallback<>(call));
    }


    public void login(UserAuth user, ApiCallback<TokenResponse> call){
        apiService.login(user).enqueue(new RetrofitCallback<>(call));
    }

    public void getUser(ApiCallback<UserResponse> call) {
        apiService.getCurrentUser().enqueue(new RetrofitCallback<>(call));
    }

//    Call<UserResponse> getCurrentUser(@Header("Authorization") String token);
//    Call<UserResponse> getCurrentUser(){
//
//    }
//
//    Call<List<NoteResponse>> getUsersNotes(){
//
//    }
}
