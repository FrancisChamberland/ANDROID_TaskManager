package com.chamberland.kickmyb.activities;

import android.os.Bundle;
import android.view.View;

import com.chamberland.kickmyb.databinding.ActivityConsultBinding;
import com.chamberland.kickmyb.utils.DateFormatter;
import com.google.gson.Gson;

import org.kickmyb.transfer.TaskDetailResponse;

public class ConsultActivity extends BaseActivity {
    private ActivityConsultBinding binding;
    private TaskDetailResponse task;

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
        setTaskInfo();
    }

    private void setTaskInfo(){
        binding.detailTaskName.setText(task.name);
        binding.detailTaskDueDate.setText(DateFormatter.getFormatted(task.deadline, "yyyy-MM-dd"));
        binding.detailTaskProgressPercentage.setText(String.format("%s%%", task.percentageDone));
        binding.detailTaskElapsedTime.setText(String.format("%s%%", task.percentageTimeSpent));
        binding.detailTaskProgressBar.setProgress(task.percentageDone);
        binding.taskProgressSeekBar.setProgress(task.percentageDone);
    }
}