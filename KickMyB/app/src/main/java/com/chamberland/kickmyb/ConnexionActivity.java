package com.chamberland.kickmyb;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.chamberland.kickmyb.databinding.ActivityConnexionBinding;

public class ConnexionActivity extends AppCompatActivity {
    private ActivityConnexionBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityConnexionBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        this.setTitle("Connexion");
    }
}