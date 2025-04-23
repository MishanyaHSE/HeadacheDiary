package com.example.headachediary.presentation.auth;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.headachediary.R;
import com.example.headachediary.domain.auth.UserCreate;
import com.example.headachediary.domain.auth.UserResponse;
import com.example.headachediary.data.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterFragment extends Fragment {
    private EditText emailEditText, passwordEditText, confirmPasswordEditText, nameEditText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        emailEditText = view.findViewById(R.id.emailEditText);
        passwordEditText = view.findViewById(R.id.passwordEditText);
        confirmPasswordEditText = view.findViewById(R.id.confirmPasswordEditText);
        nameEditText = view.findViewById(R.id.nameEditText);
        Button registerButton = view.findViewById(R.id.registerButton);
        TextView loginText = view.findViewById(R.id.loginText);

        registerButton.setOnClickListener(v -> attemptRegister());
        loginText.setOnClickListener(v -> goToLogin());

        return view;
    }


    private void attemptRegister() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String confirmPassword = confirmPasswordEditText.getText().toString().trim();
        String name = nameEditText.getText().toString().trim();

        // Валидация
        if (email.isEmpty()) {
            emailEditText.setError("Введите email");
            return;
        }

        if (name.isEmpty()) {
            nameEditText.setError("Введите имя");
            return;
        }

        if (password.isEmpty()) {
            passwordEditText.setError("Введите пароль");
            return;
        }

        if (!password.equals(confirmPassword)) {
            confirmPasswordEditText.setError("Пароли не совпадают");
            return;
        }

        registerUser(name, email, password);

    }

    private void goToLogin() {
        // Возврат к экрану входа
        requireActivity().getSupportFragmentManager().popBackStack();
    }


    private void registerUser(String name, String email, String password) {
        UserCreate user = new UserCreate();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);

        RetrofitClient.getApiService(requireContext()).registerUser(user).enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful()) {
                    UserResponse createdUser = response.body();
                    Log.d("REGISTRATION", "User created: " + createdUser.getEmail());
                    goToLogin();
                } else {
                    Log.e("REGISTRATION", "Error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Log.e("REGISTRATION", "Failure: " + t.getMessage());
            }
        });
    }
}
