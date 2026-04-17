package com.example.diaryapp;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.work.Data;
import androidx.work.ExistingWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.example.diaryapp.data.AppDao;
import com.example.diaryapp.data.AppDatabase;
import com.example.diaryapp.data.HeadacheLog;
import com.example.diaryapp.data.Medication;
import com.example.diaryapp.data.MedicationLog;
import com.example.diaryapp.data.SymptomLog;
import com.example.diaryapp.notifications.ReminderWorker;

import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MainViewModel extends AndroidViewModel {
    private final AppDao appDao;
    private final WorkManager workManager;

    public MainViewModel(@NonNull Application application) {
        super(application);
        AppDatabase db = AppDatabase.getDatabase(application);
        appDao = db.appDao();
        workManager = WorkManager.getInstance(application);
    }

    public LiveData<List<Medication>> getAllMedications() {
        return appDao.getAllMedications();
    }

    public void insertMedication(Medication medication) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            appDao.insertMedication(medication);
            scheduleReminder(medication);
        });
    }

    public void deleteMedication(Medication medication) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            appDao.deleteMedication(medication);
            workManager.cancelUniqueWork("med_" + medication.name);
        });
    }

    public LiveData<List<MedicationLog>> getAllMedicationLogs() {
        return appDao.getAllMedicationLogs();
    }

    public void logMedicationTaken(Medication medication) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            appDao.insertMedicationLog(new MedicationLog(medication.id, medication.name, System.currentTimeMillis()));
        });
    }

    public void insertSymptom(String type, String value) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            appDao.insertSymptomLog(new SymptomLog(type, value, System.currentTimeMillis()));
        });
    }

    public void deleteSymptom(SymptomLog log) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            appDao.deleteSymptomLog(log);
        });
    }

    public LiveData<List<SymptomLog>> getAllSymptomLogs() {
        return appDao.getAllSymptomLogs();
    }

    public void insertHeadache(int intensity, String triggers, String duration) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            appDao.insertHeadacheLog(new HeadacheLog(intensity, triggers, duration, System.currentTimeMillis()));
        });
    }

    public void deleteHeadache(HeadacheLog log) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            appDao.deleteHeadacheLog(log);
        });
    }

    public LiveData<List<HeadacheLog>> getAllHeadacheLogs() {
        return appDao.getAllHeadacheLogs();
    }

    private void scheduleReminder(Medication medication) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, medication.hour);
        calendar.set(Calendar.MINUTE, medication.minute);
        calendar.set(Calendar.SECOND, 0);

        long delay = calendar.getTimeInMillis() - System.currentTimeMillis();
        if (delay < 0) {
            delay += TimeUnit.DAYS.toMillis(1);
        }

        Data data = new Data.Builder()
                .putString("name", medication.name)
                .putString("dosage", medication.dosage)
                .build();

        OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(ReminderWorker.class)
                .setInitialDelay(delay, TimeUnit.MILLISECONDS)
                .setInputData(data)
                .build();

        workManager.enqueueUniqueWork("med_" + medication.name, ExistingWorkPolicy.REPLACE, request);
    }
}
