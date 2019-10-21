package com.asha.android.architecturecomponents.com.asha.android.architecturecomponents.dao;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.asha.android.architecturecomponents.com.asha.android.architecturecomponents.model.Memory;

import java.util.List;

@Dao
public interface MemoryDao {
    @Insert
    void insert(Memory memory);

    @Update
    void update(Memory memory);

    @Delete
    void delete(Memory memory);

    @Query("DELETE FROM memory_table")
    void deleteAllMemories();

    @Query("SELECT * FROM memory_table ORDER BY priority DESC")
    LiveData<List<Memory>> getAllMemories();


}
