package com.asha.android.architecturecomponents.com.asha.android.architecturecomponents.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.ItemTouchHelper.SimpleCallback;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.asha.android.architecturecomponents.com.asha.android.architecturecomponents.model.Memory;
import com.asha.android.architecturecomponents.com.asha.android.architecturecomponents.db.MemoryAdapter;
import com.asha.android.architecturecomponents.com.asha.android.architecturecomponents.viewmodel.MemoryViewModel;
import com.asha.android.architecturecomponents.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {

    public static final int ADD_NOTE_REQUEST = 1;
    public static final int EDIT_NOTE_REQUEST = 2;

    private MemoryViewModel memoryViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton buttonAddNote = findViewById(R.id.button_add_note);
        buttonAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddOrEditMemoryActivity.class);
                startActivityForResult(intent, ADD_NOTE_REQUEST);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final MemoryAdapter adapter = new MemoryAdapter();
        recyclerView.setAdapter(adapter);

        memoryViewModel = ViewModelProviders.of(this).get(MemoryViewModel.class);
        memoryViewModel.getAllMemories().observe(this, new Observer<List<Memory>>() {
            @Override
            public void onChanged(@Nullable List<Memory> memories) {
                adapter.setMemories(memories);
            }
        });

        new ItemTouchHelper(new SimpleCallback(0,ItemTouchHelper.LEFT |ItemTouchHelper.RIGHT) {
            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                return 0;
            }

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                    memoryViewModel.delete(adapter.getMemoryAt(viewHolder.getAdapterPosition()));
                    Toast.makeText(MainActivity.this,"Message deleted",Toast.LENGTH_LONG).show();
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new MemoryAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(Memory memory) {
                Intent intent = new Intent(MainActivity.this, AddOrEditMemoryActivity.class);
                intent.putExtra(AddOrEditMemoryActivity.EXTRA_ID, memory.getId());
                intent.putExtra(AddOrEditMemoryActivity.EXTRA_TITLE, memory.getTitle());
                intent.putExtra(AddOrEditMemoryActivity.EXTRA_DESCRIPTION, memory.getMemoryInfo());
                intent.putExtra(AddOrEditMemoryActivity.EXTRA_PRIORITY, memory.getPriority());
                startActivityForResult(intent, EDIT_NOTE_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm a", Locale.US);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        if (requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK) {
            String title = data.getStringExtra(AddOrEditMemoryActivity.EXTRA_TITLE);
            String description = data.getStringExtra(AddOrEditMemoryActivity.EXTRA_DESCRIPTION);
            int priority = data.getIntExtra(AddOrEditMemoryActivity.EXTRA_PRIORITY, 1);


            Memory memory = new Memory(title, description, sdf.format(new Date()), priority);
            memoryViewModel.insert(memory);

            Toast.makeText(this, "Memory saved", Toast.LENGTH_SHORT).show();
        } else if (requestCode == EDIT_NOTE_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(AddOrEditMemoryActivity.EXTRA_ID, -1);

            if (id == -1) {
                Toast.makeText(this, "Memory can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }

            String title = data.getStringExtra(AddOrEditMemoryActivity.EXTRA_TITLE);
            String description = data.getStringExtra(AddOrEditMemoryActivity.EXTRA_DESCRIPTION);
            int priority = data.getIntExtra(AddOrEditMemoryActivity.EXTRA_PRIORITY, 1);

            Memory note = new Memory(title, description, sdf.format(new Date()), priority);
            note.setId(id);
            memoryViewModel.update(note);

            Toast.makeText(this, "Memory updated", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Memory not saved", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.delete_all :
                memoryViewModel.deleteAllMemories();
                Toast.makeText(this,"All Memories deleted",Toast.LENGTH_LONG);
                return true;
                default:return super.onOptionsItemSelected(item);
        }
    }
}
