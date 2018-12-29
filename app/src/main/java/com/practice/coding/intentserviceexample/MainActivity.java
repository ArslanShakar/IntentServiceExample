package com.practice.coding.intentserviceexample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView tv;
    private ProgressBar progressBar;

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String songName = intent.getStringExtra(Constants.MESSAGE_KEY);
            tv.append("\n"+songName+" : downloaded");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressBarHorizontal);
        tv = findViewById(R.id.textView);

    }

    @Override
    protected void onStart() {
        super.onStart();
        //register broadcast
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(mReceiver, new IntentFilter(Constants.Intent_SERVICE_MESSAGE));
    }

    @Override
    protected void onStop() {
        super.onStop();
        //unregister broadcast for avoiding resources leaking
        LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(mReceiver);
    }

    public void runService(View view) {
        displayProgresBar(true);
        tv.setText("\nCode Running...");

        for (String song : Playlist.songs) {
            /*
            For every song intent executed and send msg to to StartedService class..like 3 songs 3 times intent executed and send message..
             */
            Intent intent = new Intent(this, MyIntentService.class);
            intent.putExtra(Constants.MESSAGE_KEY, song);

            startService(intent); //here Service Start and startService take intent parameter..
            //this intent is reveived in StartedService Java Class in onStartCommand..
        }
    }

    public void displayProgresBar(boolean display) {
        if (display) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }
}
