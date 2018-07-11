package com.example.alirodhi.broiler.fragment;



import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.ToggleButton;

import com.example.alirodhi.broiler.API.ServiceAPI;
import com.example.alirodhi.broiler.Models.RelayModel;
import com.example.alirodhi.broiler.R;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RemoteFragment extends Fragment {

    public static final String URL = "http://192.168.43.140:3038/";

    ToggleButton switchButtonLamp;
    ToggleButton switchButtonFan;
    Button switchButtonSpray;
    ToggleButton switchButtonExhaustFan;

    private RelayModel relayState;

    private Socket sc;
    {
        try{
            //sc = IO.socket("https://ali.jagopesan.com/");
            sc = IO.socket("http://192.168.43.140:3038/");
        }catch (URISyntaxException e){
            throw new RuntimeException(e);
        }
    }

    public RemoteFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_remote, container, false);

        sc.on("relay1", statusLamp);
        sc.on("relay2", statusFan);
        sc.on("relay3", statusSpray);
        sc.on("relay4", statusExhaustFan);
        sc.connect();

        switchButtonLamp = (ToggleButton) view.findViewById(R.id.switchBtnLamp);
        switchButtonLamp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                JSONObject relayLamp = new JSONObject();
                if (isChecked){
                    try{
                        relayLamp.put("status", true);
                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                    relayLampTrue();
                    switchButtonLamp.setTextColor(getResources().getColor(R.color.colorPrimary));
                    //toggleButtonLamp.setText("ON");
                    //Toast.makeText(getActivity(), "Toggle button spray is on", Toast.LENGTH_LONG).show();
                } else {
                    try{
                        relayLamp.put("status", false);
                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                    relayLampFalse();
                    switchButtonLamp.setTextColor(getResources().getColor(R.color.grey));
                    //toggleButtonLamp.setText("OFF");
                    //Toast.makeText(getActivity(), "Toggle button spray is off", Toast.LENGTH_LONG).show();
                }
                sc.emit("relay1", relayLamp);
            }
        });

        switchButtonFan = (ToggleButton) view.findViewById(R.id.switchBtnFan);
        switchButtonFan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                JSONObject relayFan = new JSONObject();
                if (isChecked){
                    try{
                        relayFan.put("status", true);
                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                    relayFanTrue();
                    switchButtonFan.setTextColor(getResources().getColor(R.color.colorPrimary));
                    //toggleButtonFan.setText("ON");
                    //Toast.makeText(getActivity(), "Toggle button Fan is on", Toast.LENGTH_LONG).show();
                } else {
                    try{
                        relayFan.put("status", false);
                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                    relayFanFalse();
                    switchButtonFan.setTextColor(getResources().getColor(R.color.grey));
                    //toggleButtonFan.setText("OFF");
                    //Toast.makeText(getActivity(), "Toggle button Fan is off", Toast.LENGTH_LONG).show();
                }
                sc.emit("relay2", relayFan);
            }
        });

        switchButtonSpray = (Button) view.findViewById(R.id.switchBtnSpray);
        switchButtonSpray.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int aksi = event.getAction();

                if (aksi == MotionEvent.ACTION_DOWN){
                    Log.d("test", "penek");
                    relaySprayTrue();
                    JSONObject relaySpray1 = new JSONObject();
                    try {
                        relaySpray1.put("status", true);
                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                    sc.emit("relay3", relaySpray1);

                } else if (aksi == event.ACTION_UP){
                    Log.d("test", "ga penek");
                    relaySprayFalse();
                    JSONObject relaySpray2 = new JSONObject();
                    try {
                        relaySpray2.put("status", false);
                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                    sc.emit("relay3", relaySpray2);

                }
                return false;
            }
        });
//        switchButtonSpray.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
//                JSONObject relaySpray = new JSONObject();
//                if (isChecked){
//                    try {
//                        relaySpray.put("status", true);
//                    }catch (JSONException e){
//                        e.printStackTrace();
//                    }
//                    relaySprayTrue();
//                    //toggleButtonSpray.setText("ON");
//                    //Toast.makeText(getActivity(), "Toggle button spray is on", Toast.LENGTH_LONG).show();
//                } else {
//                    try{
//                        relaySpray.put("status", false);
//                    }catch (JSONException e){
//                        e.printStackTrace();
//                    }
//                    relaySprayFalse();
//                    //toggleButtonFan.setText("OFF");
//                    //Toast.makeText(getActivity(), "Toggle button spray is off", Toast.LENGTH_LONG).show();
//                }
//                sc.emit("relay3", relaySpray);
//            }
//        });

        switchButtonExhaustFan = (ToggleButton) view.findViewById(R.id.switchBtnExhaustFan);
        switchButtonExhaustFan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                JSONObject relayExhaustFan = new JSONObject();
                if (isChecked){
                    try {
                        relayExhaustFan.put("status", true);
                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                    relayExhaustFanTrue();
                    switchButtonExhaustFan.setTextColor(getResources().getColor(R.color.colorPrimary));
                    //toggleButtonSpray.setText("ON");
                    //Toast.makeText(getActivity(), "Toggle button exhaust fan is on", Toast.LENGTH_LONG).show();
                } else {
                    try{
                        relayExhaustFan.put("status", false);
                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                    relayExhaustFanFalse();
                    switchButtonExhaustFan.setTextColor(getResources().getColor(R.color.grey));
                    //Toast.makeText(getActivity(), "Toggle button exhsust fan is off", Toast.LENGTH_LONG).show();
                }
                //toggleButtonSpray.setText("OFF");
                sc.emit("relay4", relayExhaustFan);
            }
        });

        getStateRelay();

        return view;
    }


    //state relay
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
                switchButtonLamp.setChecked(response.body().getLamp());
                switchButtonFan.setChecked(response.body().getFan());
                //switchButtonSpray.setChecked(response.body().getSpray());
                switchButtonExhaustFan.setChecked(response.body().getExhaust());

            }

            @Override
            public void onFailure(Call<RelayModel> call, Throwable t) {

            }
        });
    }

    //Emitter relay lampu
    private Emitter.Listener statusLamp = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            if(getActivity()!=null) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        JSONObject data = (JSONObject) args[0];
                        String status;
                        try {
                            status = data.getString("status");
                        } catch (JSONException e) {
                            return;
                        }
                        Log.e("Status lampu: ", status);
                        switchButtonLamp = (ToggleButton) getActivity().findViewById(R.id.switchBtnLamp);
                        if (status == "true") {
                            relayLampTrue();
                            switchButtonLamp.setTextColor(getResources().getColor(R.color.colorPrimary));
                        } else {
                            relayLampFalse();
                            switchButtonLamp.setTextColor(getResources().getColor(R.color.grey));
                        }
                    }
                });
            }
        }
    };

    private void relayLampTrue() {}

    private void relayLampFalse() {}

    //Emitter relay fan
    private Emitter.Listener statusFan = new Emitter.Listener() {
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
                        Log.e("Status kipas: ", status);
                        switchButtonFan = (ToggleButton) getActivity().findViewById(R.id.switchBtnFan);
                        if (status == "true"){
                            relayFanTrue();
                            switchButtonFan.setTextColor(getResources().getColor(R.color.colorPrimary));
                        }
                        else{
                            relayFanFalse();
                            switchButtonFan.setTextColor(getResources().getColor(R.color.grey));
                        }
                    }
                });
            }
        }
    };

    private void relayFanTrue() {}

    private void relayFanFalse() {}

    //Emitter relay spray
    private Emitter.Listener statusSpray = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            if(getActivity()!=null) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        JSONObject data = (JSONObject) args[0];
                        String status;
                        try {
                            status = data.getString("status");
                        } catch (JSONException e) {
                            return;
                        }
                        Log.e("Status spray: ", status);
                        switchButtonSpray = (Button) getActivity().findViewById(R.id.switchBtnSpray);
                        if (status == "true") {
                            relaySprayTrue();
                        } else {
                            relaySprayFalse();
                        }
                    }
                });
            }
        }
    };

    private void relaySprayTrue() {}

    private void relaySprayFalse() {}

    //Emitter relay exhaust fan
    private Emitter.Listener statusExhaustFan = new Emitter.Listener() {
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
                        Log.e("Status exhaust fan: ", status);
                        switchButtonExhaustFan = (ToggleButton) getActivity().findViewById(R.id.switchBtnExhaustFan);
                        if (status == "true"){
                            relayExhaustFanTrue();
                            switchButtonExhaustFan.setTextColor(getResources().getColor(R.color.colorPrimary));
                        }
                        else{
                            relayExhaustFanFalse();
                            switchButtonExhaustFan.setTextColor(getResources().getColor(R.color.grey));
                        }
                    }
                });
            }
        }
    };

    private void relayExhaustFanTrue() {}

    private void relayExhaustFanFalse() {}
}
