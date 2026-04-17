package com.example.diaryapp.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "medication_logs")
public class MedicationLog {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public int medicationId;
    public String medicationName;
    public long timestamp;

    public MedicationLog(int medicationId, String medicationName, long timestamp) {
        this.medicationId = medicationId;
        this.medicationName = medicationName;
        this.timestamp = timestamp;
    }
}
