package com.chamberland.kickmyb.utils;

import android.util.Log;

import org.kickmyb.transfer.SigninResponse;

public class SessionSigninResponse {
    private static SigninResponse instance;

    public static SigninResponse get(){
        return instance;
    }

    public static void set(SigninResponse signinResponse){
        instance = signinResponse;
    }
}
