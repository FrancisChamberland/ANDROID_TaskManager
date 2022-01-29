package com.chamberland.kickmyb;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.chamberland.kickmyb.databinding.ActivityHomeBinding;

public abstract class BaseActivity extends AppCompatActivity {

    abstract void createEventsListeners();
}