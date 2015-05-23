package com.example.dekel.goodworld;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.List;

/**
 * Created by Dekel on 5/10/2015.
 */
public class HistoryActivity extends Activity {

    private final String TAG = "History Activity";

    ListView listViewHistory;
    ListAdapter adapter;
    HistoryContactsDataSource dataSource;
    Context context;

    private void initAdapter() {
        Log.i(TAG, "Initializing the adapter for the list view");
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getContactArray());
        listViewHistory.setAdapter(adapter);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_layout);
        DatabaseInfo.contactsData.clear();
        listViewHistory = (ListView)findViewById(R.id.listviewHistory);
        initDatabase(getApplicationContext());
        initAdapter();
    }

    private void initDatabase(Context context){
        Log.i(TAG, "Start creating the database");
        dataSource = new HistoryContactsDataSource(context);
        Log.i(TAG,"Start loading the data");
        dataSource.loadContactsAndSumFromDatabase();
        Log.i(TAG, "Finished loading the data");
    }

    public String[] getContactArray(){
        String[] contactsHistory = new String[DatabaseInfo.contactsData.size()];
        contactsHistory = DatabaseInfo.contactsData.toArray(contactsHistory);
        return contactsHistory;
    }
}
