package com.example.dekel.goodworld;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.List;

/**
 * Created by Dekel on 5/10/2015.
 */
public class HistoryActivity extends Activity {

    private final String TAG = "DekelMain";

    private SqliteDataHandler mDataHandler;
    private List<String> contactsData;
    ListView listViewHistory;
    ListAdapter adapter;

    public HistoryActivity() {
        init();
    }

    private void initAdapter() {
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mDataHandler.getContactArray());
        listViewHistory.setAdapter(adapter);
    }

    private void init() {
        mDataHandler = new SqliteDataHandler();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_layout);
        listViewHistory = (ListView)findViewById(R.id.listviewHistory);
        initAdapter();
    }
}
