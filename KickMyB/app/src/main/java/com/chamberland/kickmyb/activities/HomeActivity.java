package com.chamberland.kickmyb.activities;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chamberland.kickmyb.R;
import com.chamberland.kickmyb.adapters.TaskAdapter;
import com.chamberland.kickmyb.databinding.ActivityHomeBinding;

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

    }
}