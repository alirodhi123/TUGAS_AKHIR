package com.example.alirodhi.broiler.Models;

/**
 * Created by alirodhi on 7/7/2018.
 */

public class HistorySensorModel {
    public static final String TABLE_NAME = "notes";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TEMP = "temp";
    public static final String COLUMN_HUM = "hum";
    public static final String COLUMN_CDIOKSIDA = "cdioksida";
    public static final String COLUMN_AMMONIA = "ammonia";
    public static final String COLUMN_LATITUDE = "latitude";
    public static final String COLUMN_LONGITUDE = "longitude";
    public static final String COLUMN_TIMESTAMP = "timestamp";

    private int id;
    private String temp;
    private String hum;
    private String cdioksida;
    private String ammonia;
    private String latitude;
    private String longitude;
    private String timestamp;

    // Membuat table SQL
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_TEMP + " TEXT,"
                + COLUMN_HUM + " TEXT,"
                + COLUMN_CDIOKSIDA + " TEXT,"
                + COLUMN_AMMONIA + " TEXT,"
                + COLUMN_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP"
                + ")";

    public HistorySensorModel(){}

    public HistorySensorModel(int id, String temp, String hum, String cdioksida, String ammonia, String timestamp) {
        this.id = id;
        this.temp = temp;
        this.hum = hum;
        this.cdioksida = cdioksida;
        this.ammonia = ammonia;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getHum() {
        return hum;
    }

    public void setHum(String hum) {
        this.hum = hum;
    }

    public String getCdioksida() {
        return cdioksida;
    }

    public void setCdioksida(String cdioksida) {
        this.cdioksida = cdioksida;
    }

    public String getAmmonia() {
        return ammonia;
    }

    public void setAmmonia(String ammonia) {
        this.ammonia = ammonia;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
