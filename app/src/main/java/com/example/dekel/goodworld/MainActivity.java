package com.example.dekel.goodworld;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;


public class MainActivity extends Activity {

    HistoryContactsDataSource dataSource;
    private static final int PICK_CONTACT_REQUEST = 1;
    private SmsInfo smsInfo;
    private int mMinute;
    private int mHour;


    private enum Screens{
        HISTORY, SETTINGS;
    }

    public MainActivity(){
        smsInfo = new SmsInfo();
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
        Button btnClearDatabase;


        private Layout(){
            btnSnd = (Button)findViewById(R.id.btnSMS);
            btnHistory = (Button)findViewById(R.id.btnHistory);
            btnSettings = (Button)findViewById(R.id.btnSettings);
            btnChoose = (Button)findViewById(R.id.btnPick);
            etNumber = (EditText)findViewById(R.id.etPhoneNumber);
            etMessage = (EditText)findViewById(R.id.etMessage);
            tvName = (TextView)findViewById(R.id.tvName);
            btnClean = (Button)findViewById(R.id.btnClean);
            btnClearDatabase = (Button)findViewById(R.id.btnClear);

        }
    }

    class Events{


    private Events(){

            layout.btnClearDatabase.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dataSource.deleteDataFromDatabase();
                    Toast.makeText(getApplicationContext(), "Your history has been deleted", Toast.LENGTH_LONG).show();
                }
            });

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
                        String number = removeExtras(layout.etNumber.getText().toString());

                        Log.i(TAG, "the number is: " + number);
                        sendMessage(layout.tvName.getText().toString(),
                                    number,
                                    layout.etMessage.getText().toString());
                        showAlertDialog(MainActivity.this);
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

    private String removeExtras(String number) {

        number = number.trim();
        number = number.replace("(","");
        number = number.replace(")", "");
        number = number.replace("-","");
        number = number.replace(" ","");

        return  number;
    }

    Layout layout;
    Events events;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initObjects();
        dataSource = new HistoryContactsDataSource(getApplicationContext());
    }

    @Override
    protected void onResume() {
        super.onResume();
        //notifyManager.cancel(NOTIFY_ID);
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
                popDialog();
                break;
        }
    }

    private void popDialog() {

        final Calendar calendar = Calendar.getInstance();
        mHour = calendar.get(Calendar.HOUR_OF_DAY);
        mMinute = calendar.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog tpd = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hour,
                                          int minute) {
//                        Set the time of the notification
                        setNotification(hour,minute,0);
                    }
                }, mHour, mMinute, false);

        tpd.show();

    }
    private void sendMessage(String name, String number, String message){
        smsInfo.setUsername(name);
        smsInfo.setUserNumber(number);
        smsInfo.setMessage(message);
        Log.i(TAG, "Inserting data into database");
        dataSource.insertDataToDatabase(smsInfo);
        Log.i(TAG, "recipient data has been inserted into database");
        layout.tvName.setText("");
        layout.etNumber.setText("");
        layout.etMessage.setText("");

//        SendingMessage.instance().sendSMS(getBaseContext(),number,message);
    }

    private void showAlertDialog(Context context){
        new AlertDialog.Builder(context)
                .setTitle("Great job!!!")
                .setMessage("you have just made someone feel really good!\n keep on the great work")
                .setIcon(android.R.drawable.ic_dialog_info)
                .show();
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

    private void setNotification(int hour, int minute, int second){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,hour);
        calendar.set(Calendar.MINUTE,minute);
        calendar.set(Calendar.SECOND, second);
        Intent intent = new Intent(MainActivity.this, AlarmReciever.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am = (AlarmManager) MainActivity.this.getSystemService(MainActivity.this.ALARM_SERVICE);
        am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }
}
