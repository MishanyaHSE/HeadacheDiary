package com.example.headachediary.presentation.view.mainmenu;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import com.example.headachediary.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Настройка Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Календарь");

        // Настройка навигации
        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();

        // Подключение BottomNavigationView к NavController
        NavigationUI.setupWithNavController(bottomNav, navController);

        // Смена заголовка Toolbar при переключении фрагментов
        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            getSupportActionBar().setTitle(destination.getLabel());
        });
    }
}
