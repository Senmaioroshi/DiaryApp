package com.example.diaryapp.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "symptom_logs")
public class SymptomLog {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String type; // e.g., "Mood", "General Symptom"
    public String value; // 1-5 for mood, or description for symptom
    public long timestamp;

    public SymptomLog(String type, String value, long timestamp) {
        this.type = type;
        this.value = value;
        this.timestamp = timestamp;
    }
}
