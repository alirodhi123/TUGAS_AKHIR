package com.example.alirodhi.broiler.fragment;



import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.ToggleButton;

import com.example.alirodhi.broiler.R;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

public class RemoteFragment extends Fragment {

    Switch switchButtonLamp;
    Switch switchButtonFan;
    Switch switchButtonSpray;
    Switch switchButtonExhaustFan;

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

        final Switch switchButtonLamp = (Switch) view.findViewById(R.id.switchBtnLamp);
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
                    //toggleButtonLamp.setText("ON");
                    //Toast.makeText(getActivity(), "Toggle button spray is on", Toast.LENGTH_LONG).show();
                } else {
                    try{
                        relayLamp.put("status", false);
                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                    //toggleButtonLamp.setText("OFF");
                    //Toast.makeText(getActivity(), "Toggle button spray is off", Toast.LENGTH_LONG).show();
                }
                sc.emit("relay1", relayLamp);
            }
        });

        final Switch switchButtonFan = (Switch) view.findViewById(R.id.switchBtnFan);
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
                    //toggleButtonFan.setText("ON");
                    //Toast.makeText(getActivity(), "Toggle button Fan is on", Toast.LENGTH_LONG).show();
                } else {
                    try{
                        relayFan.put("status", false);
                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                    //toggleButtonFan.setText("OFF");
                    //Toast.makeText(getActivity(), "Toggle button Fan is off", Toast.LENGTH_LONG).show();
                }
                sc.emit("relay2", relayFan);
            }
        });

        final Switch switchButtonSpray = (Switch) view.findViewById(R.id.switchBtnSpray);
        switchButtonSpray.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                JSONObject relaySpray = new JSONObject();
                if (isChecked){
                    try {
                        relaySpray.put("status", true);
                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                    //toggleButtonSpray.setText("ON");
                    //Toast.makeText(getActivity(), "Toggle button spray is on", Toast.LENGTH_LONG).show();
                } else {
                    try{
                        relaySpray.put("status", false);
                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                    //toggleButtonFan.setText("OFF");
                    //Toast.makeText(getActivity(), "Toggle button spray is off", Toast.LENGTH_LONG).show();
                }
                sc.emit("relay3", relaySpray);
            }
        });

        Switch switchButtonExhaustFan = (Switch) view.findViewById(R.id.switchBtnExhaustFan);
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
                    //toggleButtonSpray.setText("ON");
                    //Toast.makeText(getActivity(), "Toggle button exhaust fan is on", Toast.LENGTH_LONG).show();
                } else {
                    try{
                        relayExhaustFan.put("status", false);
                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                    //Toast.makeText(getActivity(), "Toggle button exhsust fan is off", Toast.LENGTH_LONG).show();
                }
                //toggleButtonSpray.setText("OFF");
                sc.emit("relay4", relayExhaustFan);
            }
        });

        return view;
    }

    //Emitter relay lampu
    private Emitter.Listener statusLamp = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
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
                    Log.e("Status lampu: ", status);
                    switchButtonLamp = (Switch) getActivity().findViewById(R.id.switchBtnLamp);
                    if (status== "true"){
                        switchButtonLamp.setChecked(true);
                    }
                    else{
                        switchButtonLamp.setChecked(false);
                    }
                }
            });
        }
    };

    //Emitter relay fan
    private Emitter.Listener statusFan = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
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
                    switchButtonFan = (Switch) getActivity().findViewById(R.id.switchBtnFan);
                    if (status == "true"){
                        switchButtonFan.setChecked(true);
                    }
                    else{
                        switchButtonFan.setChecked(false);
                    }
                }
            });
        }
    };

    //Emitter relay spray
    private Emitter.Listener statusSpray = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
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
                    Log.e("Status spray: ", status);
                    switchButtonSpray = (Switch) getActivity().findViewById(R.id.switchBtnSpray);
                    if (status == "true"){
                        switchButtonSpray.setChecked(true);
                    }
                    else{
                        switchButtonSpray.setChecked(false);
                    }
                }
            });
        }
    };

    //Emitter relay exhaust fan
    private Emitter.Listener statusExhaustFan = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
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
                    switchButtonExhaustFan = (Switch) getActivity().findViewById(R.id.switchBtnExhaustFan);
                    if (status == "true"){
                        switchButtonExhaustFan.setChecked(true);
                    }
                    else{
                        switchButtonExhaustFan.setChecked(false);
                    }
                }
            });
        }
    };
}
