package com.example.alirodhi.broiler.API;

import com.example.alirodhi.broiler.Models.ResponseModel;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by alirodhi on 6/13/2018.
 */

public interface ServiceAPI {
    @GET("log/get-log")
    Call<ResponseModel> getLogLamp();

    @GET("sensor")
    Call<ResponseModel> getSensor();
}
