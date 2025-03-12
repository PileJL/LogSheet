package com.example.logsheet.DayLogs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.logsheet.Logs.LogsItem;
import com.example.logsheet.R;

import java.util.List;

public class DayLogsAdapter extends RecyclerView.Adapter<DayLogsViewHolder> {

    Context context;
    DayLogsSelectListener listener;

    public DayLogsAdapter(Context context, List<DayLogsItem> items, DayLogsSelectListener listener) {
        this.context = context;
        this.items = items;
        this.listener = listener;
    }

    List<DayLogsItem> items;

    @NonNull
    @Override
    public DayLogsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DayLogsViewHolder(LayoutInflater.from(context).inflate(R.layout.day_logs_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DayLogsViewHolder holder, int position) {
        holder.title.setText(items.get(position).getTitle());
        holder.duration.setText(items.get(position).getDuration());
        holder.intensity.setText(items.get(position).getIntensity());

        // change color of intensity based on passed value
        if (items.get(position).getIntensity().equalsIgnoreCase("Light")) {
            holder.intensity.setTextColor(ContextCompat.getColor(context, R.color.orange));
        }
        else if (items.get(position).getIntensity().equalsIgnoreCase("Moderate")) {
            holder.intensity.setTextColor(ContextCompat.getColor(context, R.color.yellow));
        }
        else if (items.get(position).getIntensity().equalsIgnoreCase("Vigorous")) {
            holder.intensity.setTextColor(ContextCompat.getColor(context, R.color.green));
        }

        // set onclick of item
        holder.itemContainer.setOnClickListener(v -> listener.onItemClicked(items.get(position)));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
