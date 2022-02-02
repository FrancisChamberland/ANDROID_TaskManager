package com.chamberland.kickmyb.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chamberland.kickmyb.R;
import com.chamberland.kickmyb.adapters.TaskAdapter;
import com.chamberland.kickmyb.databinding.ActivityHomeBinding;
import com.chamberland.kickmyb.models.Task;

import java.time.LocalDateTime;

@RequiresApi(api = Build.VERSION_CODES.O)
public class HomeActivity extends BaseActivity {
    private ActivityHomeBinding binding;
    private TaskAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        this.setTitle("Accueil");
        createEventsListeners();
        initRecycler();
        fillRecycler();
    }

    @Override
    void createEventsListeners() {
        binding.btnAddTask.setOnClickListener(v -> {
            Intent i = new Intent(HomeActivity.this, CreateActivity.class);
            startActivity(i);
        });
    }

    private void initRecycler(){
        RecyclerView recyclerView = findViewById(R.id.taskList);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new TaskAdapter();
        recyclerView.setAdapter(adapter);
    }

    private void fillRecycler() {
        for (int i = 0; i < 200; i++){
            LocalDateTime dueDateTime = LocalDateTime.of(2022, 2, 25, 11, 50);
            Task task = new Task("Faire cela", dueDateTime);
            adapter.add(task);
        }
    }
}