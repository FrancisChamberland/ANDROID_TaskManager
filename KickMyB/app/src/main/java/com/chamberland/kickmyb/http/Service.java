package com.chamberland.kickmyb.http;

import com.chamberland.kickmyb.transfer.Task;

import org.kickmyb.transfer.AddTaskRequest;
import org.kickmyb.transfer.HomeItemResponse;
import org.kickmyb.transfer.SigninRequest;
import org.kickmyb.transfer.SigninResponse;
import org.kickmyb.transfer.SignupRequest;
import org.kickmyb.transfer.TaskDetailResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface Service {
    @POST("/api/id/signup")
    Call<SigninResponse> signup(@Body SignupRequest signupRequest);

    @POST("/api/id/signin")
    Call<SigninResponse> signin(@Body SigninRequest signinRequest);

    @POST("/api/id/signout")
    Call<String> signout();

    @GET("/api/home")
    Call<List<Task>> getTasks();

    @POST("/api/add")
    Call<String> addTask(@Body AddTaskRequest addTaskRequest);

    @GET("/api/detail/{id}")
    Call<TaskDetailResponse> detail(@Path("id") long id);

    @GET("/api/progress/{taskID}/{value}")
    Call<String> updateProgress(@Path("taskID") long taskID, @Path("value") int value);
}
