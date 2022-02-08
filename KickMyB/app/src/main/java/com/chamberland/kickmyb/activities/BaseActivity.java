package com.chamberland.kickmyb.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.chamberland.kickmyb.R;
import com.chamberland.kickmyb.databinding.ActivityBaseBinding;
import com.chamberland.kickmyb.databinding.ActivityHomeBinding;
import com.google.android.material.navigation.NavigationView;

public abstract class BaseActivity extends AppCompatActivity {

    String currentActivity; // Évite la double ouverture d'une activité
    ActivityBaseBinding bindingBase;
    @Override
    public void setContentView(View view) {
        bindingBase = ActivityBaseBinding.inflate(getLayoutInflater());
        bindingBase.baseFrameLayout.addView (view);
        super.setContentView(bindingBase.drawerLayoutID);
        bindingBase.navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent i;
                switch (item.getItemId()){
                    case (R.id.navHome):
                        if (!currentActivity.equals("Home")){
                            i = new Intent(BaseActivity.this, HomeActivity.class);
                            startActivity(i);
                            new Intent(BaseActivity.this, ConnexionActivity.class);
                        }
                        break;
                    case (R.id.navAddTask):
                        if (!currentActivity.equals("Create")) {
                            i = new Intent(BaseActivity.this, CreateActivity.class);
                            startActivity(i);
                        }
                        break;
                    case (R.id.navDisconnect):
                        i = new Intent(BaseActivity.this, ConnexionActivity.class);
                        startActivity(i);
                        break;
                }
                return false;
            }
        });
    }
}