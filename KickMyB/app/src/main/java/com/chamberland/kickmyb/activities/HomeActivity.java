package com.chamberland.kickmyb.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chamberland.kickmyb.R;
import com.chamberland.kickmyb.adapters.TaskAdapter;
import com.chamberland.kickmyb.databinding.ActivityHomeBinding;
import com.chamberland.kickmyb.http.RetrofitUtil;
import com.chamberland.kickmyb.http.Service;
import com.chamberland.kickmyb.transfer.Task;

import org.kickmyb.transfer.HomeItemResponse;

import java.time.LocalDateTime;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends BaseActivity {
    private ActivityHomeBinding binding;
    private TaskAdapter adapter;
    private Service service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        service = RetrofitUtil.get();
        currentActivity = "Home";
        this.setTitle("Accueil");
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        createEventsListeners();
        initRecycler();
        requestTasks();
    }

    private void requestTasks(){
        service.getTasks().enqueue(new Callback<List<Task>>() {
            @Override
            public void onResponse(Call<List<Task>> call, Response<List<Task>> response) {
                if (response.isSuccessful()){
                    Log.i("HOME", "Response is successful");
                    adapter.set(response.body());
                } else{
                    Log.i("HOME", "Response is not successful");
                }
            }
            @Override
            public void onFailure(Call<List<Task>> call, Throwable t) {
                Log.i("HOME", "Request failed");
            }
        });
    }

    private void createEventsListeners() {
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
}