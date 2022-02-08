package com.chamberland.kickmyb.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.chamberland.kickmyb.databinding.ActivityConnexionBinding;
import com.chamberland.kickmyb.databinding.ActivityConsultBinding;

public class ConsultActivity extends BaseActivity {
    private ActivityConsultBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityConsultBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        currentActivity = "Consult";
        this.setTitle("Tâche");
        createEventsListeners();

        binding.traskProgressInput.setMin(0);
        binding.traskProgressInput.setMax(100);
        binding.traskProgressInput.setValue(75);
    }

    private void createEventsListeners() {

    }
}