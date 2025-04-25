package com.example.headachediary.presentation.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.headachediary.data.repository.Repository;
import com.example.headachediary.domain.auth.UserAuth;

public class LoginViewModel extends AndroidViewModel {
    LiveData<RegistrationState> registrationState;
    Repository repository;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application.getApplicationContext());
    }

    public void login(UserAuth auth) {
        repository.login(auth);
    }
}

enum  RegistrationState{
    LOADING,
    SUCCESS,
    ERROR
}
