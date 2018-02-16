package com.example.sarthak.todo;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import static com.example.sarthak.todo.home.PREF_COUNT_KEY;
import static com.example.sarthak.todo.home.PREF_DATA_KEY;
import static com.example.sarthak.todo.home.PREF_NAME;

public class create extends AppCompatActivity {
    private RecyclerView recycler;
    private InformationAdapter adapter;
    ArrayList<Information> data;
    private Button create;
    private EditText content;
    private EditText e1;
    private EditText e2;

    private Information temp;
    SharedPreferences pref;
    private ArrayList<Information> object;

    Calendar myCalendar = Calendar.getInstance();

    private void updateLabel() {
        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        e1.setText(sdf.format(myCalendar.getTime()));
    }

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }
    };
    TimePickerDialog.OnTimeSetListener time = new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            myCalendar.set(Calendar.HOUR, hourOfDay);
            myCalendar.set(Calendar.MINUTE, minute);
            e2.setText(myCalendar.getTime().toString().substring(11, 16));
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        pref = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("TODOLIST");
        object = (ArrayList<Information>) args.getSerializable("ARRAYLIST");

        create = (Button) findViewById(R.id.create);
        content = (EditText) findViewById(R.id.task);
        e1 = (EditText) findViewById(R.id.e1);
        e2 = (EditText) findViewById(R.id.e2);
        e1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(create.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });


        e2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(create.this, time,
                        myCalendar.get(Calendar.HOUR), myCalendar.get(Calendar.MINUTE),
                        true).show();
            }
        });

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEmpty()) {
                } else {
                    try {
                        JSONObject obj = new JSONObject();
                        obj.put("content", content.getText().toString());
                        obj.put("time", e2.getText().toString());
                        obj.put("date", e1.getText().toString());
//                        Toast.makeText(v.getContext(), ""+obj.toString(), Toast.LENGTH_SHORT).show();
                        int count = pref.getInt(PREF_COUNT_KEY, 0) + 1;

                        pref.edit().putString(PREF_DATA_KEY + count, obj.toString()).apply();
                        Log.d("App", "Event:" + pref.getString(PREF_DATA_KEY + count, "X"));
                        pref.edit().putInt(PREF_COUNT_KEY, count).apply();
                        startActivity(new Intent(getApplicationContext(), home.class));
                        finish();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    boolean isEmpty() {
        if (TextUtils.isEmpty(content.getText().toString())) {
            content.setError("Task cannot be empty!!");
            return true;
        } else
            return false;
    }
}
