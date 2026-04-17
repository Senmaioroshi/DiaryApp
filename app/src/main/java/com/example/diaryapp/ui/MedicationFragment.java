package com.example.diaryapp.ui;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.diaryapp.MainViewModel;
import com.example.diaryapp.R;
import com.example.diaryapp.data.Medication;
import com.google.android.material.textfield.TextInputEditText;

public class MedicationFragment extends Fragment {
    private MainViewModel viewModel;
    private int selectedHour = 8;
    private int selectedMinute = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_medication, container, false);

        viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

        TextInputEditText etName = view.findViewById(R.id.et_med_name);
        TextInputEditText etDosage = view.findViewById(R.id.et_med_dosage);
        Button btnPickTime = view.findViewById(R.id.btn_pick_time);
        Button btnAdd = view.findViewById(R.id.btn_add_med);
        RecyclerView rv = view.findViewById(R.id.rv_medications);

        MedicationAdapter adapter = new MedicationAdapter();
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setAdapter(adapter);

        adapter.setListener(new MedicationAdapter.OnMedicationClickListener() {
            @Override
            public void onTakeClick(Medication medication) {
                viewModel.logMedicationTaken(medication);
                Toast.makeText(getContext(), "Прием зафиксирован: " + medication.name, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDeleteClick(Medication medication) {
                viewModel.deleteMedication(medication);
                Toast.makeText(getContext(), "Удалено", Toast.LENGTH_SHORT).show();
            }
        });

        viewModel.getAllMedications().observe(getViewLifecycleOwner(), adapter::setMedications);

        btnPickTime.setOnClickListener(v -> {
            TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), (view1, hourOfDay, minute) -> {
                selectedHour = hourOfDay;
                selectedMinute = minute;
                btnPickTime.setText(String.format("Время: %02d:%02d", hourOfDay, minute));
            }, selectedHour, selectedMinute, true);
            timePickerDialog.show();
        });

        btnAdd.setOnClickListener(v -> {
            String name = etName.getText().toString();
            String dosage = etDosage.getText().toString();
            if (!name.isEmpty() && !dosage.isEmpty()) {
                viewModel.insertMedication(new Medication(name, dosage, selectedHour, selectedMinute));
                etName.setText("");
                etDosage.setText("");
                Toast.makeText(getContext(), "Добавлено в график", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
