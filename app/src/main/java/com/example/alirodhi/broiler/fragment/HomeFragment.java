package com.example.alirodhi.broiler.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.alirodhi.broiler.Models.LogModel;
import com.example.alirodhi.broiler.Models.SensorModel;
import com.example.alirodhi.broiler.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class HomeFragment extends Fragment {

    //public static final String URL = "https://ali.jagopesan.com/";
    public static final String URL = "http://192.168.43.140:3038/";

    private List<SensorModel> sensorModels = new ArrayList<>();

    public HomeFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        String currentDateandTime = new SimpleDateFormat("dd - MM - yyyy").format(new Date());
        TextView textView = (TextView)view.findViewById(R.id.date);
        textView.setText(currentDateandTime);

        return view;
    }






}