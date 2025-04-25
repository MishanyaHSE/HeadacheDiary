package com.example.headachediary.presentation.view.auth;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.headachediary.R;
import com.example.headachediary.data.repository.Repository;
import com.example.headachediary.domain.auth.UserAuth;
import com.example.headachediary.presentation.viewmodel.LoginViewModel;

public class LoginFragment extends Fragment {

    private LoginViewModel loginViewModel;
    private Repository repository;
    private EditText emailEditText, passwordEditText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        emailEditText = view.findViewById(R.id.emailEditText);
        passwordEditText = view.findViewById(R.id.passwordEditText);
        Button loginButton = view.findViewById(R.id.loginButton);
        Button registerButton = view.findViewById(R.id.registerButton);
        TextView forgotPassword = view.findViewById(R.id.forgotPasswordText);

        loginButton.setOnClickListener(v -> attemptLogin());
        registerButton.setOnClickListener(v -> goToRegister());
        forgotPassword.setOnClickListener(v -> goToForgotPassword());
        repository = new Repository(requireContext());
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        return view;
    }

    private void attemptLogin() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        UserAuth cred = new UserAuth();
        cred.setUsername(email);
        cred.setPassword(password);
        Log.d("GETTING TOKEN", cred.toString());
        loginViewModel.login(cred);

//        RetrofitClient.getApiService(requireContext()).login(cred).enqueue(new Callback<TokenResponse>() {
//
//            @Override
//            public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {
//                if (response.isSuccessful()) {
//                    TokenResponse token = response.body();
//                    Log.d("GETTING TOKEN", "Got token: " + token.getAccess_token());
//                    TokenManager tokenManager = new TokenManager(requireContext());
//                    tokenManager.saveToken(token.getAccess_token());
//                    Intent intent = new Intent(requireContext(), MainActivity.class);
//                    startActivity(intent);
//                } else {
//                    Log.e("GETTING TOKEN", "Error: " + response.code());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<TokenResponse> call, Throwable t) {
//                Log.e("GETTING TOKEN", "Failure: " + t.getMessage());
//            }
//        });

    }

    private void goToRegister() {
        ((AuthActivity)requireActivity())
                .replaceFragment(new RegisterFragment(), "register_fragment");
    }

    private void goToForgotPassword() {
        ((AuthActivity)requireActivity())
                .replaceFragment(new ForgotPasswordFragment(), "forgot_password_fragment");
    }
}
