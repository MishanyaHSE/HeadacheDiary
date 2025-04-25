package com.example.headachediary.presentation.view.mainmenu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import com.example.headachediary.R;

public class CalendarFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);

        // Логика взаимодействия с CalendarView и кнопками
        CalendarView calendarView = view.findViewById(R.id.calendarView);
        LinearLayout buttonsGroup = view.findViewById(R.id.buttonsGroup);

        calendarView.setOnDateChangeListener((view1, year, month, dayOfMonth) -> {
            buttonsGroup.setVisibility(View.VISIBLE); // Показываем кнопки при выборе даты
        });

        return view;
    }
}