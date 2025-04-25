package com.example.headachediary.data.source.network;

import com.example.headachediary.domain.headache.NoteResponse;
import com.example.headachediary.domain.auth.TokenResponse;
import com.example.headachediary.domain.auth.UserAuth;
import com.example.headachediary.domain.auth.UserCreate;
import com.example.headachediary.domain.auth.UserResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;

import retrofit2.http.GET;
import retrofit2.http.POST;


public interface HeadacheAPI {
    @POST("register")
    Call<UserResponse> registerUser(@Body UserCreate user);


    @POST("login")
    Call<TokenResponse> login(@Body UserAuth user);

    @GET("users/me")
//    Call<UserResponse> getCurrentUser(@Header("Authorization") String token);
    Call<UserResponse> getCurrentUser();

    @GET("users/notes")
    Call<List<NoteResponse>> getUsersNotes();
}
