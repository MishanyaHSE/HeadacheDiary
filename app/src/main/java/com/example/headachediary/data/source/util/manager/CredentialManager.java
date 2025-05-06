package com.example.headachediary.data.source.util.manager;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.headachediary.domain.auth.UserAuth;

public class CredentialManager {

    private static final String APP_PREFERENCES = "myprefs";
    private static final String LOGIN_KEY = "login";
    private static final String PASSWORD_KEY = "password";

    private SharedPreferences preferences;

    public CredentialManager(Context context) {
        preferences = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
    }

    public void saveCred(UserAuth cred) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(LOGIN_KEY, cred.getUsername());
        editor.putString(PASSWORD_KEY, cred.getPassword());
        editor.apply();
    }

    public UserAuth getCred() {
        UserAuth cred = new UserAuth();
        cred.setUsername(preferences.getString(LOGIN_KEY, null));
        cred.setPassword(preferences.getString(PASSWORD_KEY, null));
        return cred;
    }

    public void deleteCred() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
    }
}


