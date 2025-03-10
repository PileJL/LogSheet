package com.example.logsheet.Logs;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.logsheet.R;

import java.util.List;

public class LogsAdapter extends RecyclerView.Adapter<LogsViewHolder> {

    Context context;
    List<LogsItem> items;
    LogsSelectListener listener;

    public LogsAdapter(Context context, List<LogsItem> items, LogsSelectListener listener) {
        this.context = context;
        this.items = items;
        this.listener = listener;
    }

    @NonNull
    @Override
    public LogsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LogsViewHolder(LayoutInflater.from(context).inflate(R.layout.logs_item, parent, false) );
    }

    @Override
    public void onBindViewHolder(@NonNull LogsViewHolder holder, int position) {
        holder.title.setText(items.get(position).getTitle());
        holder.activeness.setText(items.get(position).getActiveness());

        // change color of activeness based on passed value
        if (items.get(position).getActiveness().equalsIgnoreCase("Highly Active")) {
            holder.activeness.setTextColor(ContextCompat.getColor(context, R.color.green));
        }
        else if (items.get(position).getActiveness().equalsIgnoreCase("Inactive")) {
            holder.activeness.setTextColor(ContextCompat.getColor(context, R.color.red));
        }
        else if (items.get(position).getActiveness().equalsIgnoreCase("Low Activity")) {
            holder.activeness.setTextColor(ContextCompat.getColor(context, R.color.orange));
        }
        else if (items.get(position).getActiveness().equalsIgnoreCase("Moderate Activity")) {
            holder.activeness.setTextColor(ContextCompat.getColor(context, R.color.yellow));
        }

        // set onclick of item
        holder.itemContainer.setOnClickListener(v -> listener.onItemClicked(items.get(position)));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
