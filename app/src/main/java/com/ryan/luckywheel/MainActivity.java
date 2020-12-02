package com.ryan.luckywheel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import rubikstudio.library.model.LuckyItem;
import rubikstudio.library.model.Wheel;

public class MainActivity extends AppCompatActivity {
    ArrayList<Wheel> listWheel;
    WheelAdapter wheelAdapter;
    ListView listView;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String TEXT = "text";
    public static final String SWITCH1 = "switch1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);
        setTitle("Spin The Wheel-Random Picker");
        listWheel = new ArrayList<>();
        loadData();

        wheelAdapter = new WheelAdapter(listWheel);
        listView = findViewById(R.id.listViewWheel);
        listView.setAdapter(wheelAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Object o = listView.getItemAtPosition(position);
                Wheel wheel = (Wheel) o;
                Intent playWheel=new Intent(getApplicationContext(),PlayWheels.class);
                Bundle bd=new Bundle();
                bd.putString("key",wheel.getTitle());
                playWheel.putExtras(bd);
                startActivity(playWheel);
            }
        });

        final Button btnCreateWheel=(Button)findViewById(R.id.btnCreateWheel);
        btnCreateWheel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent createWheelsIntent = new Intent(getApplicationContext(), CreateWheel.class);
                startActivity(createWheelsIntent);
            }
        });

    }
    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("listwheel", null);
        Type type = new TypeToken<ArrayList<Wheel>>() {}.getType();
        listWheel = gson.fromJson(json, type);
        if (listWheel == null) {
            listWheel = new ArrayList<>();
        }
    }
}