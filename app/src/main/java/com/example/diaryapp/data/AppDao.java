package com.example.diaryapp.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface AppDao {
    @Insert
    void insertMedication(Medication medication);

    @Query("SELECT * FROM medications ORDER BY hour, minute")
    LiveData<List<Medication>> getAllMedications();

    @Delete
    void deleteMedication(Medication medication);

    @Insert
    void insertMedicationLog(MedicationLog log);

    @Query("SELECT * FROM medication_logs ORDER BY timestamp DESC")
    LiveData<List<MedicationLog>> getAllMedicationLogs();

    @Insert
    void insertSymptomLog(SymptomLog log);

    @Query("SELECT * FROM symptom_logs ORDER BY timestamp DESC")
    LiveData<List<SymptomLog>> getAllSymptomLogs();

    @Delete
    void deleteSymptomLog(SymptomLog log);

    @Insert
    void insertHeadacheLog(HeadacheLog log);

    @Query("SELECT * FROM headache_logs ORDER BY timestamp DESC")
    LiveData<List<HeadacheLog>> getAllHeadacheLogs();

    @Delete
    void deleteHeadacheLog(HeadacheLog log);
}
