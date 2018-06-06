package com.example.alirodhi.broiler.fragment;


import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.alirodhi.broiler.LogActivity;
import com.example.alirodhi.broiler.R;
import com.example.alirodhi.broiler.adapter.RecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

public class LogFragment extends Fragment {

    //Deklarasi
    private RecyclerView mRecyclerView;
    private List<LogActivity> listLogActivity;
    View view;

    public LogFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_log, container, false);

        mRecyclerView = (RecyclerView)view.findViewById(R.id.recycler_view);
        RecyclerAdapter adapter = new RecyclerAdapter(getContext(), listLogActivity);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        listLogActivity = new ArrayList<>();
        listLogActivity.add(new LogActivity("Kipas On", "20:00", "Kipas menyala karena suhu terlalu panas", R.drawable.ic_lamp_off));
        listLogActivity.add(new LogActivity("Kipas On", "20:00", "Kipas menyala karena suhu terlalu panas", R.drawable.ic_lamp_on));
        listLogActivity.add(new LogActivity("Kipas On", "20:00", "Kipas menyala karena suhu terlalu panas", R.drawable.ic_spray_off));
        listLogActivity.add(new LogActivity("Kipas On", "20:00", "Kipas menyala karena suhu terlalu panas", R.drawable.ic_spray_on));
        listLogActivity.add(new LogActivity("Kipas On", "20:00", "Kipas menyala karena suhu terlalu panas", R.drawable.ic_fan_off));
        listLogActivity.add(new LogActivity("Kipas On", "20:00", "Kipas menyala karena suhu terlalu panas", R.drawable.ic_fan_on));
        listLogActivity.add(new LogActivity("Kipas On", "20:00", "Kipas menyala karena suhu terlalu panas", R.drawable.ic_lamp_off));
        listLogActivity.add(new LogActivity("Kipas On", "20:00", "Kipas menyala karena suhu terlalu panas", R.drawable.ic_lamp_on));
        listLogActivity.add(new LogActivity("Kipas On", "20:00", "Kipas menyala karena suhu terlalu panas", R.drawable.ic_spray_off));
        listLogActivity.add(new LogActivity("Kipas On", "20:00", "Kipas menyala karena suhu terlalu panas", R.drawable.ic_spray_on));
        listLogActivity.add(new LogActivity("Kipas On", "20:00", "Kipas menyala karena suhu terlalu panas", R.drawable.ic_fan_off));
        listLogActivity.add(new LogActivity("Kipas On", "20:00", "Kipas menyala karena suhu terlalu panas", R.drawable.ic_fan_on));

    }
}
