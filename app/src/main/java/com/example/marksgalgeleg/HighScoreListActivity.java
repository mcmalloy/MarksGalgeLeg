package com.example.marksgalgeleg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;

public class HighScoreListActivity extends AppCompatActivity {
    ListView listView;
    TextView textView;
    ArrayList<Data> scores = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score_list);
        listView=(ListView)findViewById(R.id.listView);
        textView=(TextView)findViewById(R.id.textView);
        scores = (ArrayList<Data>) getIntent().getExtras().get("scores");
        final ArrayAdapter<Data> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1,scores);
        listView.setAdapter(adapter);
    }

    private void populateUserList(){
        ArrayList<Data> arrayOfData = Data.getData();
        CustomDataAdapter adapter = new CustomDataAdapter(this, arrayOfData);
        // Attach the adapter to a ListView
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);
    }
}
