package com.example.diaryapp.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.diaryapp.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Locale;

public class BMIFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bmi, container, false);

        TextInputEditText etHeight = view.findViewById(R.id.et_height);
        TextInputEditText etWeight = view.findViewById(R.id.et_weight);
        Button btnCalculate = view.findViewById(R.id.btn_calculate_bmi);
        TextView tvResult = view.findViewById(R.id.tv_bmi_result);
        TextView tvInterpretation = view.findViewById(R.id.tv_bmi_interpretation);

        btnCalculate.setOnClickListener(v -> {
            String heightStr = etHeight.getText().toString();
            String weightStr = etWeight.getText().toString();

            if (!heightStr.isEmpty() && !weightStr.isEmpty()) {
                float height = Float.parseFloat(heightStr) / 100; // convert cm to m
                float weight = Float.parseFloat(weightStr);

                if (height > 0) {
                    float bmi = weight / (height * height);
                    tvResult.setText(String.format(Locale.getDefault(), "Ваш ИМТ: %.2f", bmi));
                    tvInterpretation.setText(getInterpretation(bmi));
                }
            }
        });

        return view;
    }

    private String getInterpretation(float bmi) {
        if (bmi < 18.5) return "Недостаточный вес";
        if (bmi < 25) return "Нормальный вес";
        if (bmi < 30) return "Избыточный вес";
        return "Ожирение";
    }
}
