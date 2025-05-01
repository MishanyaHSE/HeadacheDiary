package com.example.headachediary.data.repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.headachediary.data.source.network.ApiCallback;
import com.example.headachediary.data.source.network.HeadacheAPI;
import com.example.headachediary.data.source.network.RetrofitCallback;
import com.example.headachediary.data.source.network.RetrofitClient;
import com.example.headachediary.domain.Message;
import com.example.headachediary.domain.headache.NoteResponse;
import com.example.headachediary.domain.headache.QuestionData;
import com.example.headachediary.domain.headache.QuestionResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotesRepository {

    private Context context;
    private final HeadacheAPI apiService;



    public NotesRepository(Context context) {
        this.apiService = RetrofitClient.getApiService(context);
        this.context = context;
    }


    public void getNotesForMonth(LocalDate date, ApiCallback<ArrayList<NoteResponse>> call) {
        apiService.getNotesForMonth(date).enqueue(new RetrofitCallback<>(call));
    }

    public void deleteNoteForDate(LocalDate date, ApiCallback<Message> call) {
        apiService.deleteNoteForDate(date).enqueue(new RetrofitCallback<>(call));
    }

    public void getQuestions(ApiCallback<QuestionResponse> call) {
        apiService.getQuestionsForUser().enqueue(new RetrofitCallback<>(call));
    }

    public void saveQuestions(QuestionData questions, ApiCallback<QuestionResponse> call) {
        apiService.saveQuestionsForUser(questions).enqueue(new RetrofitCallback<>(call));
    }

    public void getReport(LocalDate date, ApiCallback<ResponseBody> call) {
        apiService.getReport(date).enqueue(new RetrofitCallback<>(call));
    }


//    public int deleteNotesByDate(LocalDate date) throws IOException {
//        Call<DeleteCountResponse> call = apiService.deleteNotesByDate(date);
//        Response<DeleteCountResponse> response = call.execute();
//        if (response.isSuccessful()) {
//            return response.body().getDeletedCount();
//        }
//        throw new IOException("Delete failed: " + response.code());
//    }
}
