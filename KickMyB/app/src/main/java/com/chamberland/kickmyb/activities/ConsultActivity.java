package com.chamberland.kickmyb.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.SeekBar;

import com.chamberland.kickmyb.databinding.ActivityConsultBinding;
import com.chamberland.kickmyb.utils.DateFormatter;
import com.google.gson.Gson;

import org.kickmyb.transfer.TaskDetailResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConsultActivity extends BaseActivity {
    private ActivityConsultBinding binding;
    private TaskDetailResponse task;
    private int taskProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityConsultBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        currentActivity = "Consult";
        this.setTitle("Tâche");
        createEventsListeners();
    }

    @Override
    protected void onResume() {
        super.onResume();
        long taskId = getIntent().getLongExtra("taskId", -1);
        requestTaskDetail(taskId);
        bindingBase.drawerLayoutID.closeDrawer(Gravity.LEFT, false);
    }

    private void createEventsListeners(){
        binding.btnUpTaskProgress.setOnClickListener(v -> {
            upProgress();
        });
        binding.btnDownTaskProgress.setOnClickListener(v -> {
            downProgress();
        });
        binding.taskProgressSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                taskProgress = progress;
                updateProgress();
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        binding.btnSaveProgress.setOnClickListener(view -> {
            requestUpdateProgress();
        });
    }

    private void setTaskBinding(){
        binding.detailTaskName.setText(task.name);
        binding.detailTaskDueDate.setText(DateFormatter.getFormatted(task.deadline, "yyyy-MM-dd"));
        binding.detailTaskElapsedTime.setText(String.format("%s%%", task.percentageTimeSpent));
        taskProgress = task.percentageDone;
        updateProgress();
    }

    private void upProgress(){
        if (taskProgress >= 100) return;
        taskProgress += 1;
        updateProgress();
    }

    private void downProgress(){
        if (taskProgress <= 0) return;
        taskProgress -= 1;
        updateProgress();
    }

    private void updateProgress(){
        binding.detailTaskProgressPercentage.setText(String.format("%s%%", taskProgress));
        binding.taskProgressSeekBar.setProgress(taskProgress);
        binding.detailTaskProgressBar.setProgress(taskProgress);
    }

    private void requestTaskDetail(long id){
        service.detail(id).enqueue(new Callback<TaskDetailResponse>() {
            @Override
            public void onResponse(Call<TaskDetailResponse> call, Response<TaskDetailResponse> response) {
                if (response.isSuccessful()){
                    Log.i("DETAIL", "Response is successful");
                    task = response.body();
                    setTaskBinding();
                } else {
                    Log.i("DETAIL", "Response is not successful");
                }
            }
            @Override
            public void onFailure(Call<TaskDetailResponse> call, Throwable t) {
                Log.i("DETAIL", "Resquest failed");
            }
        });
    }

    private void requestUpdateProgress(){
        service.updateProgress(task.id, taskProgress).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()){
                    Log.i("UPDATE", "Response is successful");
                    Intent i = new Intent(ConsultActivity.this, HomeActivity.class);
                    startActivity(i);
                } else {
                    Log.i("UPDATE", "Response is not successful");
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.i("UPDATE", "Request failed");
            }
        });
    }
}