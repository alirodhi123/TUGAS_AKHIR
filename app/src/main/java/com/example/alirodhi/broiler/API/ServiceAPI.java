package com.example.alirodhi.broiler.API;

import com.example.alirodhi.broiler.Models.RelayModel;
import com.example.alirodhi.broiler.Models.ResponseLogModel;
import com.example.alirodhi.broiler.Models.ResponseSensorModel;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by alirodhi on 6/13/2018.
 */

public interface ServiceAPI {
    @GET("log/get-log")
    Call<ResponseLogModel> getLogLamp();

    @GET("sensor")
    Call<ResponseSensorModel> getDataSensor();

    @GET("relay/get-relay/state")
    Call<RelayModel> getStateRelay();
}
