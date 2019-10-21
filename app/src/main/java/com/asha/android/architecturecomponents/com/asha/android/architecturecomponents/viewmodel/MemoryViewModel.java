package com.asha.android.architecturecomponents.com.asha.android.architecturecomponents.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.asha.android.architecturecomponents.com.asha.android.architecturecomponents.dao.MemoryRepository;
import com.asha.android.architecturecomponents.com.asha.android.architecturecomponents.model.Memory;

import java.util.List;

public class MemoryViewModel extends AndroidViewModel {
    private LiveData<List<Memory>> allMemories;
    private MemoryRepository repository;

    public MemoryViewModel(@NonNull Application application) {
        super(application);
        repository = new MemoryRepository(application);
        allMemories = repository.getAllMemories();
    }

    public void insert(Memory memory) {
        repository.insert(memory);
    }

    public void update(Memory memory) {
        repository.update(memory);
    }

    public void delete(Memory memory) {
        repository.delete(memory);
    }

    public void deleteAllMemories() {
        repository.deleteAllNotes();
    }

    public LiveData<List<Memory>> getAllMemories() {
        return allMemories;
    }
}
