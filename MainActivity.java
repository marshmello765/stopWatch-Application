package com.example.stopwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    ImageView play_btn,pause_btn,lap_btn,stop_btn;
    TextView timeView,timeViewWithMs,timeLapse;
    int min,sec,hr,ms;
    int lapCount=0;
    boolean running;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initializing ImageView Objects

        play_btn = (ImageView)findViewById(R.id.play_bt);
        pause_btn = (ImageView)findViewById(R.id.pause_bt);
        lap_btn = (ImageView)findViewById(R.id.pause_bt);
        stop_btn = (ImageView)findViewById(R.id.stop_bt);

        //Initializing TextView Objects

        timeView = (TextView)findViewById(R.id.time_text_without_ms);
        timeViewWithMs = (TextView)findViewById(R.id.time_text_with_ms);
        timeLapse = (TextView)findViewById(R.id.time_lapse_text);

        play_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Simple Toast message to show for the user
                Toast.makeText(MainActivity.this, "You're Timer has begun", Toast.LENGTH_SHORT).show();
                //Buttons to hide
                play_btn.setVisibility(View.GONE);
                stop_btn.setVisibility(View.GONE);
                //Buttons to display
                pause_btn.setVisibility(View.VISIBLE);
                lap_btn.setVisibility(View.VISIBLE);
                running=true;
            }
        });
        pause_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "You're Timer has Paused", Toast.LENGTH_SHORT).show();

                play_btn.setVisibility(View.VISIBLE);
                stop_btn.setVisibility(View.VISIBLE);

                pause_btn.setVisibility(View.GONE);
                lap_btn.setVisibility(View.GONE);

                running=false;

            }
        });

        stop_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                running=false;
                Toast.makeText(MainActivity.this, "You're Timer has stopped", Toast.LENGTH_SHORT).show();
                sec=0;
                lapCount=0;
                timeView.setText("00:00:00");
                timeViewWithMs.setText("00");
                timeLapse.setText("");

                play_btn.setVisibility(View.VISIBLE);

                pause_btn.setVisibility(View.GONE);
                lap_btn.setVisibility(View.GONE);
                stop_btn.setVisibility(View.GONE);
            }
        });
        timeLapse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timeLapse();
            }
        });
        runtimer();
    }
    public void timeLapse(){
        lapCount++;
         String lapText = String.format(Locale.getDefault(),"%02d:%02d:%02d",hr,min,sec);
         String msText = String.format(Locale.getDefault(),"%02d",ms);
         lapText= lapText+":"+msText;
         lapText = "Lap : "+lapCount+" : "+lapText+"\n"+timeLapse.getText();
         Toast.makeText(MainActivity.this,"Lap"+lapCount,Toast.LENGTH_SHORT).show();
         timeLapse.setText(lapText);
    }
    public void runtimer(){
        final Handler handlerTime = new Handler();
        final Handler handlerMs = new Handler();
        handlerTime.post(new Runnable() {
            @Override
            public void run() {
                hr = sec / 3600;
                min = (sec % 3600) / 60;
                sec = sec % 60;
                if (running){
                    String time = String.format(Locale.getDefault(),"%02d:%02d:%02d",hr,min,sec);
                    timeView.setText(time);
                    sec++;
                }
                handlerTime.postDelayed(this,1000);
            }
        });
        handlerMs.post(new Runnable() {
            @Override
            public void run() {
                if(ms>=99){
                    ms=0;
                }
                if(running){
                    String msString = String.format(Locale.getDefault(),"%02d",ms);
                    timeViewWithMs.setText(msString);
                    ms++;
                }
                handlerMs.postDelayed(this,1);
            }
        });
    }
}