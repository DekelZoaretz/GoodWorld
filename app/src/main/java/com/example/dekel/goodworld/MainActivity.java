package com.example.dekel.goodworld;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;


public class MainActivity extends Activity {

    private final String TAG = "DekelMain";
    Context context;

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
                    switchToHistory("History");
                }
            });
            layout.btnSettings.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i(TAG, "Setting button was clicked");
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

        //Check if the database is exist
        //If yes, load the data
        //If not create the data base
    }

    private void initObjects(){

        layout = new Layout();
        events = new Events();

    }

    private void switchToHistory(String className){
        Intent intent;

        switch (className){
            case "History":
                Log.i(TAG, "Switch activity to history");
                intent = new Intent(MainActivity.this, HistoryActivity.class);
                startActivity(intent);
                break;
            case "Settings":
                Log.i(TAG, "Switch activity to settings");
                //intent = new Intent(MainActivity.this, SettingsActivity.class);
                //startActivity(intent);
                break;
        }
    }

}
