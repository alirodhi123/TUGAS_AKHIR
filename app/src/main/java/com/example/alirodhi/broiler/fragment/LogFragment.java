package com.example.alirodhi.broiler.fragment;


import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.alirodhi.broiler.API.ServiceAPI;
import com.example.alirodhi.broiler.Models.LogModel;
import com.example.alirodhi.broiler.Models.ResponseModel;
import com.example.alirodhi.broiler.R;
import com.example.alirodhi.broiler.adapter.RecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LogFragment extends Fragment {

    public static final String URL = "https://ali.jagopesan.com/";

    //Deklarasi
    private RecyclerView mRecyclerView;
    private RecyclerAdapter recyclerAdapter;
    //private List<LogActivity> listLogActivity;
    private List<LogModel> logModels = new ArrayList<>();
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
        RecyclerAdapter adapter = new RecyclerAdapter(getContext(), logModels);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(adapter);
//        recyclerAdapter = new RecyclerAdapter(getContext(), logModels);
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
//        mRecyclerView.setLayoutManager(layoutManager);
//        mRecyclerView.setAdapter(recyclerAdapter);
        getLogLamp();
        return view;
    }

    private void getLogLamp(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ServiceAPI serviceAPI = retrofit.create(ServiceAPI.class);
        Call<ResponseModel> call = serviceAPI.getLogLamp();
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                String value = response.body().getStatus();
                if (value.equals("success")){
                    logModels = response.body().getData();
                    recyclerAdapter = new RecyclerAdapter(getActivity(), logModels);
                    mRecyclerView.setAdapter(recyclerAdapter);
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {

            }
        });
    }
}
