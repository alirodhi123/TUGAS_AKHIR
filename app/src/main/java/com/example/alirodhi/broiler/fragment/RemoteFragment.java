package com.example.alirodhi.broiler.fragment;



import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.example.alirodhi.broiler.R;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

public class RemoteFragment extends Fragment {

    ToggleButton toggleButtonLamp;
    ToggleButton toggleButtonFan;
    ToggleButton toggleButtonSpray;
    ToggleButton toggleButtonExhaustFan;

    private Socket sc;
    {
        try{
            sc = IO.socket("https://ali.jagopesan.com/");
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

        final ToggleButton toggleButtonLamp = (ToggleButton)view.findViewById(R.id.toggleBtnLamp);
        toggleButtonLamp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                JSONObject relayLamp = new JSONObject();
                if (isChecked){
                    try{
                        relayLamp.put("status", true);
                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                    toggleButtonLamp.setText("ON");
                    //Toast.makeText(getActivity(), "Toggle button spray is on", Toast.LENGTH_LONG).show();
                } else {
                    try{
                        relayLamp.put("status", false);
                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                    toggleButtonLamp.setText("OFF");
                    //Toast.makeText(getActivity(), "Toggle button spray is off", Toast.LENGTH_LONG).show();
                }
                sc.emit("relay1", relayLamp);
            }
        });

        final ToggleButton toggleButtonFan = (ToggleButton)view.findViewById(R.id.toggleBtnFan);
        toggleButtonFan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                JSONObject relayFan = new JSONObject();
                if (isChecked){
                    try{
                        relayFan.put("status", true);
                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                    toggleButtonFan.setText("ON");
                    //Toast.makeText(getActivity(), "Toggle button Fan is on", Toast.LENGTH_LONG).show();
                } else {
                    try{
                        relayFan.put("status", false);
                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                    toggleButtonFan.setText("OFF");
                    //Toast.makeText(getActivity(), "Toggle button Fan is off", Toast.LENGTH_LONG).show();
                }
                sc.emit("relay2", relayFan);
            }
        });

        final ToggleButton toggleButtonSpray = (ToggleButton)view.findViewById(R.id.toggleBtnSpray);
        toggleButtonSpray.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                JSONObject relaySpray = new JSONObject();
                if (isChecked){
                    try {
                        relaySpray.put("status", true);
                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                    toggleButtonSpray.setText("ON");
                    //Toast.makeText(getActivity(), "Toggle button spray is on", Toast.LENGTH_LONG).show();
                } else {
                    try{
                        relaySpray.put("status", false);
                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                    toggleButtonFan.setText("OFF");
                    //Toast.makeText(getActivity(), "Toggle button spray is off", Toast.LENGTH_LONG).show();
                }
                sc.emit("relay3", relaySpray);
            }
        });

        ToggleButton toggleButtonExhaustFan = (ToggleButton)view.findViewById(R.id.toggleBtnExhaustFan);
        toggleButtonExhaustFan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                JSONObject relayExhaustFan = new JSONObject();
                if (isChecked){
                    try {
                        relayExhaustFan.put("status", true);
                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                    toggleButtonSpray.setText("ON");
                    //Toast.makeText(getActivity(), "Toggle button exhaust fan is on", Toast.LENGTH_LONG).show();
                } else {
                    try{
                        relayExhaustFan.put("status", false);
                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                    //Toast.makeText(getActivity(), "Toggle button exhsust fan is off", Toast.LENGTH_LONG).show();
                }
                toggleButtonSpray.setText("OFF");
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
                    toggleButtonLamp = (ToggleButton) getActivity().findViewById(R.id.toggleBtnLamp);
                    if (status== "true"){
                        toggleButtonLamp.setChecked(true);
                    }
                    else{
                        toggleButtonLamp.setChecked(false);
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
                    toggleButtonFan = (ToggleButton) getActivity().findViewById(R.id.toggleBtnFan);
                    if (status == "true"){
                        toggleButtonFan.setChecked(true);
                    }
                    else{
                        toggleButtonFan.setChecked(false);
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
                    toggleButtonSpray = (ToggleButton) getActivity().findViewById(R.id.toggleBtnSpray);
                    if (status == "true"){
                        toggleButtonSpray.setChecked(true);
                    }
                    else{
                        toggleButtonSpray.setChecked(false);
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
                    toggleButtonExhaustFan = (ToggleButton) getActivity().findViewById(R.id.toggleBtnExhaustFan);
                    if (status == "true"){
                        toggleButtonExhaustFan.setChecked(true);
                    }
                    else{
                        toggleButtonExhaustFan.setChecked(false);
                    }
                }
            });
        }
    };
}

