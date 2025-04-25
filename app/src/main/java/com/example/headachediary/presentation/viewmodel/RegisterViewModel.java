package com.example.headachediary.presentation.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.headachediary.data.repository.Repository;
import com.example.headachediary.domain.auth.UserCreate;

public class RegisterViewModel extends AndroidViewModel {
    Repository repository;

    public RegisterViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application.getApplicationContext());
    }

    public void registerUser(UserCreate userCreate) {
        repository.registerUser(userCreate);
    }
}
