package com.example.logsheet.DayLogs;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.logsheet.R;

public class DayLogsViewHolder extends RecyclerView.ViewHolder {

    TextView title, intensity, duration;
    ImageView deleteButton;
    CardView itemContainer;

    public DayLogsViewHolder(@NonNull View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.title);
        intensity = itemView.findViewById(R.id.intensity);
        duration = itemView.findViewById(R.id.duration);
        deleteButton = itemView.findViewById(R.id.deleteButton);
        itemContainer = itemView.findViewById(R.id.cardview);
    }
}
