package com.asha.android.architecturecomponents.com.asha.android.architecturecomponents.dao;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.asha.android.architecturecomponents.com.asha.android.architecturecomponents.model.Memory;
import com.asha.android.architecturecomponents.com.asha.android.architecturecomponents.db.MemoryDatabase;

import java.util.List;

public class MemoryRepository {
    private MemoryDao memoryDao;
    LiveData<List<Memory>> memories;

    public MemoryRepository(Application application) {
        MemoryDatabase memoryDatabase = MemoryDatabase.getInstance(application);
        this.memoryDao = memoryDatabase.memoryDao();
        memories = memoryDao.getAllMemories();
    }

    public void insert(Memory memory){
        new InsertMemoryAsyncTask(memoryDao).execute(memory);
    }

    public void update(Memory memory){
        new UpdateMemoryAsyncTask(memoryDao).execute(memory);
    }

    public void delete(Memory memory){
        new DeleteMemoryAsyncTask(memoryDao).execute(memory);
    }

    public LiveData<List<Memory>> getAllMemories(){
       return  memories;
    }

    public void deleteAllNotes() {
        new DeleteAllNotesAsyncTask(memoryDao).execute();
    }
    private static class InsertMemoryAsyncTask extends AsyncTask<Memory,Void,Void>{
        private MemoryDao memoryDao;


        public InsertMemoryAsyncTask(MemoryDao memoryDao) {
            this.memoryDao = memoryDao;
        }

        @Override
        protected Void doInBackground(Memory... memories) {
            memoryDao.insert(memories[0]);
            return null;
        }
    }

    private static class UpdateMemoryAsyncTask extends AsyncTask<Memory,Void,Void>{
        private MemoryDao memoryDao;


        public UpdateMemoryAsyncTask(MemoryDao memoryDao) {
            this.memoryDao = memoryDao;
        }

        @Override
        protected Void doInBackground(Memory... memories) {
            memoryDao.update(memories[0]);
            return null;
        }
    }

    private static class DeleteMemoryAsyncTask extends AsyncTask<Memory,Void,Void>{
        private MemoryDao memoryDao;


        public DeleteMemoryAsyncTask(MemoryDao memoryDao) {
            this.memoryDao = memoryDao;
        }

        @Override
        protected Void doInBackground(Memory... memories) {
            memoryDao.delete(memories[0]);
            return null;
        }
    }

    private static class DeleteAllNotesAsyncTask extends AsyncTask<Memory,Void,Void>{
        private MemoryDao memoryDao;

        public DeleteAllNotesAsyncTask(MemoryDao memoryDao) {
            this.memoryDao = memoryDao;
        }
        @Override
        protected Void doInBackground(Memory... memories) {
            memoryDao.deleteAllMemories();
            return null;
        }
    }
}
