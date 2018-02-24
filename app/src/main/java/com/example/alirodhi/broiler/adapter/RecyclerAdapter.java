package com.example.alirodhi.broiler.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alirodhi.broiler.Log;
import com.example.alirodhi.broiler.R;

import java.util.List;

/**
 * Created by alirodhi on 2/21/2018.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder>{

    Context context;
    List<Log> mData;

    public RecyclerAdapter(Context context, List<Log> mData) {
        this.context = context;
        this.mData = mData;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        view = LayoutInflater.from(context).inflate(R.layout.list_item_log, parent, false);
        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(view);


        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {

        holder.tv_judul.setText(mData.get(position).getJudul());
        holder.tv_jam.setText(mData.get(position).getJam());
        holder.tv_deskripsi.setText(mData.get(position).getDeskripsi());
        holder.img.setImageResource(mData.get(position).getImage());


    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder{

        private TextView tv_judul;
        private TextView tv_jam;
        private TextView tv_deskripsi;
        private ImageView img;


        public RecyclerViewHolder(View itemView){
            super(itemView);

            tv_judul = (TextView)itemView.findViewById(R.id.judul);
            tv_jam = (TextView)itemView.findViewById(R.id.jam);
            tv_deskripsi = (TextView)itemView.findViewById(R.id.deskripsi);
            img = (ImageView)itemView.findViewById(R.id.daftar_icon);
        }

    }
}


