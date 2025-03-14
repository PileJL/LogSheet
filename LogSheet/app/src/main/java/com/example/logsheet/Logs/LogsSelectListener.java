package com.example.logsheet.Logs;

import android.content.Context;

public interface LogsSelectListener {
    void onItemClicked(LogsItem item);
    void onDeleteClicked(LogsItem item, Context context);
}
