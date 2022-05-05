package com.chamberland.kickmyb.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.chamberland.kickmyb.R;
import com.chamberland.kickmyb.databinding.ActivityBaseBinding;
import com.chamberland.kickmyb.http.RetrofitUtil;
import com.chamberland.kickmyb.http.Service;
import com.chamberland.kickmyb.utils.SessionSigninResponse;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import org.kickmyb.transfer.SigninResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class BaseActivity extends AppCompatActivity {
    String currentActivity;
    ActivityBaseBinding bindingBase;
    ActionBarDrawerToggle actionBarDrawerToggle;
    DrawerLayout drawerLayout;
    SigninResponse sessionSigninResponse;
    Service service;
    ProgressDialog progressDialog;

    @Override
    public void setContentView(View view) {
        sessionSigninResponse = SessionSigninResponse.get();
        bindingBase = ActivityBaseBinding.inflate(getLayoutInflater());
        bindingBase.baseFrameLayout.addView (view);
        drawerLayout = bindingBase.drawerLayoutID;
        super.setContentView(drawerLayout);
        service = RetrofitUtil.get();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        createEventsListeners();
        initProgressDialog("Logging out");
    }

    private void initProgressDialog(String title){
        progressDialog = new ProgressDialog(BaseActivity.this, R.style.LoadingDialogStyle);
        progressDialog.setTitle(title);
        progressDialog.setMessage("Please wait a moment");
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
                        }
                        break;
                    case (R.id.navAddTask):
                        if (!currentActivity.equals("Create")) {
                            i = new Intent(BaseActivity.this, CreateActivity.class);
                            startActivity(i);
                        }
                        break;
                    case (R.id.navDisconnect):
                        requestSignout();
                        break;
                }
                return false;
            }
        });

        actionBarDrawerToggle =
                new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close){
                    @Override
                    public void onDrawerOpened(View drawerView) {
                        super.onDrawerOpened(drawerView);
                    }

                    @Override
                    public void onDrawerClosed(View drawerView) {
                        super.onDrawerClosed(drawerView);
                    }

                    @Override
                    public void onDrawerSlide(View drawerView, float slideOffset) {
                        TextView currentUsername = (TextView) findViewById(R.id.currentUser);
                        currentUsername.setText(sessionSigninResponse.username);
                        super.onDrawerSlide(drawerView, slideOffset);
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

    public void requestSignout(){
        progressDialog.show();
        service.signout().enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()){
                    progressDialog.dismiss();
                    Log.i("SIGNOUT", "Response is successful");
                    Intent i = new Intent(BaseActivity.this, ConnexionActivity.class);
                    startActivity(i);
                    finishAffinity();
                }
                else{
                    progressDialog.dismiss();
                    Log.e("SIGNOUT", "Response is not successful");
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                progressDialog.dismiss();
                Log.e("SIGNOUT", "Request failed");
                Snackbar.make(bindingBase.baseFrameLayout, R.string.connexion_failed, Snackbar.LENGTH_LONG).show();
            }
        });
    }
}