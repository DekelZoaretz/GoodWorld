package com.example.dekel.goodworld;

import android.content.Context;
import android.telephony.SmsManager;
import android.widget.Toast;

/**
 * Created by Dekel on 5/21/2015.
 */
public class SendingMessage {
    
    private static SendingMessage _Instance = null;

    public static SendingMessage instance() {
        
        if(_Instance == null){
            _Instance = new SendingMessage();
        }
        return _Instance;
    }

    public void sendSMS(Context context, String phoneNo, String message) {

        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo, null, message, null, null);
            Toast.makeText(context, context.getString(R.string.message_sent),Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(context, context.getString(R.string.message_failed), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
}
