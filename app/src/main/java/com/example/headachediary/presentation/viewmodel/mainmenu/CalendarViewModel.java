package com.example.headachediary.presentation.viewmodel.mainmenu;

import android.Manifest;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.headachediary.data.repository.NotesRepository;
import com.example.headachediary.data.source.network.ApiCallback;
import com.example.headachediary.domain.Message;
import com.example.headachediary.domain.headache.NoteResponse;
import com.example.headachediary.presentation.viewmodel.StateBase;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.concurrent.Executors;

import okhttp3.ResponseBody;

public class CalendarViewModel extends AndroidViewModel {

    NotesRepository notesRepository;

    private MutableLiveData<StateBase<ArrayList<NoteResponse>>> notesLoadingState;
    private MutableLiveData<StateBase<Message>> notesDeletingState;
    private MutableLiveData<StateBase<String>> reportLoadingState;

    private MutableLiveData<StateBase<String>> reportSavingState;

    public LiveData<StateBase<ArrayList<NoteResponse>>> getNotesLoadingState() {

        return notesLoadingState;
    }

    public LiveData<StateBase<Message>> getNoteDeletingState() {
        return notesDeletingState;
    }

    public LiveData<StateBase<String>> getReportLoadingState() {
        return reportLoadingState;
    }

    public LiveData<StateBase<String>> getReportSavingState() {
        return reportSavingState;
    }

    public CalendarViewModel(@NonNull Application application) {
        super(application);
        notesRepository = new NotesRepository(application.getApplicationContext());
        notesLoadingState = new MutableLiveData<>();
        notesDeletingState = new MutableLiveData<>();
        reportLoadingState = new MutableLiveData<>();
        reportSavingState = new MutableLiveData<>();
    }


    public void loadDates(LocalDate date){
        notesLoadingState.postValue(StateBase.loading());
        Executors.newSingleThreadExecutor().execute(() -> {
            notesRepository.getNotesForMonth(date, new ApiCallback<>() {
                @Override
                public void onSuccess(ArrayList<NoteResponse> data) {
                    notesLoadingState.postValue(StateBase.success(data));
                }

                @Override
                public void onError(String error) {
                    notesLoadingState.postValue(StateBase.error(error));
                }
            });
        });
    }

    public void deleteNotes(LocalDate date) {
        notesDeletingState.postValue(StateBase.loading());
        Executors.newSingleThreadExecutor().execute(() -> {
            notesRepository.deleteNoteForDate(date, new ApiCallback<Message>() {
                @Override
                public void onSuccess(Message data) {
                    notesDeletingState.postValue(StateBase.success(data));
                }

                @Override
                public void onError(String error) {
                    notesDeletingState.postValue(StateBase.error(error));
                }
            });
        });
    }

    public void getReport(LocalDate date) {
        reportLoadingState.postValue(StateBase.loading());
        Executors.newSingleThreadExecutor().execute(() -> {
            notesRepository.getReport(date, new ApiCallback<ResponseBody>() {

                @Override
                public void onSuccess(ResponseBody data) {
                    File file = saveFile(data.byteStream());
                    reportLoadingState.postValue(StateBase.success(file.getAbsolutePath()));
                }

                @Override
                public void onError(String error) {
                    Log.d("ERROR IN LOADING", error);
                    reportLoadingState.postValue(StateBase.error(error));
                }
            });
        });
    }

    private File saveFile(InputStream inputStream) {
        File downloadsDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOWNLOADS);
//        reportSavingState.postValue(StateBase.loading());
        File file = new File(downloadsDir, "HeadacheReport_" + System.currentTimeMillis() + ".pdf");
        try {
//            Executors.newSingleThreadExecutor().execute(() -> {
                try (FileOutputStream fos = new FileOutputStream(file)) {
                    byte[] buffer = new byte[8192];
                    int bytesReader;
                    while ((bytesReader = inputStream.read(buffer)) != -1){
                        fos.write(buffer, 0, bytesReader);
                    }
                } catch (IOException e) {
//                    reportSavingState.postValue(StateBase.error(e.getMessage()));
                }
//                reportSavingState.postValue(StateBase.success(file.getAbsolutePath()));
//            });
        } catch (Exception e) {
//            reportSavingState.postValue(StateBase.error(e.getMessage()));
        }
        return file;
    }

    public boolean isStoragePermissionGranted(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            return true; // Разрешение не требуется для Downloads на Android 10+
        }
        return ContextCompat.checkSelfPermission(context,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }



}