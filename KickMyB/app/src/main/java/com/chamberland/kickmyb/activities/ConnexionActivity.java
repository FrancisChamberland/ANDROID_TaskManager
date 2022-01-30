package com.chamberland.kickmyb.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.chamberland.kickmyb.databinding.ActivityConnexionBinding;

public class ConnexionActivity extends BaseActivity {
    private ActivityConnexionBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityConnexionBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        this.setTitle("Connexion");
        createEventsListeners();
    }

    @Override
    void createEventsListeners(){
        binding.btnRegister.setOnClickListener(v -> {
            Intent i = new Intent(ConnexionActivity.this, RegisterActivity.class);
            startActivity(i);
        });
        binding.btnConnect.setOnClickListener(v -> {
            Intent i = new Intent(ConnexionActivity.this, HomeActivity.class);
            startActivity(i);
        });
    }
}