package com.example.logsheet.Logs;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.logsheet.R;

public class LogsViewHolder extends RecyclerView.ViewHolder {

    TextView title, activeness;
    ImageView deleteButton;
    CardView itemContainer;
    public LogsViewHolder(@NonNull View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.title);
        activeness = itemView.findViewById(R.id.activeness);
        deleteButton = itemView.findViewById(R.id.deleteButton);
        itemContainer = itemView.findViewById(R.id.cardview);

    }
}
