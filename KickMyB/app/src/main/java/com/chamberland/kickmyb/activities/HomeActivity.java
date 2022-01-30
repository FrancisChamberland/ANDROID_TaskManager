package com.chamberland.kickmyb.activities;

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

        this.setTitle("Home");
        createEventsListeners();
        initRecycler();
        fillRecycler();
    }

    @Override
    void createEventsListeners() {

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
            LocalDateTime endDateTime = LocalDateTime.of(2022, 2, 10, 10, 10);
            Task task = new Task("Faire cela", endDateTime);
            adapter.add(task);
        }
    }
}