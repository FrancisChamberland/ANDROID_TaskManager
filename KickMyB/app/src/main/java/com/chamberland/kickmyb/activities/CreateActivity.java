package com.chamberland.kickmyb.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.chamberland.kickmyb.R;
import com.chamberland.kickmyb.databinding.ActivityConnexionBinding;
import com.chamberland.kickmyb.databinding.ActivityCreateBinding;

public class CreateActivity extends BaseActivity {
    private ActivityCreateBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        this.setTitle("Ajouter");
        createEventsListeners();
    }

    @Override
    void createEventsListeners() {
        binding.btnCreateTask.setOnClickListener(v -> {
            Intent i = new Intent(CreateActivity.this, HomeActivity.class);
            startActivity(i);
        });
    }
}