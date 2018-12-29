package com.practice.coding.intentserviceexample;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

public class MyIntentService extends IntentService {
    public MyIntentService() {
        super("MyIntentService");

        /*
        setIntentRedelivery(true); //if app close then again service start and remaining task completes
         */
        setIntentRedelivery(true);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(Constants.TAG, "onCreate called  : Thread Name : "+Thread.currentThread().getName());
    }

    //only onHandleIntent Run on Background thread ..all others service method run on main thread
    //For better understanding watch lectures : Thread & Services by U4Universe
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String songName = intent.getStringExtra(Constants.MESSAGE_KEY);
        downloadSong(songName);

        sendDataToUIThread(songName);

        Log.d(Constants.TAG, "onHandleIntent called  : Thread Name : "+Thread.currentThread().getName());
    }

    private void sendDataToUIThread(String songName) {
        Intent intent = new Intent(Constants.Intent_SERVICE_MESSAGE);
        intent.putExtra(Constants.MESSAGE_KEY, songName);


        //send data thru local broadcast
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
    }

    private void downloadSong(String songName) {
        try {
            Log.d(Constants.TAG, "Download Starting . . .");
            Thread.sleep(4000);
            Log.d(Constants.TAG, songName+"Downloaded!");
        } catch (InterruptedException e) {
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(Constants.TAG, "onDestory called  : Thread Name : "+Thread.currentThread().getName());
    }
}
