package com.example.logsheet.Logs;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.logsheet.R;
import com.example.logsheet.Utilities.Utility;

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
        Utility.setActivenessColor(context, holder.activeness, items.get(position).getActiveness());

        // set onclick of item
        holder.itemContainer.setOnClickListener(v -> listener.onItemClicked(items.get(position)));

        // set editButton onClick
        holder.deleteButton.setOnClickListener(v -> listener.onDeleteClicked(items.get(position), context));

    }

    public void removeItem(int position) {
        items.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, items.size()); // Update other items' positions
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
