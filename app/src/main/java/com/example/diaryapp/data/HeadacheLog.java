package com.example.diaryapp.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "headache_logs")
public class HeadacheLog {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public int intensity; // 1-10
    public String triggers;
    public String duration; // e.g., "2 hours"
    public long timestamp;

    public HeadacheLog(int intensity, String triggers, String duration, long timestamp) {
        this.intensity = intensity;
        this.triggers = triggers;
        this.duration = duration;
        this.timestamp = timestamp;
    }
}
