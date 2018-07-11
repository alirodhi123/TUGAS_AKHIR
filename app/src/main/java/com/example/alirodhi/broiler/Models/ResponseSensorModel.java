package com.example.alirodhi.broiler.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by alirodhi on 6/29/2018.
 */

public class ResponseSensorModel {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<SensorModel> data = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<SensorModel> getData() {
        return data;
    }

    public void setData(List<SensorModel> data) {
        this.data = data;
    }
}
