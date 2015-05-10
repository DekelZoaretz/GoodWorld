package com.example.dekel.goodworld;

import android.app.Activity;
import android.content.Context;
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

        private Layout(){

            btnSnd = (Button)findViewById(R.id.btnSMS);
            btnHistory = (Button)findViewById(R.id.btnHistory);

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
        initilize();

        //Check if the database is exist
        //If yes, load the data
        //If not create the data base
    }

    private void initilize(){

        layout = new Layout();
        events = new Events();

    }

}
