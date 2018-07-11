package com.example.alirodhi.broiler.fragment;


import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.alirodhi.broiler.Models.HistorySensorModel;
import com.example.alirodhi.broiler.R;
import com.example.alirodhi.broiler.RecyclerTouchListener;
import com.example.alirodhi.broiler.adapter.HistorySensorAdapter;
import com.example.alirodhi.broiler.db.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alirodhi on 7/5/2018.
 */

public class LogSensorFragment extends Fragment {

    private List<HistorySensorModel> historySensorModels = new ArrayList<>();
    private DatabaseHelper db;

    private TextView tvJudul;
    private TextView tvTemp;
    private TextView tvHum;
    private TextView tvCdioksida;
    private TextView tvAmmonia;
    private RecyclerView rcHistory;
    private LinearLayout linearLayout;



    public LogSensorFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_log_sensor, container, false);

        rcHistory = (RecyclerView)view.findViewById(R.id.recycler_view_sensor);


        db = new DatabaseHelper(getContext());
        historySensorModels.addAll(db.getAllHistory());

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rcHistory.setLayoutManager(layoutManager);
        rcHistory.setItemAnimator(new DefaultItemAnimator());
        rcHistory.setAdapter(new HistorySensorAdapter(getContext(), historySensorModels));

        rcHistory.addOnItemTouchListener(new RecyclerTouchListener(getContext(), rcHistory, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
            }

            @Override
            public void onLongClick(View view, int position) {
                showActionsDialog(position);
            }
        }));

        return view;
    }


    /**
     * Delete histrory from sqlite database
     * SQLITE DATABASE
     * ---
     */
    private void deleteHistory(int position) {
        // deleting the note from db
        db.deleteHistory(historySensorModels.get(position));

        // removing the note from the list
        historySensorModels.remove(position);
        rcHistory.getAdapter().notifyItemRemoved(position);

        toggleEmptyHistory();
    }

    /**
     * If list on touche
     * SQLITE DATABASE
     * Output : will show delete dialog
     */
    private void showActionsDialog(final int position) {
        CharSequence colors[] = new CharSequence[]{"Delete"};

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setItems(colors, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteHistory(position);
            }
        });
        builder.show();
    }

    private void toggleEmptyHistory() {
        // You can check notesList.size() > 0

//        if (db.getHistoryCount() > 0) {
//            lnEmpty.setVisibility(View.GONE);
//        }
//        else {
//            lnEmpty.setVisibility(View.VISIBLE);
//        }
    }

}


