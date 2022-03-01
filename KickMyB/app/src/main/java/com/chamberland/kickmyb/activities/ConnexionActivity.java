package com.chamberland.kickmyb.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.chamberland.kickmyb.databinding.ActivityConnexionBinding;
import com.chamberland.kickmyb.http.RetrofitUtil;
import com.chamberland.kickmyb.http.Service;
import com.chamberland.kickmyb.utils.SessionSigninResponse;

import org.kickmyb.transfer.SigninRequest;
import org.kickmyb.transfer.SigninResponse;
import org.kickmyb.transfer.SignupRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConnexionActivity extends AppCompatActivity {
    private ActivityConnexionBinding binding;
    private Service service;
    private String inputUsername;
    private String inputPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityConnexionBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        service = RetrofitUtil.get();
        this.setTitle("Connexion");
        createEventsListeners();
    }

    private void setRegisterInputs(){
        inputUsername = String.valueOf(binding.inputUsername.getEditText().getText());
        inputPassword = String.valueOf(binding.inputPassword.getEditText().getText());
    }

    private SigninRequest getSigninResquest(String username, String password){
        SigninRequest signinRequest = new SigninRequest();
        signinRequest.username = username;
        signinRequest.password = password;
        return signinRequest;
    }

    private void sendSigninRequest(SigninRequest signinRequest){
        service.signin(signinRequest).enqueue(new Callback<SigninResponse>() {
            @Override
            public void onResponse(Call<SigninResponse> call, Response<SigninResponse> response) {
                if (response.isSuccessful()){
                    Log.i("SIGNIN", "Response is successful");
                    SessionSigninResponse.set(response.body());
                    Intent i = new Intent(ConnexionActivity.this, HomeActivity.class);
                    startActivity(i);
                }
                else{
                    Log.e("SIGNIN", "Response is not successful");
                }
            }
            @Override
            public void onFailure(Call<SigninResponse> call, Throwable t) {
                Log.e("SIGNIN", "Request failed");
            }
        });
    }

    private void createEventsListeners(){
        binding.btnRegister.setOnClickListener(v -> {
            Intent i = new Intent(ConnexionActivity.this, RegisterActivity.class);
            startActivity(i);
        });
        binding.btnConnect.setOnClickListener(v -> {
            setRegisterInputs();
            SigninRequest signinRequest = getSigninResquest(inputUsername, inputPassword);
            sendSigninRequest(signinRequest);
        });
    }
}