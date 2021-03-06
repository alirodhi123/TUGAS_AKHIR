package com.example.alirodhi.broiler.fragment;


import android.app.ProgressDialog;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alirodhi.broiler.API.ServiceAPI;
import com.example.alirodhi.broiler.Models.LogModel;
import com.example.alirodhi.broiler.Models.ResponseLogModel;
import com.example.alirodhi.broiler.R;
import com.example.alirodhi.broiler.adapter.RecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LogRelayFragment extends Fragment {

    //public static final String URL = "https://ali.jagopesan.com/";
    public static final String URL = "http://192.168.43.140:3038/";

    //Deklarasi
    private RecyclerView mRecyclerView;
    private RecyclerAdapter recyclerAdapter;
    private List<LogModel> logModels = new ArrayList<>();
    private ProgressDialog progressDialog;
    private LinearLayout linearLayout;
    View view;

    public LogRelayFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_log_relay, container, false);

        linearLayout = (LinearLayout) view.findViewById(R.id.ln_empty);
        mRecyclerView = (RecyclerView)view.findViewById(R.id.recycler_view);

        RecyclerAdapter adapter = new RecyclerAdapter(getContext(), logModels);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(adapter);
//        recyclerAdapter = new RecyclerAdapter(getContext(), logModels);
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
//        mRecyclerView.setLayoutManager(layoutManager);
//        mRecyclerView.setAdapter(recyclerAdapter);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading ...");
        progressDialog.show();

        // Memanggil method getLogLamp
        getLogLamp();
        return view;
    }

    //Method toolbar
    private void setSupportActionBar(Toolbar toolbar) {}


    /**
     * RETROFIT
     * Get all log from relay
     * Output: Relay on or Relay off, in log activiti will showed
     */
    private void getLogLamp(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ServiceAPI serviceAPI = retrofit.create(ServiceAPI.class);
        Call<ResponseLogModel> call = serviceAPI.getLogLamp();
        call.enqueue(new Callback<ResponseLogModel>() {
            @Override
            public void onResponse(Call<ResponseLogModel> call, Response<ResponseLogModel> response) {
                linearLayout.setVisibility(View.GONE);
                String value = response.body().getStatus();
                if (value.equals("success")){
                    logModels = response.body().getData();
                    recyclerAdapter = new RecyclerAdapter(getActivity(), logModels);
                    mRecyclerView.setAdapter(recyclerAdapter);
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<ResponseLogModel> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Jaringan Error!", Toast.LENGTH_SHORT).show();
                linearLayout.setVisibility(View.VISIBLE);
            }
        });
    }
    
}
