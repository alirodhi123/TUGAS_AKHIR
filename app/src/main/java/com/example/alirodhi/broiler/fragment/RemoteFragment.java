package com.example.alirodhi.broiler.fragment;



import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.alirodhi.broiler.R;

public class RemoteFragment extends Fragment {

    public RemoteFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_remote, container, false);

        ToggleButton toggleButtonFan = (ToggleButton)view.findViewById(R.id.toggleBtnFan);
        toggleButtonFan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    Toast.makeText(getActivity(), "Toggle button spray is on", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getActivity(), "Toggle button spray is off", Toast.LENGTH_LONG).show();
                }
            }
        });

        ToggleButton toggleButtonLamp = (ToggleButton)view.findViewById(R.id.toggleBtnLamp);
        toggleButtonLamp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    Toast.makeText(getActivity(), "Toggle button Lamp is on", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getActivity(), "Toggle button Lamp is off", Toast.LENGTH_LONG).show();
                }
            }
        });

        ToggleButton toggleButtonSpray = (ToggleButton)view.findViewById(R.id.toggleBtnSpray);
        toggleButtonSpray.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    Toast.makeText(getActivity(), "Toggle button spray is on", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getActivity(), "Toggle button spray is off", Toast.LENGTH_LONG).show();
                }
            }
        });

        ToggleButton toggleButtonFanOut = (ToggleButton)view.findViewById(R.id.toggleBtnFanOut);
        toggleButtonFanOut.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    Toast.makeText(getActivity(), "Toggle button fan out is on", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getActivity(), "Toggle button fan out is off", Toast.LENGTH_LONG).show();
                }
            }
        });

        return view;
    }
}
