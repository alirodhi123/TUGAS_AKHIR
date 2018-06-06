package com.example.alirodhi.broiler;

/**
 * Created by alirodhi on 2/23/2018.
 */

public class LogActivity {

    private String judul;
    private String jam;
    private String deskripsi;
    private int image;

    public LogActivity() {
    }

    public LogActivity(String judul, String jam, String deskripsi, int image) {
        this.judul = judul;
        this.jam = jam;
        this.deskripsi = deskripsi;
        this.image = image;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public void setJam(String jam) {
        this.jam = jam;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getJudul() {
        return judul;
    }

    public String getJam() {
        return jam;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public int getImage() {
        return image;
    }

    public static void e(String s, String status) {
    }
}
