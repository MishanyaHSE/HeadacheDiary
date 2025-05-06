package com.example.headachediary.data.source.network;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.headachediary.data.source.util.manager.TokenManager;
import com.example.headachediary.domain.auth.TokenResponse;
import com.example.headachediary.presentation.view.auth.AuthActivity;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RefreshInterceptor implements Interceptor {

    private Context context;
    private TokenManager tokenManager;


    public RefreshInterceptor(Context context, TokenManager tokenManager) {
        this.context = context;
        this.tokenManager = tokenManager;
    }


    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request originalRequest = chain.request();
        Response response = chain.proceed(originalRequest);
        Request newRequest;
        if (response.code() == 401) {
            response.close();
            HeadacheAPI headacheAPI = RetrofitClient.getApiServiceWithoutRefreshInterceptor(context);
            String auth = "Bearer " + tokenManager.getRefreshToken();
            // Синхронный запрос на обновление токена
            retrofit2.Response<TokenResponse> refreshResponse = headacheAPI.refreshToken(auth).execute();
            Log.d("REFRESING", "Response executed");
            if (refreshResponse.isSuccessful()){
                tokenManager.saveToken(refreshResponse.body().getAccessToken(),
                        refreshResponse.body().getRefreshToken());

                newRequest = originalRequest.newBuilder()
                        .header("Authorization", "Bearer " + tokenManager.getToken())
                        .build();
                Log.d("REFRESING", "Success");
                return chain.proceed(newRequest);
            } else {
                refreshFailed();
            }
        }
        return response;
    }

    private void refreshFailed() {
        tokenManager.deleteTokens();
        Intent intent = new Intent(context, AuthActivity.class);
        context.startActivity(intent);
    }

}
