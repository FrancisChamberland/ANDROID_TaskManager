package com.chamberland.kickmyb.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;

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
    }
}