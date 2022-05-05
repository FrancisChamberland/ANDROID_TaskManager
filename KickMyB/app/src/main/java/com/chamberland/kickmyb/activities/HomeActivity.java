package com.chamberland.kickmyb.activities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chamberland.kickmyb.R;
import com.chamberland.kickmyb.adapters.TaskAdapter;
import com.chamberland.kickmyb.databinding.ActivityHomeBinding;
import com.chamberland.kickmyb.http.RetrofitUtil;
import com.chamberland.kickmyb.http.Service;
import com.chamberland.kickmyb.transfer.Task;
import com.google.android.material.snackbar.Snackbar;

import org.kickmyb.transfer.HomeItemResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends BaseActivity {
    private ActivityHomeBinding binding;
    private TaskAdapter adapter;
    private Service service;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        service = RetrofitUtil.get();
        currentActivity = "Home";
        this.setTitle(getString(R.string.home_title));
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        createEventsListeners();
        initRecycler();
        initProgressDialog(getString(R.string.loading_tasks));
    }

    private void initProgressDialog(String title){
        progressDialog = new ProgressDialog(HomeActivity.this, R.style.LoadingDialogStyle);
        progressDialog.setTitle(title);
        progressDialog.setMessage(getString(R.string.loading));
    }

    @Override
    protected void onResume() {
        super.onResume();
        requestTasks();
        bindingBase.drawerLayoutID.closeDrawer(Gravity.LEFT, false);
    }

    private void createEventsListeners() {
        binding.btnAddTask.setOnClickListener(v -> {
            Intent i = new Intent(HomeActivity.this, CreateActivity.class);
            startActivity(i);
        });
    }

    private void requestTasks(){
        progressDialog.show();
        service.getTasks().enqueue(new Callback<List<Task>>() {
            @Override
            public void onResponse(Call<List<Task>> call, Response<List<Task>> response) {
                if (response.isSuccessful()){
                    progressDialog.dismiss();
                    Log.i("HOME", "Response is successful");
                    adapter.set(response.body());
                } else{
                    try {
                        progressDialog.dismiss();
                        Log.i("HOME", "Response is not successful");
                        if (response.code() == 403){
                            Intent i = new Intent(HomeActivity.this, ConnexionActivity.class);
                            startActivity(i);
                            return;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onFailure(Call<List<Task>> call, Throwable t) {
                progressDialog.dismiss();
                Log.i("HOME", "Request failed");
                Snackbar.make(binding.homeLayout, R.string.connexion_failed, Snackbar.LENGTH_LONG).show();
            }
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