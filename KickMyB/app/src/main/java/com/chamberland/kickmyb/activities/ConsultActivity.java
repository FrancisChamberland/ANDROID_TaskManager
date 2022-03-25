package com.chamberland.kickmyb.activities;

import android.os.Bundle;
import android.util.Log;
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
        Gson gson = new Gson();
        String jsonTask = getIntent().getStringExtra("task");
        task = gson.fromJson(jsonTask, TaskDetailResponse.class);
        taskProgress = task.percentageDone;
        setTaskBinding();
        createEventsListeners();
    }

    private void setTaskBinding(){
        binding.detailTaskName.setText(task.name);
        binding.detailTaskDueDate.setText(DateFormatter.getFormatted(task.deadline, "yyyy-MM-dd"));
        binding.detailTaskElapsedTime.setText(String.format("%s%%", task.percentageTimeSpent));
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
        binding.btnSaveTask.setOnClickListener(v -> {
            requestUpdateProgress();
        });
    }

    private void requestUpdateProgress(){
        service.updateProgress(task.id, taskProgress).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()){
                    Log.i("UPDATE", "Response is successful");
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