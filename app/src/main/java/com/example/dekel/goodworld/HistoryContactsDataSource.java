package com.example.dekel.goodworld;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Dekel on 5/16/2015.
 */
public class HistoryContactsDataSource {

    public DatabaseOperations mOperations;
    HashMap<String, Integer> map;
    private final String TAG = "Contacts DataSource";

    public HistoryContactsDataSource(Context context) {

        Log.i(TAG, "Constructor");
        mOperations = new DatabaseOperations(context);
        SQLiteDatabase db = mOperations.getReadableDatabase();
        db.close();
    }

    private SQLiteDatabase open(){
        return  mOperations.getWritableDatabase();
    }
    private void close(SQLiteDatabase database){
        database.close();
    }

    public void loadContactsAndSumFromDatabase(){
        map = new HashMap<String, Integer>();
        String currentName = "";
        Integer sum = 1;
//        Getting the data (USERNAME) from the database and load it to contactsData
        SQLiteDatabase db = open();
        Cursor cursor = db.query(DatabaseInfo.TABLE_NAME,
                new String[] {DatabaseInfo.COLUMN_USER_NAME},
                null, //selection
                null, //selection args
                null, //group by
                null, //having
                null); //order by
        if(cursor.moveToFirst()){
            do{
                currentName = getStringFromColumnName(cursor, DatabaseInfo.COLUMN_USER_NAME);

                if(!map.containsKey(currentName)){
                    map.put(currentName, sum);
                }else{
                    map.put(currentName, map.get(currentName)+1);
                }
            }while(cursor.moveToNext());
        }
        cursor.close();
        close(db);

        for(Map.Entry<String,Integer> entry : map.entrySet()){
            DatabaseInfo.contactsData.add(entry.getKey() + " (" + String.valueOf(entry.getValue()) + ")");
        }
    }


    //    Get the data from the database with the position of the cursor
    private String getStringFromColumnName(Cursor cursor, String columnName){
        int columnIndex = cursor.getColumnIndex(columnName);
        return cursor.getString(columnIndex);
    }

    public void insertDataToDatabase(SmsInfo info){

        SQLiteDatabase database = open();
        database.beginTransaction();

        ContentValues userValues = new ContentValues();
        userValues.put(DatabaseInfo.COLUMN_USER_NAME, info.getUsername());
        userValues.put(DatabaseInfo.COLUMN_USER_MSG, info.getMessage());
        userValues.put(DatabaseInfo.COLUMN_USER_NUM, info.getUserNumber());
        long userID = database.insert(DatabaseInfo.TABLE_NAME, null, userValues);

        database.setTransactionSuccessful();
        database.endTransaction();
        close(database);

    }

    public void deleteDataFromDatabase(){
        SQLiteDatabase database = open();
        database.beginTransaction();

        database.delete(DatabaseInfo.TABLE_NAME, null, null);

        database.setTransactionSuccessful();
        database.endTransaction();
        database.close();
    }
}
