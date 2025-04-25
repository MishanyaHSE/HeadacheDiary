package com.example.headachediary.presentation.view.auth;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.headachediary.R;

public class ForgotPasswordFragment extends Fragment {
    private EditText emailEditText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forgot_password, container, false);

        emailEditText = view.findViewById(R.id.emailEditText);
        Button resetButton = view.findViewById(R.id.resetButton);
        TextView backToLoginText = view.findViewById(R.id.backToLoginText);

        resetButton.setOnClickListener(v -> attemptReset());
        backToLoginText.setOnClickListener(v -> goBackToLogin());

        return view;
    }

    private void attemptReset() {
        String email = emailEditText.getText().toString().trim();

        if (email.isEmpty()) {
            emailEditText.setError("Введите email");
            return;
        }
        // Реализовать сброс пароля
        Toast.makeText(getContext(), "Инструкции отправлены на " + email, Toast.LENGTH_SHORT).show();
        goBackToLogin();
    }

    private void goBackToLogin() {
        requireActivity().getSupportFragmentManager().popBackStack();
    }
}
