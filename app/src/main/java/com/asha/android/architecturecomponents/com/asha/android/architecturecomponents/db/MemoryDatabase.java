package com.asha.android.architecturecomponents.com.asha.android.architecturecomponents.db;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.asha.android.architecturecomponents.com.asha.android.architecturecomponents.model.Memory;
import com.asha.android.architecturecomponents.com.asha.android.architecturecomponents.dao.MemoryDao;

@Database(entities = Memory.class, version = 2, exportSchema = false)
public abstract class MemoryDatabase extends RoomDatabase {

    private static MemoryDatabase memoryDatabase;

    public abstract MemoryDao memoryDao();

    public static synchronized MemoryDatabase getInstance(Context context){
        if(memoryDatabase == null){
            memoryDatabase = Room.databaseBuilder(context.getApplicationContext(),
                             MemoryDatabase.class, "memory_database")
                             .fallbackToDestructiveMigration()
                             .addCallback(roomCallBack)
                             .build();
        }
        return memoryDatabase;
    }

    private static RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db){
            super.onCreate(db);
            new PopulateDbAsyncTask(memoryDatabase).execute();
        }
    };


    private static class PopulateDbAsyncTask extends AsyncTask<Void,Void,Void>{

        private MemoryDao memoryDao;

        private PopulateDbAsyncTask(MemoryDatabase db){
            memoryDao = db.memoryDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            memoryDao.insert(new Memory("Title1","Went on hiking", "2019/02/01",2));
            memoryDao.insert(new Memory("Title2","Celebrated birthday party.", "2019/03/02",1));
            memoryDao.insert(new Memory("Title3","Visited parents.","2019/02/15",3));
            return null;
        }
    }


}
