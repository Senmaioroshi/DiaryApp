package com.example.diaryapp.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.diaryapp.MainViewModel;
import com.example.diaryapp.R;
import com.example.diaryapp.data.SymptomLog;

import java.util.Locale;

public class SymptomsFragment extends Fragment {
    private MainViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_symptoms, container, false);

        viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

        SeekBar sbMood = view.findViewById(R.id.sb_mood);
        com.google.android.material.textfield.TextInputEditText etSymptom = view.findViewById(R.id.et_symptom);
        Button btnSave = view.findViewById(R.id.btn_save_symptom);
        RecyclerView rv = view.findViewById(R.id.rv_symptoms);

        LogAdapter<SymptomLog> adapter = new LogAdapter<>(
                new LogAdapter.LogBinder<SymptomLog>() {
                    @Override
                    public String getTitle(SymptomLog item) {
                        return String.format(Locale.getDefault(), "%s: %s", 
                                item.type.equals("Mood") ? "Настроение" : "Симптом", item.value);
                    }

                    @Override
                    public long getTimestamp(SymptomLog item) {
                        return item.timestamp;
                    }
                },
                item -> {
                    viewModel.deleteSymptom(item);
                    Toast.makeText(getContext(), "Запись удалена", Toast.LENGTH_SHORT).show();
                }
        );

        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setAdapter(adapter);

        viewModel.getAllSymptomLogs().observe(getViewLifecycleOwner(), adapter::setItems);

        btnSave.setOnClickListener(v -> {
            int mood = sbMood.getProgress() + 1;
            viewModel.insertSymptom("Mood", String.valueOf(mood));

            String symptom = etSymptom.getText().toString();
            if (!symptom.isEmpty()) {
                viewModel.insertSymptom("Symptom", symptom);
                etSymptom.setText("");
            }
            Toast.makeText(getContext(), "Запись сохранена", Toast.LENGTH_SHORT).show();
        });

        return view;
    }
}
