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

    HistoryData mHistoryData;
    private List<String> contactsData;
    String[] contactsHistory;
    ListView listViewHistory;
    ListAdapter adapter;

    public HistoryActivity() {
        init();
    }

    private void initAdapter() {

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, contactsHistory);
        listViewHistory.setAdapter(adapter);

    }

    private void init() {
        mHistoryData = new HistoryData();
        contactsHistory = new String[mHistoryData.getFullList().size()];
        contactsHistory = mHistoryData.getFullList().toArray(contactsHistory);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_layout);
        listViewHistory = (ListView)findViewById(R.id.listviewHistory);
        initAdapter();
    }
}
