package com.example.headachediary.presentation.view.mainmenu;


import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.headachediary.R;
import com.example.headachediary.domain.headache.NoteResponse;
import com.example.headachediary.presentation.viewmodel.StateBase;
import com.example.headachediary.presentation.viewmodel.mainmenu.CalendarViewModel;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CalendarFragment extends Fragment {
//    private CalendarView calendarView;



    private MaterialCalendarView calendarView;
    private LinearLayout buttonsGroup;
    private Button btnAdd, btnDelete, btnEdit;
    private LocalDate dateForDelete;

    private ProgressDialog progressDialog;


    private Map<LocalDate, Boolean> headacheDays = new HashMap<>();

    private ArrayList<NoteResponse> monthNotes = new ArrayList<>();// Дата + наличие головной боли


    private CalendarViewModel calendarViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);

        calendarViewModel =  new ViewModelProvider(this).get(CalendarViewModel.class);

        // Инициализация элементов
        buttonsGroup = view.findViewById(R.id.buttonsGroup);
        btnAdd = view.findViewById(R.id.btnAdd);
        btnDelete = view.findViewById(R.id.btnDelete);
        btnEdit = view.findViewById(R.id.btnEdit);
        calendarView = view.findViewById(R.id.calendarView);
        //Получаем с сервера ноуты
        calendarViewModel.loadDates(LocalDate.now());
        setupLoadingObservers();
        setupDeletingObservers();

        calendarView.setOnMonthChangedListener((widget, date) -> {
            hideButtons();
            LocalDate localDate = LocalDate.of(
                    date.getYear(),
                    date.getMonth(),
                    date.getDay()
            );
            calendarViewModel.loadDates(localDate);
            Log.d("CURRENT MONTH AND YEAR", "DATE: " + localDate);
        });


        // Обработчик выбора даты
        calendarView.setOnDateChangedListener((widget, date, selected) -> {
            hideButtons();
            LocalDate localDate = LocalDate.of(
                    date.getYear(),
                    date.getMonth(),
                    date.getDay()
            );

            if (headacheDays.containsKey(localDate)) {
                buttonsGroup.setVisibility(View.VISIBLE);
            } else if (!localDate.isAfter(LocalDate.now())) {
                Log.d("CHECKED DATE", localDate.toString());
                Log.d("LOCAL DATE", LocalDate.now().toString());
                btnAdd.setVisibility(View.VISIBLE);
            }
        });

        btnDelete.setOnClickListener(v -> deleteNoteClick());
//        requestPermissionLauncher.launch();

        return view;
    }

    private void deleteNoteClick() {
        hideButtons();
        CalendarDay date = calendarView.getSelectedDate();
        dateForDelete = LocalDate.of(
                date.getYear(),
                date.getMonth(),
                date.getDay()
        );
        calendarViewModel.deleteNotes(dateForDelete);

    }

    private void markHeadacheDays() {
        // 1. Очищаем предыдущие отметки
        calendarView.removeDecorators();

        // 2. Подготовка списков дат
        List<CalendarDay> headacheDaysList = new ArrayList<>();
        List<CalendarDay> noHeadacheDaysList = new ArrayList<>();
        for (Map.Entry<LocalDate, Boolean> entry : headacheDays.entrySet()) {
            LocalDate dateTime = entry.getKey();
            CalendarDay day = CalendarDay.from(
                    dateTime.getYear(),
                    dateTime.getMonthValue(), // январь=0
                    dateTime.getDayOfMonth()
            );
            if (entry.getValue()) {
                headacheDaysList.add(day); // Головная боль есть - красная точка
            } else {
                noHeadacheDaysList.add(day); // Нет головной боли - зеленая точка
            }
        }


        // 3. Декоратор для дней с головной болью (красные точки)
        calendarView.addDecorator(new DayViewDecorator() {
            @Override
            public boolean shouldDecorate(CalendarDay day) {
                return headacheDaysList.contains(day);
            }

            @Override
            public void decorate(DayViewFacade view) {
                view.addSpan(new DotSpan(8, Color.RED));
            }
        });

        // 4. Декоратор для дней без головной боли (зеленые точки)
        calendarView.addDecorator(new DayViewDecorator() {
            @Override
            public boolean shouldDecorate(CalendarDay day) {
                return noHeadacheDaysList.contains(day);
            }

            @Override
            public void decorate(DayViewFacade view) {
                view.addSpan(new DotSpan(8, Color.GREEN));
            }
        });
    }

    public void setupDeletingObservers() {
        calendarViewModel.getNoteDeletingState().observe(getViewLifecycleOwner(), state -> {
            switch(state.getStatus()) {
                case LOADING:
                    showLoading("Удаляем запись");
                    break;
                case SUCCESS:
                    headacheDays.remove(dateForDelete);
                    markHeadacheDays();
                    hideLoading();
                    break;
                case ERROR:
                    hideLoading();
                    showError(state.getError());
                    break;
            }
        });
    }


    private void setupLoadingObservers() {
        calendarViewModel.getNotesLoadingState().observe(getViewLifecycleOwner(), state -> {
            switch(state.getStatus()) {
                case LOADING:
                    showLoading("Загружаем записи...");
                    break;
                case SUCCESS:
                    headacheDays.clear();
                    monthNotes.clear();
                    monthNotes.addAll(state.getData());
                    if (state.getData() != null && !state.getData().isEmpty()) {
                        for (NoteResponse note : monthNotes) {
                            DateTimeFormatter formatter = null;
                            LocalDate dateTime = null;
                            formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                            dateTime = LocalDate.parse(note.getDate(), formatter);
                            headacheDays.put(dateTime, note.getIsHeadache());
                        }
                    }
                    markHeadacheDays();
                    hideLoading();
                    break;
                case ERROR:
                    hideLoading();
                    showError(state.getError());
                    break;
            }
        });
    }

    private void showLoading(String message) {
        progressDialog = new ProgressDialog(requireContext());
        progressDialog.setMessage(message);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    private void hideLoading() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    private void showError(String message) {
        new AlertDialog.Builder(requireContext())
                .setTitle("Ошибка")
                .setMessage(message)
                .setPositiveButton("OK", null)
                .show();
    }


    private void showButtonsForDate(int year, int month, int dayOfMonth) {
        // Показываем кнопки
        buttonsGroup.setVisibility(View.VISIBLE);
        btnAdd.setVisibility(View.VISIBLE);

        // Сохраняем дату в тег кнопки (пример)
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, dayOfMonth);
        btnAdd.setTag(calendar.getTimeInMillis());

        // Дополнительная логика...
    }

    private void hideButtons() {
        buttonsGroup.setVisibility(View.GONE);
        btnAdd.setVisibility(View.GONE);
    }


    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    Toast.makeText(requireContext(), "Разрешение получено",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(requireContext(), "Разрешение отклонено",
                            Toast.LENGTH_SHORT).show();
                }
            });

    private void checkStoragePermission() {
        // Для API >= 33 (Android 13+) используем отдельные разрешения
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(requireContext(),
                    Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED) {
                requestPermissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES);
            }
        }
        // Для API 29-32 (Android 10-12L)
        else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            // Разрешение не требуется для записи в папку Downloads
        }
        // Для API < 29
        else {
            if (ContextCompat.checkSelfPermission(requireContext(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
        }
    }

    private boolean isStoragePermissionGranted() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q ||
                ContextCompat.checkSelfPermission(requireContext(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES);
        } else if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.Q) {
            requestPermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        } else {
            calendarViewModel.saveFile(requireContext());
        }
    }

    private void saveFile() {
        if (calendarViewModel.isStoragePermissionGranted(requireContext())) {
            calendarViewModel.saveFile(requireContext());
        } else {
            requestPermission();
        }
    }

    private void handleSaveResult() {
        calendarViewModel.getReportSavingState().observe(getViewLifecycleOwner(), state -> {
            switch(state.getStatus()) {
                case LOADING:
                    showLoading("Сохраняем файл...");
                case SUCCESS:
                    Toast.makeText(requireContext(), "Сохранено в " + state.getData(),
                            Toast.LENGTH_SHORT);
            }
        });
    }

    private void setupLoadingReportObserver() {
        calendarViewModel.getReportLoadingState().observe(getViewLifecycleOwner(), state -> {
            switch(state.getStatus()) {
                case LOADING:
                    showLoading("Загружаем файл...");
                    break;
                case SUCCESS:
                    requestPermission();
            }
        });
    }

    private void reportButtonClick() {
        CalendarDay date = calendarView.getSelectedDate();
        LocalDate reportDate = LocalDate.of(
                date.getYear(),
                date.getMonth(),
                date.getDay()
        );
        calendarViewModel.getReport(reportDate);
    }

}




