package com.example.dekel.goodworld;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;


public class MainActivity extends Activity {

    public enum Screens{
        HISTORY, SETTINGS;
    }
    public Screens screen;

    private final String TAG = "Main Activity";

    class Layout{

        Button btnSnd;
        Button btnHistory;
        Button btnSettings;

        private Layout(){
            btnSnd = (Button)findViewById(R.id.btnSMS);
            btnHistory = (Button)findViewById(R.id.btnHistory);
            btnSettings = (Button)findViewById(R.id.btnSettings);

        }

    }

    class Events{


        private Events(){

            layout.btnSnd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i(TAG, "Send button has been clicked");
                }
            });

            layout.btnHistory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i(TAG, "History button has been clicked");
                    switchToActivity(Screens.HISTORY);
                }
            });
            layout.btnSettings.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i(TAG, "Setting button was clicked");
                    switchToActivity(Screens.SETTINGS);
                }
            });
        }
    }

    Layout layout;
    Events events;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initObjects();
    }

    private void initObjects(){

        layout = new Layout();
        events = new Events();

    }

    private void switchToActivity(Screens screen){
        Intent intent;

        switch (screen){
            case HISTORY:
                Log.i(TAG, "Switch activity to history");
                intent = new Intent(MainActivity.this, HistoryActivity.class);
                startActivity(intent);
                break;
            case SETTINGS:
                Log.i(TAG, "Switch activity to settings");
                //intent = new Intent(MainActivity.this, SettingsActivity.class);
                //startActivity(intent);
                break;
        }
    }

}
