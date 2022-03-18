package com.chamberland.kickmyb.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.chamberland.kickmyb.R;
import com.chamberland.kickmyb.databinding.ActivityConnexionBinding;
import com.chamberland.kickmyb.databinding.ActivityCreateBinding;
import com.chamberland.kickmyb.http.RetrofitUtil;
import com.chamberland.kickmyb.http.Service;
import com.chamberland.kickmyb.utils.DateFormatter;
import com.fasterxml.jackson.annotation.JsonFormat;

import org.kickmyb.transfer.AddTaskRequest;

import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateActivity extends BaseActivity {
    private ActivityCreateBinding binding;
    private String taskName;
    private Date taskDeadline;
    private Service service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        service = RetrofitUtil.get();
        currentActivity = "Create";
        this.setTitle("Ajouter");
        createEventsListeners();
    }

    private void createEventsListeners() {
        binding.btnCreateTask.setOnClickListener(v -> {
            setAddTaskInputs();
            AddTaskRequest addTaskRequest = getAddTaskRequest(taskName, taskDeadline);
            requestAddTask(addTaskRequest);
            Intent i = new Intent(CreateActivity.this, HomeActivity.class);
            startActivity(i);
        });
    }

    private void setAddTaskInputs(){
        taskName = String.valueOf(binding.inputTaskName.getEditText().getText());
        taskDeadline = DateFormatter.getDateFromDatePicker(binding.taskDueDate);
    }

    private AddTaskRequest getAddTaskRequest(String name, Date deadline){
        AddTaskRequest addTaskRequest = new AddTaskRequest();
        addTaskRequest.name = name;
        addTaskRequest.deadline = deadline;
        return addTaskRequest;
    }

    private void requestAddTask(AddTaskRequest addTaskRequest){
        service.addTask(addTaskRequest).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()){
                    Log.i("CREATE", "Response is successful");
                } else {
                    Log.i("CREATE", "Response is not successful");
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.i("CREATE", "Request failed");
            }
        });
    }
}