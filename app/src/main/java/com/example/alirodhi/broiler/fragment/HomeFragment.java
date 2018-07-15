package com.example.alirodhi.broiler.fragment;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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

    private SharedPreferences historyPref;
    private SharedPreferences.Editor historyEdit;

    private List<SensorModel> sensorModels = new ArrayList<>();

    private TextView txtTemp;
    private TextView txtHum;
    private TextView txtCdioksida;
    private TextView txtAmmonia;
    private TextView txtView;
    private Switch swSensor;

    private int currTime;
    private boolean firstEnterRecursive = true;

    private float tempCache = 0.0f;
    private float humCache = 0.0f;
    private float cdioksidaCache = 0.0f;
    private float ammoniaCache = 0.0f;

    private Socket sc;
    {
        try{
            //sc = IO.socket("https://ali.jagopesan.com/");
            sc = IO.socket("http://192.168.43.140:3038/");
        }catch (URISyntaxException e){
            throw new RuntimeException(e);
        }
    }

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

        historyPref = getContext().getSharedPreferences(HISTORY_PREF, Context.MODE_PRIVATE);
        historyEdit = historyPref.edit();

        String currentDateandTime = new SimpleDateFormat("dd - MM - yyyy").format(new Date());
        txtView.setText(currentDateandTime);


        // Connect to socket io
        sc.on("readsensor", getReadserial);
        sc.connect();

        // Database connect
        db = new DatabaseHelper(getContext());

        //serviceIntent = new Intent(getActivity(), NotificationService.class);

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
//
//        updateSensorData();
        getDataSensor();
        getStateRelay();

        return view;
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
                    sensorModels = response.body().getData();
                    SensorModel lastData = sensorModels.get(sensorModels.size() - 1);

                    //int tempInt = Integer.parseInt(lastData.getTemp().toString());

                    String temp = String.format("%.1f", lastData.getTemp());
                    String hum = String.format("%.1f", lastData.getHum());
                    String dioksida = String.format("%.1f", lastData.getCdioksida());
                    String ammonia = String.format("%.3f", lastData.getAmonia());

                    // Show your value to app in string mode
                    txtTemp.setText(temp);
                    txtHum.setText(hum);
                    txtCdioksida.setText(dioksida);
                    txtAmmonia.setText(ammonia);

                    float tempVal = Float.parseFloat(temp);
                    float humVal = Float.parseFloat(hum);
                    float cdioksidaVal = Float.parseFloat(dioksida);
                    float ammoniaVal = Float.parseFloat(ammonia);

                    float lastTemp = historyPref.getFloat(LAST_TEMP, 0);
                    float lastHum = historyPref.getFloat(LAST_HUM, 0);
                    float lastCdioksida = historyPref.getFloat(LAST_CO2, 0);
                    float lastAmmonia = historyPref.getFloat(LAST_NH3, 0);

                    // Your condition, what you want save in database SQL
                    if ( (tempVal < 27 || tempVal > 32) && (humVal < 60 || humVal > 70) )
                    {
                        // Create Notification
                        mgr = (NotificationManager)getContext().getSystemService(getContext().NOTIFICATION_SERVICE);
                        Notification notification = new NotificationCompat.Builder(getContext(), "exampleServiceChannel")
                                .setContentTitle("Sensor Gases")
                                .setContentText("WARNING! Please check the air condition")
                                .setSmallIcon(R.mipmap.ic_launcher)
                                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                                .setAutoCancel(true)
                                .setDefaults(Notification.DEFAULT_ALL)
                                .build();

                        mgr.notify(1, notification);

                    } else {
                        if ( (tempVal != lastTemp) ||
                                (humVal != lastHum) ||
                                (cdioksidaVal != lastCdioksida) ||
                                (ammoniaVal != lastAmmonia) )
                        {
                            // Data push in database
                            historyEdit.putFloat(LAST_TEMP, tempVal);
                            historyEdit.putFloat(LAST_HUM, humVal);
                            historyEdit.putFloat(LAST_CO2, cdioksidaVal);
                            historyEdit.putFloat(LAST_NH3, ammoniaVal);
                            historyEdit.apply();

                            db.insertHistory(temp, hum, dioksida, ammonia);
                        }
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
                    sleep(20000);
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


    /**
     * RETROFIT
     * Get state relay sensor
     * Output: call last relay sensor true or false
     */
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



                if(response.body().getSensor()){
                    relaySensorTrue();
                }else{
                    relaySensorFalse();
                }
            }

            @Override
            public void onFailure(Call<RelayModel> call, Throwable t) {

            }
        });
    }

    private void relaySensorTrue() {
    }
    private void relaySensorFalse() {
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

}