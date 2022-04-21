package com.chamberland.kickmyb.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.chamberland.kickmyb.R;
import com.chamberland.kickmyb.databinding.ActivityConnexionBinding;
import com.chamberland.kickmyb.http.RetrofitUtil;
import com.chamberland.kickmyb.http.Service;
import com.chamberland.kickmyb.utils.SessionSigninResponse;
import com.google.android.material.snackbar.Snackbar;

import org.kickmyb.transfer.SigninRequest;
import org.kickmyb.transfer.SigninResponse;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConnexionActivity extends AppCompatActivity {
    private ActivityConnexionBinding binding;
    private Service service;
    private String inputUsername;
    private String inputPassword;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityConnexionBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        service = RetrofitUtil.get();
        this.setTitle("Connexion");
        initEventsListeners();
        initProgressDialog();
    }

    private void initEventsListeners(){
        binding.btnRegister.setOnClickListener(v -> {
            Intent i = new Intent(ConnexionActivity.this, RegisterActivity.class);
            startActivity(i);
        });
        binding.btnConnect.setOnClickListener(v -> {
            removeErrors();
            setRegisterInputs();
            SigninRequest signinRequest = getSigninResquest(inputUsername, inputPassword);
            requestSignin(signinRequest);
        });
    }

    private void initProgressDialog(){
        progressDialog = new ProgressDialog(ConnexionActivity.this, R.style.LoadingDialogStyle);
        progressDialog.setTitle("Connexion");
        progressDialog.setMessage("Please wait a moment");
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

    private void showError(String error) {
        if (error.contains("BadCredentialsException")) {
            binding.inputPassword.setError("This password is incorrect");
        }
        if (error.contains("InternalAuthenticationServiceException")) {
            binding.inputUsername.setError("This username does not exist");
        }
        if (error.contains("UsernameRequired")) {
            binding.inputUsername.setError("Username is required");
        }
        if (error.contains("PasswordRequired")) {
            binding.inputPassword.setError("Password is required");
        }
    }

    private void removeErrors(){
        binding.inputUsername.setError(null);
        binding.inputPassword.setError(null);
    }

    private void requestSignin(SigninRequest signinRequest){
        progressDialog.show();
        service.signin(signinRequest).enqueue(new Callback<SigninResponse>() {
            @Override
            public void onResponse(Call<SigninResponse> call, Response<SigninResponse> response) {
                if (response.isSuccessful()){
                    Log.i("SIGNIN", "Response is successful");
                    SessionSigninResponse.set(response.body());
                    Intent i = new Intent(ConnexionActivity.this, HomeActivity.class);
                    progressDialog.dismiss();
                    startActivity(i);
                    finishAffinity();
                }
                else{
                    try {
                        progressDialog.dismiss();
                        Log.e("SIGNIN", "Response is not successful");
                        if (response.code() == 400){
                            showError(response.errorBody().string());
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onFailure(Call<SigninResponse> call, Throwable t) {
                progressDialog.dismiss();
                Snackbar.make(binding.connextionLayout, "Connexion error", Snackbar.LENGTH_LONG).show();
                Log.e("SIGNIN", "Request failed");}
        });
    }
}