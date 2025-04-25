package com.example.headachediary.presentation.view.survey;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SurveyViewModel extends ViewModel {
    private final MutableLiveData<Note> surveyLiveData = new MutableLiveData<>();

    public void initSurvey() {
        surveyLiveData.setValue(new Note());
    }

    public LiveData<Note> getSurvey() {
        return surveyLiveData;
    }

    public void updateSurvey(Note updatedSurvey) {
        surveyLiveData.setValue(updatedSurvey);
    }

    @Override
    protected void onCleared() {
        // Очистка ресурсов при уничтожении
        super.onCleared();
    }
}
