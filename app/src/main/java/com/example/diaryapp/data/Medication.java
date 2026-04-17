package com.example.diaryapp.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "medications")
public class Medication {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String name;
    public String dosage;
    public int hour;
    public int minute;
    public boolean isActive;

    public Medication(String name, String dosage, int hour, int minute) {
        this.name = name;
        this.dosage = dosage;
        this.hour = hour;
        this.minute = minute;
        this.isActive = true;
    }
}
