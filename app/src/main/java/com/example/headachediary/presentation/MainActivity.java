package com.example.headachediary.presentation;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.headachediary.R;
import com.example.headachediary.data.network.RetrofitClient;

import com.example.headachediary.domain.auth.UserResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getUser(this);
    }

    private void getUser(Context context) {
        RetrofitClient.getApiService(context).getCurrentUser().enqueue(new Callback<UserResponse>() {

            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful()) {
                    UserResponse user = response.body();
                    Log.d("GETTING USER", "user id: " + user.getId());
                    Log.d("GETTING USER", "user email: " + user.getEmail());
                    Log.d("GETTING USER", "user name: " + user.getName());
                } else {
                    Log.e("GETTING USER", "Error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Log.e("GETTING USER", "Failure: " + t.getMessage());
            }
        });
    }

    public void startSurvey(View view) {
//        Intent intent = new Intent(this, FirstQuestionActivity.class);
//        startActivity(intent);
    }
}