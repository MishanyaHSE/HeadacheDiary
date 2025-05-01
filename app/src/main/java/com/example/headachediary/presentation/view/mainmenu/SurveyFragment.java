package com.example.headachediary.presentation.view.mainmenu;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.headachediary.R;
import com.example.headachediary.domain.headache.QuestionData;
import com.example.headachediary.presentation.viewmodel.mainmenu.QuestionsViewModel;
import com.google.android.material.switchmaterial.SwitchMaterial;

public class SurveyFragment extends Fragment {

    private SwitchMaterial switchPainTime, switchIntensity, switchMedication;
    private Button btnSave;

    private ProgressDialog progressDialog;

    private QuestionsViewModel questionsViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_survey, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Инициализация элементов
        switchPainTime = view.findViewById(R.id.switchPainTime);
        switchIntensity = view.findViewById(R.id.switchIntensity);
        switchMedication = view.findViewById(R.id.switchMedication);
        btnSave = view.findViewById(R.id.btnSave);


        questionsViewModel =  new ViewModelProvider(this).get(QuestionsViewModel.class);

        // Загрузка сохранённых данных (пример)
        loadSavedPreferences();
        questionsViewModel.getQuestions();
        setupLoadQuestionObservers();
        setupSavingObservers();

        // Обработчик сохранения
        btnSave.setOnClickListener(v -> savePreferences());
    }

    private void loadSavedPreferences() {
        // Пример загрузки (замените на реальные данные)
        /*
        SharedPreferences prefs = requireContext().getSharedPreferences("SurveyPrefs", Context.MODE_PRIVATE);
        switchPainTime.setChecked(prefs.getBoolean("pain_time", false));
        switchIntensity.setChecked(prefs.getBoolean("intensity", false));
        switchMedication.setChecked(prefs.getBoolean("medication", false));
        */
    }

    private void savePreferences() {
        // Пример сохранения
        /*
        SharedPreferences.Editor editor = requireContext()
            .getSharedPreferences("SurveyPrefs", Context.MODE_PRIVATE)
            .edit();

        editor.putBoolean("pain_time", switchPainTime.isChecked());
        editor.putBoolean("intensity", switchIntensity.isChecked());
        editor.putBoolean("medication", switchMedication.isChecked());
        editor.apply();
        */
        QuestionData questions = new QuestionData();
        questions.setSecondQuestion(switchPainTime.isChecked());
        questions.setThirdQuestion(switchIntensity.isChecked());
        questions.setFourthQuestion(switchMedication.isChecked());
        // Возврат назад
        questionsViewModel.updateQuestions(questions);
    }

    private void setupLoadQuestionObservers() {
        questionsViewModel.getLoadQuestionsState().observe(getViewLifecycleOwner(), state -> {
            switch(state.getStatus()) {
                case LOADING:
                    showLoading("Загружаем опрос...");
                    break;
                case SUCCESS:
                    switchPainTime.setChecked(state.getData().getSecondQuestion());
                    switchIntensity.setChecked(state.getData().getThirdQuestion());
                    switchMedication.setChecked(state.getData().getFourthQuestion());
                    hideLoading();
                    break;
                case ERROR:
                    hideLoading();
                    showError(state.getError());
                    break;
            }
        });
    }

    private void setupSavingObservers() {
        questionsViewModel.getSaveQuestionsState().observe(getViewLifecycleOwner(), state -> {
            switch(state.getStatus()) {
                case LOADING:
                    showLoading("Сохраняем опрос...");
                    break;
                case SUCCESS:
                    Log.d("SURVEY QUESTIONS", "SUCCSESSFULLY SAVED");
                    Toast.makeText(requireContext(), "Данные сохранены", Toast.LENGTH_LONG).show();
                    if (getActivity() != null) {
                        getActivity().onBackPressed();
                    }
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
}