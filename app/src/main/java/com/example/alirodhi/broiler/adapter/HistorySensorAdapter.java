package com.example.alirodhi.broiler.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.alirodhi.broiler.DateParser;
import com.example.alirodhi.broiler.Models.HistorySensorModel;
import com.example.alirodhi.broiler.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by alirodhi on 7/9/2018.
 */

public class HistorySensorAdapter extends RecyclerView.Adapter<HistorySensorAdapter.MyViewHolder> {

    private Context context;
    private List<HistorySensorModel> historySensorModels;

    public HistorySensorAdapter(Context context, List<HistorySensorModel> historySensorModels) {
        this.context = context;
        this.historySensorModels = historySensorModels;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_sensor, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position){
        final HistorySensorModel historySensorModel = historySensorModels.get(position);

        holder.tvTemp.setText(historySensorModels.get(position).getTemp());
        holder.tvHum.setText(historySensorModels.get(position).getHum());
        holder.tvCdioksida.setText(historySensorModels.get(position).getCdioksida());
        holder.tvAmmonia.setText(historySensorModels.get(position).getAmmonia());
        holder.imgJam.setImageResource(R.drawable.ic_clock);
        holder.imgTgl.setImageResource(R.drawable.ic_calendar);

        float tempVal = Float.parseFloat(historySensorModel.getTemp());
        float humVal = Float.parseFloat(historySensorModel.getHum());
        float cdioksidaVal = Float.parseFloat(historySensorModel.getCdioksida());
        float ammoniaVal = Float.parseFloat(historySensorModel.getAmmonia());

        if (tempVal < 29 || humVal < 60 || cdioksidaVal < 350 || ammoniaVal < 0 ){
            holder.tvJudul.setText("Air is Low");
        } else if (
                (tempVal >= 29 && tempVal <= 32) ||
                (humVal >= 60 && humVal <= 70) ||
                (cdioksidaVal >= 350 && cdioksidaVal <= 2500) ||
                (ammoniaVal < 20)){
            holder.tvJudul.setText("Air is Normal");
        } else {
            holder.tvJudul.setText("Air is High");
        }

        final String date = formatDate(historySensorModels.get(position).getTimestamp());
        String[] dates = date.split(" ");
        holder.tvTanggal.setText(dates[0]+" "+ dates[1]+" "+ dates[2]+" "+dates[3]);
        holder.tvJam.setText(dates[4]);

        //Cara wahyu ade sasongko
        //holder.tvTanggal.setText(DateParser.parseDateToDayDateMonthYear(histories.get(position).getTimestamp()));
    }

    @Override
    public int getItemCount(){ return historySensorModels.size(); }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private CardView cardViewHistory;
        private TextView tvJudul, tvTemp, tvHum, tvCdioksida, tvAmmonia, tvTanggal, tvJam;
        private ImageView imgJam, imgTgl;

        public MyViewHolder(View itemView){
            super(itemView);

            cardViewHistory = (CardView)itemView.findViewById(R.id.card_view_sensor);
            tvJudul = (TextView)itemView.findViewById(R.id.tv_judul);
            tvTemp = (TextView)itemView.findViewById(R.id.tv_temp);
            tvHum = (TextView)itemView.findViewById(R.id.tv_hum);
            tvCdioksida = (TextView)itemView.findViewById(R.id.tv_cdioksida);
            tvAmmonia = (TextView)itemView.findViewById(R.id.tv_ammonia);
            tvTanggal = (TextView)itemView.findViewById(R.id.tanggal);
            tvJam = (TextView)itemView.findViewById(R.id.jam);
            imgTgl = (ImageView)itemView.findViewById(R.id.img_tgl);
            imgJam = (ImageView)itemView.findViewById(R.id.img_jam);
        }
    }

    /**
     * Formatting timestamp to `MMM d` format
     * Input: 2018-02-21 00:15:42
     * Output: Feb 21
     */
    private String formatDate(String dateStr) {
        try {
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = fmt.parse(dateStr);
            SimpleDateFormat fmtOut = new SimpleDateFormat("EEE, dd MMMM yyyy HH:mm");
            return fmtOut.format(date);
        } catch (ParseException e) {

        }

        return " ";
    }
}
