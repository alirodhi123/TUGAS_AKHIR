package com.example.alirodhi.broiler.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.alirodhi.broiler.DateParser;
import com.example.alirodhi.broiler.Models.LogModel;
import com.example.alirodhi.broiler.R;

import java.util.List;

/**
 * Created by alirodhi on 2/21/2018.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder>{

    Context context;
    List<LogModel> mData;

    public RecyclerAdapter(Context context, List<LogModel> mData) {
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
        LogModel logModel = mData.get(position);

        final String date = DateParser.parseDateToDayDateMonthYear(mData.get(position).getTanggal());
        String[] dates = date.split(" ");

        holder.tv_judul.setText(mData.get(position).getTitle());
        holder.tv_jam.setText(dates[4]);
        holder.tv_tanggal.setText(dates[0] +" "+ dates[1] +" "+ dates[2]+" "+ dates[3]);
        holder.tv_deskripsi.setText(mData.get(position).getKeterangan());
        //holder.img.setImageResource(mData.get(position).getImage());
        holder.img_jam.setImageResource(R.drawable.ic_clock);
        holder.img_tgl.setImageResource(R.drawable.ic_calendar);

        switch (mData.get(position).getTitle()){
            case "Lamp On":
                holder.img.setImageResource(R.drawable.ic_lamp_on);
                break;

            case "Lamp Off":
                holder.img.setImageResource(R.drawable.ic_lamp_off);
                break;

            case "Fan On":
                holder.img.setImageResource(R.drawable.ic_fan_on);
                break;

            case "Fan Off":
                holder.img.setImageResource(R.drawable.ic_fan_off);
                break;

            case "Spray On":
                holder.img.setImageResource(R.drawable.ic_spray_on);
                break;

            case "Spray Off":
                holder.img.setImageResource(R.drawable.ic_spray_off);
                break;

            case "Exhaust On":
                holder.img.setImageResource(R.drawable.ic_fan_out);
                break;

            case "Exhaust Off":
                holder.img.setImageResource(R.drawable.ic_fan_out_off);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder{

        private TextView tv_judul;
        private TextView tv_jam;
        private TextView tv_deskripsi;
        private TextView tv_tanggal;
        private ImageView img;
        private ImageView img_jam;
        private ImageView img_tgl;


        public RecyclerViewHolder(View itemView){
            super(itemView);

            tv_judul = (TextView)itemView.findViewById(R.id.judul);
            tv_jam = (TextView)itemView.findViewById(R.id.jam);
            tv_tanggal = (TextView)itemView.findViewById(R.id.tanggal);
            tv_deskripsi = (TextView)itemView.findViewById(R.id.deskripsi);
            img = (ImageView)itemView.findViewById(R.id.daftar_icon);
            img_jam = (ImageView)itemView.findViewById(R.id.img_jam);
            img_tgl = (ImageView)itemView.findViewById(R.id.img_tgl);
        }
    }
}


