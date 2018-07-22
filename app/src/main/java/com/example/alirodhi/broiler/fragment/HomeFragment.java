package com.example.alirodhi.broiler.fragment;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.example.alirodhi.broiler.API.ServiceAPI;
import com.example.alirodhi.broiler.MainActivity;
import com.example.alirodhi.broiler.Models.RelayModel;
import com.example.alirodhi.broiler.Models.ResponseSensorModel;
import com.example.alirodhi.broiler.Models.SensorModel;
import com.example.alirodhi.broiler.service.NotificationService;
import com.example.alirodhi.broiler.R;
//import com.example.alirodhi.broiler.db.DatabaseHelper;
import com.example.alirodhi.broiler.db.DatabaseHelper;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

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


public class HomeFragment extends Fragment {
    public static final String HISTORY_PREF = "history-pref";
    public static final String LAST_TEMP = "last-temp";
    public static final String LAST_HUM = "last-hum";
    public static final String LAST_CO2 = "last-cdioksida";
    public static final String LAST_NH3 = "last-ammonia";

    //public static final String URL = "https://ali.jagopesan.com/";
    public static final String URL = "http://192.168.43.140:3038/";

    private Context context;
    private Intent notificationIntent;

    private DatabaseHelper db;

    private NotificationManager mgr;
    private Notification notification;

    private SharedPreferences historyPref;
    private SharedPreferences.Editor historyEdit;

    private List<SensorModel> sensorModels = new ArrayList<>();

    private TextView txtTemp;
    private TextView txtHum;
    private TextView txtCdioksida;
    private TextView txtAmmonia;
    private TextView txtView;
    private Switch swSensor;
    private Switch swOtomatis;

    private int currTime;
    private boolean firstEnterRecursive = true;


    private Socket sc;
    {
        try{
            //sc = IO.socket("https://ali.jagopesan.com/");
            sc = IO.socket("http://192.168.43.140:3038/");
        }catch (URISyntaxException e){
            throw new RuntimeException(e);
        }
    }

    private Handler handler = new Handler();
    private final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            getSensorData();
        }
    };

    public HomeFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        txtTemp = (TextView)view.findViewById(R.id.txtTemp);
        txtHum = (TextView)view.findViewById(R.id.txtHum);
        txtCdioksida = (TextView)view.findViewById(R.id.txtCdioksida);
        txtAmmonia = (TextView)view.findViewById(R.id.txtAmmonia);
        txtView = (TextView)view.findViewById(R.id.tanggal);
        swSensor = (Switch)view.findViewById(R.id.switchBtnSensor);
        swOtomatis = (Switch)view.findViewById(R.id.switchBtnOtomatis);

        historyPref = getContext().getSharedPreferences(HISTORY_PREF, Context.MODE_PRIVATE);
        historyEdit = historyPref.edit();

        String currentDateandTime = new SimpleDateFormat("dd - MM - yyyy").format(new Date());
        txtView.setText(currentDateandTime);


        // Connect to socket io
        sc.on("readsensor", getReadserial);
        sc.on("otomatis", getOtomatis);
        sc.connect();

        /**
         * SOCKET IO
         * Function to execute toggle sensor
         */
        swSensor = (Switch) view.findViewById(R.id.switchBtnSensor);
        swSensor.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                JSONObject sensor = new JSONObject();
                if (isChecked){
                    try{
                        sensor.put("status", true);
                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                    //toggleButtonLamp.setText("ON");
                    //Toast.makeText(getActivity(), "Toggle button spray is on", Toast.LENGTH_LONG).show();
                } else {
                    try{
                        sensor.put("status", false);
                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                    //toggleButtonLamp.setText("OFF");
                    //Toast.makeText(getActivity(), "Toggle button spray is off", Toast.LENGTH_LONG).show();
                }
                sc.emit("readsensor", sensor);
            }
        });

        /**
         * SOCKET IO
         * Function to execute toggle automation
         */
        swOtomatis = (Switch) view.findViewById(R.id.switchBtnOtomatis);
        swOtomatis.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                JSONObject sensor = new JSONObject();
                if (isChecked){
                    try{
                        sensor.put("status", true);
                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                    //toggleButtonLamp.setText("ON");
                    //Toast.makeText(getActivity(), "Toggle button spray is on", Toast.LENGTH_LONG).show();
                } else {
                    try{
                        sensor.put("status", false);
                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                    //toggleButtonLamp.setText("OFF");
                    //Toast.makeText(getActivity(), "Toggle button spray is off", Toast.LENGTH_LONG).show();
                }
                sc.emit("otomatis", sensor);
            }
        });
//
//        updateSensorData();
//        getDataSensor();
        updateSensorData();
        getStateRelay();

        return view;
    }

    private void displaySensorData(){
        txtTemp.setText(String.valueOf(historyPref.getFloat(LAST_TEMP, 0)));
        txtHum.setText(String.valueOf(historyPref.getFloat(LAST_HUM, 0)));
        txtCdioksida.setText(String.valueOf(historyPref.getFloat(LAST_CO2, 0)));
        txtAmmonia.setText(String.valueOf(historyPref.getFloat(LAST_NH3, 0)));
    }

    private void getSensorData() {
        txtTemp.setText(String.valueOf(historyPref.getFloat(LAST_TEMP, 0)));
        txtHum.setText(String.valueOf(historyPref.getFloat(LAST_HUM, 0)));
        txtCdioksida.setText(String.valueOf(historyPref.getFloat(LAST_CO2, 0)));
        txtAmmonia.setText(String.valueOf(historyPref.getFloat(LAST_NH3, 0)));

        handler.postDelayed(runnable, 5000);
    }

    private void updateSensorData() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                getSensorData();
            }
        });
    }

//    /**
//     * UI THREAD
//     * Get data sensor with delay 6 second
//     * Output: Looping every 6 s
//     */
//    private void getDataSensor(){
//
//        final Thread thread = new Thread(){
//            public void run(){
//                try{
//                    sleep(20000);
//                }catch (InterruptedException e){
//                    e.printStackTrace();
//                }finally {
//                    getDataSensor();
//                }
//            }
//        };
//        thread.start();
//
//        if (thread.getId() % 2 == 0) {
//            displaySensorData();
//        }
//    }

//    /**
//     * RETROFIT
//     * Get state relay sensor
//     * Output: call last relay sensor true or false
//     */
    private void getStateRelay(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ServiceAPI serviceAPI = retrofit.create(ServiceAPI.class);
        final Call<RelayModel> relayModel  = serviceAPI.getStateRelay();
        relayModel.enqueue(new Callback<RelayModel>() {
            @Override
            public void onResponse(Call<RelayModel> call, Response<RelayModel> response) {
                swSensor.setChecked(response.body().getSensor());
                swOtomatis.setChecked(response.body().getOtomatis());
            }

            @Override
            public void onFailure(Call<RelayModel> call, Throwable t) {

            }
        });
    }


    private Emitter.Listener getReadserial = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            if(getActivity()!=null){
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        JSONObject data = (JSONObject) args[0];
                        String status;
                        try{
                            status = data.getString("status");
                        }catch (JSONException e){
                            return;
                        }
                        Log.e("Status sensor: ", status);
                        swSensor = (Switch) getActivity().findViewById(R.id.switchBtnSensor);
                        if (status == "true"){
                            swSensor.setChecked(true);
                        }
                        else{
                            swSensor.setChecked(false);
                        }
                    }
                });
            }

        }
    };

    private Emitter.Listener getOtomatis = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            if(getActivity()!=null){
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        JSONObject data = (JSONObject) args[0];
                        String status;
                        try{
                            status = data.getString("status");
                        }catch (JSONException e){
                            return;
                        }
                        Log.e("Status otomatis: ", status);
                        swOtomatis = (Switch) getActivity().findViewById(R.id.switchBtnOtomatis);
                        if (status == "true"){
                            swOtomatis.setChecked(true);
                        }
                        else{
                            swOtomatis.setChecked(false);
                        }
                    }
                });
            }

        }
    };
}