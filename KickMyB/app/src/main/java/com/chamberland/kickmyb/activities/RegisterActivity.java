package com.chamberland.kickmyb.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.chamberland.kickmyb.R;
import com.chamberland.kickmyb.databinding.ActivityRegisterBinding;
import com.chamberland.kickmyb.http.RetrofitUtil;
import com.chamberland.kickmyb.http.Service;
import com.chamberland.kickmyb.utils.SessionSigninResponse;
import com.google.android.material.snackbar.Snackbar;

import org.kickmyb.transfer.SigninResponse;
import org.kickmyb.transfer.SignupRequest;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    private ActivityRegisterBinding binding;
    private Service service;
    private String inputUsername;
    private String inputPassword;
    private String inputConfirmPassword;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        service = RetrofitUtil.get();
        this.setTitle("Inscription");
        initEventsListeners();
        initProgressDialog();
    }

    private void initEventsListeners(){
        binding.btnConnect.setOnClickListener(v -> {
            Intent i = new Intent(RegisterActivity.this, ConnexionActivity.class);
            startActivity(i);
        });
        binding.btnRegister.setOnClickListener(v -> {
            removeErrors();
            setRegisterInputs();
            if (!inputPassword.equals(inputConfirmPassword)){
                binding.inputConfirmPassword.setError("The password and the confirmation password do not match");
                return;
            }
            SignupRequest signupRequest = getSignupResquest(inputUsername, inputPassword);
            requestSignup(signupRequest);
        });
    }

    private void initProgressDialog(){
        progressDialog = new ProgressDialog(RegisterActivity.this, R.style.LoadingDialogStyle);
        progressDialog.setTitle("Register");
        progressDialog.setMessage("Please wait a moment");
    }

    private void setRegisterInputs(){
        inputUsername = String.valueOf(binding.inputUsername.getEditText().getText());
        inputPassword = String.valueOf(binding.inputPassword.getEditText().getText());
        inputConfirmPassword = String.valueOf(binding.inputConfirmPassword.getEditText().getText());
    }

    private void removeErrors(){
        binding.inputUsername.setError(null);
        binding.inputPassword.setError(null);
        binding.inputConfirmPassword.setError(null);
    }

    private SignupRequest getSignupResquest(String username, String password){
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.username = username;
        signupRequest.password = password;
        return signupRequest;
    }

    private void showError(String error){
        if (error.contains("UsernameTooShort")){
            binding.inputUsername.setError("The username must be at least 2 characters");
        }
        if (error.contains("UsernameAlreadyTaken")){
            binding.inputUsername.setError("This username is already taken");
        }
        if (error.contains("PasswordTooShort")){
            binding.inputPassword.setError("The password must be at least 4 characters");
        }
    }

    private void requestSignup(SignupRequest signupRequest){
        progressDialog.show();
        service.signup(signupRequest).enqueue(new Callback<SigninResponse>() {
            @Override
            public void onResponse(Call<SigninResponse> call, Response<SigninResponse> response) {
                if (response.isSuccessful()){
                    Log.i("SIGNUP", "Response is successful");
                    SessionSigninResponse.set(response.body());
                    Intent i = new Intent(RegisterActivity.this, HomeActivity.class);
                    progressDialog.dismiss();
                    startActivity(i);
                    finishAffinity();
                }
                else{
                    try {
                        progressDialog.dismiss();
                        Log.e("SIGNUP", "Response is not successful");
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
                Snackbar.make(binding.registerLayout, "Connexion error", Snackbar.LENGTH_LONG).show();
                Log.e("SIGNUP", "Request failed");
            }
        });
    }
}