package com.example.alirodhi.broiler.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by alirodhi on 6/30/2018.
 */

public class RelayModel {
    private Boolean lamp;
    private Boolean fan;
    private Boolean spray;
    private Boolean exhaust;
    private Boolean sensor;
    private Boolean otomatis;

    public Boolean getLamp() {
        return lamp;
    }

    public void setLamp(Boolean lamp) {
        this.lamp = lamp;
    }

    public Boolean getFan() {
        return fan;
    }

    public void setFan(Boolean fan) {
        this.fan = fan;
    }

    public Boolean getSpray() {
        return spray;
    }

    public void setSpray(Boolean spray) {
        this.spray = spray;
    }

    public Boolean getExhaust() {
        return exhaust;
    }

    public void setExhaust(Boolean exhaust) {
        this.exhaust = exhaust;
    }

    public Boolean getSensor() {return sensor;}

    public void setSensor(Boolean sensor) {
        this.sensor = sensor;
    }

    public Boolean getOtomatis() {return otomatis;}

    public void setOtomatis(Boolean otomatis) {this.otomatis = otomatis;}
}
