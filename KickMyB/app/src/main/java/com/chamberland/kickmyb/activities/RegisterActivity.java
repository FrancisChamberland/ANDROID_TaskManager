package com.chamberland.kickmyb.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.chamberland.kickmyb.databinding.ActivityRegisterBinding;
import com.chamberland.kickmyb.http.RetrofitUtil;
import com.chamberland.kickmyb.http.Service;

import org.kickmyb.transfer.SigninRequest;
import org.kickmyb.transfer.SigninResponse;
import org.kickmyb.transfer.SignupRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    private ActivityRegisterBinding binding;
    private Service service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        service = RetrofitUtil.get();
        this.setTitle("Inscription");
        createEventsListeners();
    }

    private void createEventsListeners(){
        binding.btnConnect.setOnClickListener(v -> {
            Intent i = new Intent(RegisterActivity.this, ConnexionActivity.class);
            startActivity(i);
        });
        binding.btnRegister.setOnClickListener(v -> {
            String username = String.valueOf(binding.inputUsername.getEditText().getText());
            String password = String.valueOf(binding.inputPassword.getEditText().getText());
            String confirmPassword = String.valueOf(binding.inputConfirmPassword.getEditText().getText());

            if (!password.equals(confirmPassword)) return;

            SignupRequest signupRequest = new SignupRequest();
            signupRequest.username = username;
            signupRequest.password = password;

            service.signup(signupRequest).enqueue(new Callback<SigninResponse>() {
                @Override
                public void onResponse(Call<SigninResponse> call, Response<SigninResponse> response) {
                    if (response.isSuccessful()){
                        Log.i("SIGNUP", "Response is successful");
                        Intent i = new Intent(RegisterActivity.this, HomeActivity.class);
                        startActivity(i);
                    }
                    else{
                        Log.e("SIGNUP", "Response is not successful");
                    }
                }

                @Override
                public void onFailure(Call<SigninResponse> call, Throwable t) {
                    Log.e("SIGNUP", "Request failed");
                }
            });
        });
    }
}