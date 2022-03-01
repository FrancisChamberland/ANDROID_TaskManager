package com.chamberland.kickmyb.http;

import org.kickmyb.transfer.SigninRequest;
import org.kickmyb.transfer.SigninResponse;
import org.kickmyb.transfer.SignupRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface Service {
    @POST("/api/id/signup")
    Call<SigninResponse> signup(@Body SignupRequest signupRequest);

    @POST("/api/id/signin")
    Call<SigninResponse> signin(@Body SigninRequest signinRequest);
}
