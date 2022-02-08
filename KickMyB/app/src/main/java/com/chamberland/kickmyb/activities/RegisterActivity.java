package com.chamberland.kickmyb.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.chamberland.kickmyb.databinding.ActivityRegisterBinding;

public class RegisterActivity extends AppCompatActivity {
    private ActivityRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        this.setTitle("Inscription");
        createEventsListeners();
    }

    private void createEventsListeners(){
        binding.btnConnect.setOnClickListener(v -> {
            Intent i = new Intent(RegisterActivity.this, ConnexionActivity.class);
            startActivity(i);
        });
        binding.btnRegister.setOnClickListener(v -> {
            Intent i = new Intent(RegisterActivity.this, HomeActivity.class);
            startActivity(i);
        });
    }
}