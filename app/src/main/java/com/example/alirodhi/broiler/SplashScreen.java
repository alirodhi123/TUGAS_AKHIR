package com.example.alirodhi.broiler;

import android.content.Intent;
import android.graphics.Typeface;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by alirodhi on 7/22/2018.
 */

public class SplashScreen extends AppCompatActivity{

    private TextView tv_judul, tv_ket;
    private ImageView img_launcher;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        Typeface typeface = Typeface.createFromAsset(getAssets(), "font/Roboto-Condensed.ttf");

        tv_judul = (TextView)findViewById(R.id.tv_splashbroder);
        tv_ket = (TextView)findViewById(R.id.tv_splashket);
        img_launcher = (ImageView)findViewById(R.id.broder_launcher);

        tv_judul.setTypeface(typeface);
        tv_ket.setTypeface(typeface);

        Animation animation = AnimationUtils.loadAnimation(this,R.anim.mytransition);

        //Start Animation
        img_launcher.startAnimation(animation);
        tv_judul.startAnimation(animation);
        tv_ket.startAnimation(animation);

        final Intent intent = new Intent(this, MainActivity.class);
        Thread timer = new Thread(){
            public void run (){
                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    startActivity(intent);
                    finish();
                }
            }
        };
        timer.start();
    }

}
