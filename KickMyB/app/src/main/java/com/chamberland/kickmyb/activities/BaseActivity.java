package com.chamberland.kickmyb.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.chamberland.kickmyb.R;
import com.chamberland.kickmyb.databinding.ActivityBaseBinding;
import com.chamberland.kickmyb.utils.SessionSigninResponse;
import com.google.android.material.navigation.NavigationView;

import org.kickmyb.transfer.SigninResponse;

public abstract class BaseActivity extends AppCompatActivity {
    String currentActivity;
    ActivityBaseBinding bindingBase;
    ActionBarDrawerToggle actionBarDrawerToggle;
    DrawerLayout drawerLayout;
    SigninResponse sessionSigninResponse;

    @Override
    public void setContentView(View view) {
        bindingBase = ActivityBaseBinding.inflate(getLayoutInflater());
        bindingBase.baseFrameLayout.addView (view);
        drawerLayout = bindingBase.drawerLayoutID;
        sessionSigninResponse = SessionSigninResponse.get();
        super.setContentView(drawerLayout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        createEventsListeners();
    }

    private void createEventsListeners() {
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

        actionBarDrawerToggle =
                new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close){
                    @Override
                    public void onDrawerOpened(View drawerView) {
                        TextView currentUsername = (TextView) findViewById(R.id.currentUser);
                        currentUsername.setText(sessionSigninResponse.username);
                        super.onDrawerOpened(drawerView);
                    }

                    @Override
                    public void onDrawerClosed(View drawerView) {
                        super.onDrawerClosed(drawerView);
                    }
                };

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
        super.onConfigurationChanged(newConfig);
    }
}