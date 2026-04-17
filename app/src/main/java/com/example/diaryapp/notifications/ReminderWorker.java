package com.example.diaryapp.notifications;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class ReminderWorker extends Worker {
    public ReminderWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        String medicationName = getInputData().getString("name");
        String dosage = getInputData().getString("dosage");

        NotificationHelper.showNotification(
                getApplicationContext(),
                "Time for Medication!",
                "Please take " + medicationName + " (" + dosage + ")"
        );

        return Result.success();
    }
}
