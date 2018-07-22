package com.example.alirodhi.broiler;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.IdRes;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.example.alirodhi.broiler.API.ServiceAPI;
import com.example.alirodhi.broiler.Models.RelayModel;
import com.example.alirodhi.broiler.Models.ResponseSensorModel;
import com.example.alirodhi.broiler.Models.SensorModel;
import com.example.alirodhi.broiler.db.DatabaseHelper;
import com.example.alirodhi.broiler.fragment.HomeFragment;
import com.example.alirodhi.broiler.fragment.LogFragment;
import com.example.alirodhi.broiler.fragment.LogRelayFragment;
import com.example.alirodhi.broiler.fragment.RemoteFragment;
import com.example.alirodhi.broiler.service.NotificationService;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {

    public static final String HISTORY_PREF = "history-pref";
    public static final String LAST_TEMP = "last-temp";
    public static final String LAST_HUM = "last-hum";
    public static final String LAST_CO2 = "last-cdioksida";
    public static final String LAST_NH3 = "last-ammonia";

    //public static final String URL = "https://ali.jagopesan.com/";
    public static final String URL = "http://192.168.43.140:3038/";

    private ResponseSensorModel sensorModels = new ResponseSensorModel();

    private Context context;
    private Intent serviceIntent;

    public DatabaseHelper db;

    private NotificationManager mgr;
    private Notification notification;

    private SharedPreferences historyPref;
    private SharedPreferences.Editor historyEdit;

    private TextView txtTemp;
    private TextView txtHum;
    private TextView txtCdioksida;
    private TextView txtAmmonia;
    private TextView txtView;
    private Switch swSensor;

    private int currTime;
    private boolean firstEnterRecursive = true;

    private BottomBar bottomBar;
    private Fragment fragment = null;

    private TabLayout.Tab home;
    private TabLayout.Tab action;
    private TabLayout.Tab log;

    private Socket sc;
    {
        try{
            //sc = IO.socket("https://ali.jagopesan.com/");
            sc = IO.socket("http://192.168.43.140:3038/");
        }catch (URISyntaxException e){
            throw new RuntimeException(e);
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtTemp = (TextView)findViewById(R.id.txtTemp);
        txtHum = (TextView)findViewById(R.id.txtHum);
        txtCdioksida = (TextView)findViewById(R.id.txtCdioksida);
        txtAmmonia = (TextView)findViewById(R.id.txtAmmonia);
        txtView = (TextView)findViewById(R.id.tanggal);
        swSensor = (Switch)findViewById(R.id.switchBtnSensor);

        historyPref = getSharedPreferences(HISTORY_PREF, Context.MODE_PRIVATE);
        historyEdit = historyPref.edit();

        //Connect to socket server
        //sc.on("readsensor", getReadserial);
        sc.connect();

        //Database connect
        db = new DatabaseHelper(this);

        serviceIntent = new Intent(this, NotificationService.class);

        showFrament();

        bottomBar = (BottomBar)findViewById(R.id.bottombar);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(int tabId) {
                if(tabId == R.id.tab_home){
                    fragment = new HomeFragment();
                } else if(tabId == R.id.tab_remote){
                    fragment = new RemoteFragment();
                } else if(tabId == R.id.tab_log){
                    fragment = new LogFragment();
                }

                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.MyPage, fragment);
                fragmentTransaction.commit();
            }
        });

        bottomBar.setOnTabReselectListener(new OnTabReselectListener() {
            @Override
            public void onTabReSelected(@IdRes int tabId) {
                if(tabId == R.id.tab_home){
                    fragment = new HomeFragment();
                } else if(tabId == R.id.tab_remote){
                    fragment = new RemoteFragment();
                } else if(tabId == R.id.tab_log){
                    fragment = new LogFragment();
                }

                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.MyPage, fragment);
                fragmentTransaction.commit();
            }
        });

        getDataSensor();
    }

    /**
     * RETROFIT
     * Function to get all data sensor from waspmote
     * Use UI THREAD for looping
     */
    private void loadDataCoy () {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final ServiceAPI serviceAPI = retrofit.create(ServiceAPI.class);
        Call<ResponseSensorModel> call = serviceAPI.getDataSensor();
        call.enqueue(new Callback<ResponseSensorModel>() {
            @Override
            public void onResponse(Call<ResponseSensorModel> call, Response<ResponseSensorModel> response) {
                String value = response.body().getStatus();
                if (value.equals("success")){
//                    sensorModels = new ResponseSensorModel();
                    sensorModels = response.body();
                    SensorModel lastData = sensorModels.getData().get(sensorModels.getData().size() - 1);

                    String temp = String.format("%.1f", lastData.getTemp());
                    String hum = String.format("%.1f", lastData.getHum());
                    String dioksida = String.format("%.1f", lastData.getCdioksida());
                    String ammonia = String.format("%.3f", lastData.getAmonia());

                    float tempVal = Float.parseFloat(temp);
                    float humVal = Float.parseFloat(hum);
                    float cdioksidaVal = Float.parseFloat(dioksida);
                    float ammoniaVal = Float.parseFloat(ammonia);

                    float lastTemp = historyPref.getFloat(LAST_TEMP, 0);
                    float lastHum = historyPref.getFloat(LAST_HUM, 0);
                    float lastCdioksida = historyPref.getFloat(LAST_CO2, 0);
                    float lastAmmonia = historyPref.getFloat(LAST_NH3, 0);

                    // Data push in database
                    historyEdit.putFloat(LAST_TEMP, tempVal);
                    historyEdit.putFloat(LAST_HUM, humVal);
                    historyEdit.putFloat(LAST_CO2, cdioksidaVal);
                    historyEdit.putFloat(LAST_NH3, ammoniaVal);
                    historyEdit.apply();

                    if ((tempVal >= 32 && tempVal <= 34) && (humVal >= 50 && humVal <= 70)){
                        //Kondisi normal
                        Log.i("data", "kondisi normal");

                    } else if ((tempVal != lastTemp) ||
                            (humVal != lastHum) ||
                            (cdioksidaVal != lastCdioksida) ||
                            (ammoniaVal != lastAmmonia))
                    {
                        db.insertHistory(temp, hum, dioksida, ammonia);
                    } else {
                        Log.i("data", "data dari waspmote tidak di push");
                    }

                    if ((tempVal < 30 || tempVal > 34) && (humVal < 50 || humVal > 70)){
                        // Create Notification
                        startService(serviceIntent);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseSensorModel> call, Throwable t) {

            }
        });
    }

    /**
     * UI THREAD
     * Get data sensor with delay 6 second
     * Output: Looping every 6 s
     */
    private void getDataSensor(){

        final Thread thread = new Thread(){
            public void run(){
                try{
                    sleep(5000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }finally {
                    getDataSensor();
                }
            }
        };
        thread.start();

        if (thread.getId() % 2 == 0) {
            loadDataCoy();
        }
    }


    private void showFrament(){
        HomeFragment fragment = new HomeFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.MyPage, fragment);
        fragmentTransaction.commit();
    }
}
