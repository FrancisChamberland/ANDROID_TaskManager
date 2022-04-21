package com.chamberland.kickmyb.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.chamberland.kickmyb.R;
import com.chamberland.kickmyb.databinding.ActivityCreateBinding;
import com.chamberland.kickmyb.http.RetrofitUtil;
import com.chamberland.kickmyb.http.Service;
import com.chamberland.kickmyb.utils.DateFormatter;
import com.google.android.material.snackbar.Snackbar;

import org.kickmyb.transfer.AddTaskRequest;

import java.io.IOException;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateActivity extends BaseActivity {
    private ActivityCreateBinding binding;
    private String taskName;
    private Date taskDeadline;
    private Service service;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        service = RetrofitUtil.get();
        currentActivity = "Create";
        this.setTitle("Ajouter");
        initEventsListeners();
        initProgressDialog();
    }

    @Override
    protected void onResume() {
        super.onResume();
        bindingBase.drawerLayoutID.closeDrawer(Gravity.LEFT, false);
    }

    private void initEventsListeners() {
        binding.btnCreateTask.setOnClickListener(v -> {
            removeErrors();
            setAddTaskInputs();
            AddTaskRequest addTaskRequest = getAddTaskRequest(taskName, taskDeadline);
            requestAddTask(addTaskRequest);
        });
    }

    private void initProgressDialog(){
        progressDialog = new ProgressDialog(CreateActivity.this, R.style.LoadingDialogStyle);
        progressDialog.setTitle("Add a new task");
        progressDialog.setMessage("Please wait a moment");
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

    private void removeErrors(){
        binding.inputTaskName.setError(null);
    }

    private void showError(String error) {
        if (error.contains("Empty")) {
            binding.inputTaskName.setError("Description is required");
        }
        if (error.contains("TooShort")) {
            binding.inputTaskName.setError("Description must have at least 2 characters");
        }
        if (error.contains("Existing")) {
            binding.inputTaskName.setError("This description already exists");
        }
    }

    private void requestAddTask(AddTaskRequest addTaskRequest){
        progressDialog.show();
        service.addTask(addTaskRequest).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()){
                    Log.i("CREATE", "Response is successful");
                    Intent i = new Intent(CreateActivity.this, HomeActivity.class);
                    progressDialog.dismiss();
                    startActivity(i);
                } else {
                    try {
                        progressDialog.dismiss();
                        Log.i("CREATE", "Response is not successful");
                        if (response.code() == 403){
                            Intent i = new Intent(CreateActivity.this, ConnexionActivity.class);
                            startActivity(i);
                            return;
                        } else if (response.code() == 400){
                            showError(response.errorBody().string());
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                progressDialog.dismiss();
                Snackbar.make(binding.createLayout, "Connexion error", Snackbar.LENGTH_LONG).show();
                Log.i("CREATE", "Request failed");
            }
        });
    }
}