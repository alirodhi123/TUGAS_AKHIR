package com.example.alirodhi.broiler.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by alirodhi on 6/14/2018.
 */

public class SensorModel {
    @SerializedName("tanngal")
    @Expose
    private String tanngal;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("temp")
    @Expose
    private Double temp;
    @SerializedName("hum")
    @Expose
    private Double hum;
    @SerializedName("cdioksida")
    @Expose
    private Double cdioksida;
    @SerializedName("amonia")
    @Expose
    private Double amonia;
    @SerializedName("bat")
    @Expose
    private Integer bat;

    public String getTanngal() {
        return tanngal;
    }

    public void setTanngal(String tanngal) {
        this.tanngal = tanngal;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getTemp() {
        return temp;
    }

    public void setTemp(Double temp) {
        this.temp = temp;
    }

    public Double getHum() {
        return hum;
    }

    public void setHum(Double hum) {
        this.hum = hum;
    }

    public Double getCdioksida() {
        return cdioksida;
    }

    public void setCdioksida(Double cdioksida) {
        this.cdioksida = cdioksida;
    }

    public Double getAmonia() {
        return amonia;
    }

    public void setAmonia(Double amonia) {
        this.amonia = amonia;
    }

    public Integer getBat() {
        return bat;
    }

    public void setBat(Integer bat) {
        this.bat = bat;
    }
}
