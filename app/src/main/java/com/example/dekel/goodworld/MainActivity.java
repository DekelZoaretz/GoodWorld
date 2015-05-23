package com.example.dekel.goodworld;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity {

    HistoryContactsDataSource dataSource;
    private static final int PICK_CONTACT_REQUEST = 1;

    private enum Screens{
        HISTORY, SETTINGS;
    }

    private final String TAG = "Main Activity";

    class Layout{

        Button btnSnd;
        Button btnHistory;
        Button btnSettings;
        Button btnChoose;
        EditText etNumber;
        EditText etMessage;
        TextView tvName;
        Button btnClean;


        private Layout(){
            btnSnd = (Button)findViewById(R.id.btnSMS);
            btnHistory = (Button)findViewById(R.id.btnHistory);
            btnSettings = (Button)findViewById(R.id.btnSettings);
            btnChoose = (Button)findViewById(R.id.btnPick);
            etNumber = (EditText)findViewById(R.id.etPhoneNumber);
            etMessage = (EditText)findViewById(R.id.etMessage);
            tvName = (TextView)findViewById(R.id.tvName);
            btnClean = (Button)findViewById(R.id.btnClean);

        }
    }

    class Events{


        private Events(){

            layout.btnChoose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Pick from the contacts list
                    pickContact();
                }
            });

            layout.btnClean.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    layout.tvName.setText("");
                    layout.etNumber.setText("");
                }
            });


            layout.btnSnd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i(TAG, "Send button has been clicked");
                    if(layout.etNumber.getText().toString().trim().length() == 0 || layout.etMessage.getText().toString().trim().length()==0){
                        Toast.makeText(getBaseContext(), "Please enter both number and message", Toast.LENGTH_LONG).show();
                    }else {
                        Log.i(TAG, "Number and message are not empty");
                        sendMessage(layout.tvName.getText().toString(),
                                layout.etNumber.getText().toString(),
                                layout.etMessage.getText().toString());
                    }
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
                    //Toast.makeText(getApplicationContext(), "You just pressed setting button",Toast.LENGTH_LONG).show();
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

    private void sendMessage(String name, String number, String message){

    }

    private void pickContact() {
        Intent pickContactIntent = new Intent(Intent.ACTION_PICK, Uri.parse("content://contacts"));
        pickContactIntent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE); // Show user only contacts w/ phone numbers
        startActivityForResult(pickContactIntent, PICK_CONTACT_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request it is that we're responding to
        if (requestCode == PICK_CONTACT_REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                // Get the URI that points to the selected contact
                Uri contactUri = data.getData();
                // Taking the number and the name of the recipient
                String[] projection = {ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME};
                Cursor cursor = getContentResolver()
                        .query(contactUri, projection, null, null, null);
                cursor.moveToFirst();

                // Retrieve the phone number and the name
                int NumberColumn = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                String number = cursor.getString(NumberColumn);

                int NameColumn = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                String name = cursor.getString(NameColumn);

                layout.etNumber.setText(number);
                layout.tvName.setText(name);
            }
        }
    }
}
