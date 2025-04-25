package com.example.headachediary.data.source.util.manager;

import android.content.Context;
import android.content.SharedPreferences;

public class TokenManager {
    private static final String APP_PREFERENCES = "myprefs";
    private static final String TOKEN_KEY = "token";

    private SharedPreferences preferences;

    public TokenManager(Context context) {
        preferences = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
    }

    public void saveToken(String token) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(TOKEN_KEY, token);
        editor.apply();
    }

    public String getToken() {
        return preferences.getString(TOKEN_KEY, null);
    }
}
