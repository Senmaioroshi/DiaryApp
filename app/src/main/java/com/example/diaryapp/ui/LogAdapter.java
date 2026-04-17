package com.example.diaryapp.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.diaryapp.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class LogAdapter<T> extends RecyclerView.Adapter<LogAdapter.ViewHolder> {
    private List<T> items = new ArrayList<>();
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM, HH:mm", new Locale("ru"));
    private final LogBinder<T> binder;
    private final OnDeleteListener<T> deleteListener;

    public interface LogBinder<T> {
        String getTitle(T item);
        long getTimestamp(T item);
    }

    public interface OnDeleteListener<T> {
        void onDelete(T item);
    }

    public LogAdapter(LogBinder<T> binder, OnDeleteListener<T> deleteListener) {
        this.binder = binder;
        this.deleteListener = deleteListener;
    }

    public void setItems(List<T> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_log, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        T item = items.get(position);
        holder.tvDate.setText(dateFormat.format(new Date(binder.getTimestamp(item))));
        holder.tvContent.setText(binder.getTitle(item));
        holder.btnDelete.setOnClickListener(v -> {
            if (deleteListener != null) deleteListener.onDelete(item);
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvDate, tvContent;
        ImageButton btnDelete;

        ViewHolder(View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tv_log_date);
            tvContent = itemView.findViewById(R.id.tv_log_content);
            btnDelete = itemView.findViewById(R.id.btn_delete_log);
        }
    }
}
