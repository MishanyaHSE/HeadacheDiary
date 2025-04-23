package com.example.headachediary.data.network;

import android.content.Context;

import com.example.headachediary.data.util.manager.TokenManager;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static final String BASE_URL = "http://10.0.2.2:8000/"; // Для эмулятора Android
    private static Retrofit retrofit = null;

    public static HeadacheAPI getApiService(Context context) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        AuthInterceptor authInterceptor = new AuthInterceptor(new TokenManager(context), context);
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .addInterceptor(authInterceptor)
                .build();
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(HeadacheAPI.class);
    }
}
