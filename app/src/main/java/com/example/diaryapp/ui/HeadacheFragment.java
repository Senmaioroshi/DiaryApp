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
import com.example.diaryapp.data.HeadacheLog;

import java.util.Locale;

public class HeadacheFragment extends Fragment {
    private MainViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_headache, container, false);

        viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

        SeekBar sbIntensity = view.findViewById(R.id.sb_intensity);
        com.google.android.material.textfield.TextInputEditText etTriggers = view.findViewById(R.id.et_triggers);
        com.google.android.material.textfield.TextInputEditText etDuration = view.findViewById(R.id.et_duration);
        Button btnSave = view.findViewById(R.id.btn_save_headache);
        RecyclerView rv = view.findViewById(R.id.rv_headache);

        LogAdapter<HeadacheLog> adapter = new LogAdapter<>(
                new LogAdapter.LogBinder<HeadacheLog>() {
                    @Override
                    public String getTitle(HeadacheLog item) {
                        return String.format(Locale.getDefault(), "Интенсивность: %d, Причины: %s, Длительность: %s",
                                item.intensity, item.triggers, item.duration);
                    }

                    @Override
                    public long getTimestamp(HeadacheLog item) {
                        return item.timestamp;
                    }
                },
                item -> {
                    viewModel.deleteHeadache(item);
                    Toast.makeText(getContext(), "Запись удалена", Toast.LENGTH_SHORT).show();
                }
        );

        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setAdapter(adapter);

        viewModel.getAllHeadacheLogs().observe(getViewLifecycleOwner(), adapter::setItems);

        btnSave.setOnClickListener(v -> {
            int intensity = sbIntensity.getProgress() + 1;
            String triggers = etTriggers.getText().toString();
            String duration = etDuration.getText().toString();
            
            viewModel.insertHeadache(intensity, triggers, duration);
            
            etTriggers.setText("");
            etDuration.setText("");
            Toast.makeText(getContext(), "Запись сохранена", Toast.LENGTH_SHORT).show();
        });

        return view;
    }
}
