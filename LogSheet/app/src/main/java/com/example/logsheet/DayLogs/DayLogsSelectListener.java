package com.example.logsheet.DayLogs;

import android.content.Context;

public interface DayLogsSelectListener {
    void onItemClicked(DayLogsItem item);
    void onDeleteClicked(DayLogsItem item, Context context);
}
