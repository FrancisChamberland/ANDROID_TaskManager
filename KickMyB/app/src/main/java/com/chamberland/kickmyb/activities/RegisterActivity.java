package com.chamberland.kickmyb.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.chamberland.kickmyb.databinding.ActivityRegisterBinding;
import com.chamberland.kickmyb.http.RetrofitUtil;
import com.chamberland.kickmyb.http.Service;
import com.chamberland.kickmyb.utils.SessionSigninResponse;

import org.kickmyb.transfer.SigninRequest;
import org.kickmyb.transfer.SigninResponse;
import org.kickmyb.transfer.SignupRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    private ActivityRegisterBinding binding;
    private Service service;
    private String inputUsername;
    private String inputPassword;
    private String inputConfirmPassword;

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

    private void setRegisterInputs(){
        inputUsername = String.valueOf(binding.inputUsername.getEditText().getText());
        inputPassword = String.valueOf(binding.inputPassword.getEditText().getText());
        inputConfirmPassword = String.valueOf(binding.inputConfirmPassword.getEditText().getText());
    }

    private boolean inputsAreValid(){
        return (inputPassword.equals(inputConfirmPassword));
    }

    private SignupRequest getSignupResquest(String username, String password){
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.username = username;
        signupRequest.password = password;
        return signupRequest;
    }

    private void sendSignupRequest(SignupRequest signupRequest){
        service.signup(signupRequest).enqueue(new Callback<SigninResponse>() {
            @Override
            public void onResponse(Call<SigninResponse> call, Response<SigninResponse> response) {
                if (response.isSuccessful()){
                    Log.i("SIGNUP", "Response is successful");
                    SessionSigninResponse.set(response.body());
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
    }

    private void createEventsListeners(){
        binding.btnConnect.setOnClickListener(v -> {
            Intent i = new Intent(RegisterActivity.this, ConnexionActivity.class);
            startActivity(i);
        });
        binding.btnRegister.setOnClickListener(v -> {
            setRegisterInputs();
            if (!inputsAreValid()) return;
            SignupRequest signupRequest = getSignupResquest(inputUsername, inputPassword);
            sendSignupRequest(signupRequest);
        });
    }
}