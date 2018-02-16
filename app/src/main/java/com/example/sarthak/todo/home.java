package com.example.sarthak.todo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class home extends AppCompatActivity {
    private RecyclerView recycler;
    private InformationAdapter adapter;
    private ArrayList<Information> test = new ArrayList<>();
    public static SharedPreferences pref;
    private int count;
    public static final String PREF_NAME = "Data";
    public static final String PREF_COUNT_KEY = "count";
    public static final String PREF_DATA_KEY = "data-";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        pref = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        count = pref.getInt(PREF_COUNT_KEY, 0);
        try {
            for (int i = count; i > 0; i--) {
                Log.d("App", "Event:" + i);
                Information inf = new Information().setInformationFromJSON(pref.getString(PREF_DATA_KEY + i, "{}"), i);
                if (inf == null)
                    continue;
                else
                    test.add(inf);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        recycler = findViewById(R.id.list);
        adapter = new InformationAdapter(this, test);
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent intent = new Intent(getApplicationContext(), create.class);
                Bundle args = new Bundle();
                args.putSerializable("ARRAYLIST", test);
                intent.putExtra("TODOLIST", args);
                startActivity(intent);
            }
        });
    }

    public void deletethis(int pos) {
        Information inf = test.remove(pos);
        Log.d("App", "Pos : " + inf.position);
        pref.edit().remove(PREF_DATA_KEY + inf.position).apply();
        adapter.notifyDataSetChanged();
    }

}
