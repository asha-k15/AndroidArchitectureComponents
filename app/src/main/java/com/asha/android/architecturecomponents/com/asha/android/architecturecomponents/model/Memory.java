package com.asha.android.architecturecomponents.com.asha.android.architecturecomponents.model;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.asha.android.architecturecomponents.com.asha.android.architecturecomponents.util.Converters;


@Entity(tableName = "memory_table")
@TypeConverters({Converters.class})
public class Memory {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String memoryInfo;
    private String date_info;
    private int priority;

    public Memory(String title, String memoryInfo, String date_info, int priority) {
        this.title = title;
        this.memoryInfo = memoryInfo;
        this.date_info = date_info;
        this.priority = priority;
    }

    /*public Memory(String title, String memoryInfo) {
        this.title = title;
        this.memoryInfo = memoryInfo;

        this.date_info = Date.valueOf((new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault())).toString());
    }*/

    public void setId(int id) {
        this.id = id;
    }

    public void setDate_info(String date_info) {
        this.date_info = date_info;
    }
    public int getPriority() {
        return priority;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getMemoryInfo() {
        return memoryInfo;
    }

    public String getDate_info() {
        return date_info;
    }


}
