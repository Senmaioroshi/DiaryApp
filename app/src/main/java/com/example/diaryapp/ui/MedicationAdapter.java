package com.example.diaryapp.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.diaryapp.R;
import com.example.diaryapp.data.Medication;

import java.util.ArrayList;
import java.util.List;

public class MedicationAdapter extends RecyclerView.Adapter<MedicationAdapter.ViewHolder> {
    private List<Medication> medications = new ArrayList<>();
    private OnMedicationClickListener listener;

    public interface OnMedicationClickListener {
        void onTakeClick(Medication medication);
        void onDeleteClick(Medication medication);
    }

    public void setMedications(List<Medication> medications) {
        this.medications = medications;
        notifyDataSetChanged();
    }

    public void setListener(OnMedicationClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_medication, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Medication medication = medications.get(position);
        holder.tvName.setText(medication.name);
        holder.tvDosage.setText(medication.dosage);
        holder.tvTime.setText(String.format("%02d:%02d", medication.hour, medication.minute));
        
        holder.btnTake.setOnClickListener(v -> {
            if (listener != null) listener.onTakeClick(medication);
        });

        holder.btnDelete.setOnClickListener(v -> {
            if (listener != null) listener.onDeleteClick(medication);
        });
    }

    @Override
    public int getItemCount() {
        return medications.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvDosage, tvTime;
        Button btnTake;
        ImageButton btnDelete;

        ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvDosage = itemView.findViewById(R.id.tv_dosage);
            tvTime = itemView.findViewById(R.id.tv_time);
            btnTake = itemView.findViewById(R.id.btn_take);
            btnDelete = itemView.findViewById(R.id.btn_delete_med);
        }
    }
}
