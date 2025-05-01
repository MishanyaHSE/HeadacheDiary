package com.example.headachediary.data.source.network;


import com.example.headachediary.domain.Message;
import com.example.headachediary.domain.headache.NoteResponse;
import com.example.headachediary.domain.auth.TokenResponse;
import com.example.headachediary.domain.auth.UserAuth;
import com.example.headachediary.domain.auth.UserCreate;
import com.example.headachediary.domain.auth.UserResponse;
import com.example.headachediary.domain.headache.QuestionData;
import com.example.headachediary.domain.headache.QuestionResponse;

import java.time.LocalDate;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;


public interface HeadacheAPI {
    @POST("register")
    Call<UserResponse> registerUser(@Body UserCreate user);



    @POST("login")
    Call<TokenResponse> login(@Body UserAuth user);

//    @POST("login/1")
//    MutableLiveData<TokenResponse> login1(@Body UserAuth user);

    @GET("users/me")
    Call<UserResponse> getCurrentUser();

    @GET("users/notes/{date}")
    Call<ArrayList<NoteResponse>> getNotesForMonth(@Path("date") LocalDate date);

    @DELETE("/users/notes/one/{date}/")
    Call<Message> deleteNoteForDate(@Path("date") LocalDate date);

    @GET("users/questions")
    Call<QuestionResponse> getQuestionsForUser();

    @PUT("users/questions")
    Call<QuestionResponse> saveQuestionsForUser(@Body QuestionData questions);

    @GET("users/report/{date}")
    Call<ResponseBody> getReport(@Path("date") LocalDate date);


}
