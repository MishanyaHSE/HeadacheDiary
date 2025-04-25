package com.example.headachediary.presentation.view.auth;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.headachediary.R;

public class AuthActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);


        if (savedInstanceState == null) {
            // Создаем и отображаем фрагмент входа
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, new LoginFragment(), "login_fragment")
                    .commit();
        }
    }

    // Метод для замены фрагментов
    public void replaceFragment(Fragment fragment, String tag) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment, tag)
                .addToBackStack(null) // Добавляем в стек для кнопки "Назад"
                .commit();
    }
}
