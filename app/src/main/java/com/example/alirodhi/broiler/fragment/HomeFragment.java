package com.example.alirodhi.broiler.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.Toast;

import com.example.alirodhi.broiler.R;


public class HomeFragment extends Fragment {

    GridLayout mainGrid;

    public HomeFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mainGrid = (GridLayout)view.findViewById(R.id.mainGrid);
        setSingleEvent(mainGrid);

        return view;
        //return inflater.inflate(R.layout.fragment_home, container, false);
    }

    private void setSingleEvent(GridLayout mainGrid) {
        //Loop all child item for mainGrid
        for(int i = 0; i < mainGrid.getChildCount(); i++){
            CardView cardView = (CardView)mainGrid.getChildAt(i);
            final int finalI = i;
            cardView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                 Toast.makeText(view.getContext(), "Clicked at index : " + finalI, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}