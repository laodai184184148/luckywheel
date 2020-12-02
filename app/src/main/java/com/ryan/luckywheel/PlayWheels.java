package com.ryan.luckywheel;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import rubikstudio.library.LuckyWheelView;
import rubikstudio.library.model.LuckyItem;
import rubikstudio.library.model.Wheel;

public class PlayWheels extends AppCompatActivity {
    ArrayList<Wheel> listWheel;
    List<LuckyItem> data = new ArrayList<>();
    String key;
    int round;
    public static final String SHARED_PREFS = "sharedPrefs";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        setTitle("Play Wheel");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final LuckyWheelView luckyWheelView = (LuckyWheelView) findViewById(R.id.luckyWheel);


        Bundle bundle=getIntent().getExtras();
        key =bundle.getString("key");
        loadData();
        loadWheel();
        luckyWheelView.setData(data);
        luckyWheelView.setRound(5);

        findViewById(R.id.play).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int index = getRandomIndex();
                luckyWheelView.startLuckyWheelWithTargetIndex(index);
            }
        });

        luckyWheelView.setLuckyRoundItemSelectedListener(new LuckyWheelView.LuckyRoundItemSelectedListener() {
            @Override
            public void LuckyRoundItemSelected(int index) {
                Toast toast= Toast.makeText(getApplicationContext(),
                        data.get(index).topText, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 300);
                toast.show();

            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private int getRandomIndex() {
        Random rand = new Random();
        return rand.nextInt(data.size() - 1) + 0;
    }

    private int getRandomRound() {
        Random rand = new Random();
        return rand.nextInt(10) + 15;
    }
    private void loadWheel() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("listwheel", null);
        Type type = new TypeToken<ArrayList<Wheel>>() {}.getType();
        listWheel = gson.fromJson(json, type);
        if (listWheel == null) {
            listWheel = new ArrayList<>();

        }
        for (int i = 0; i < listWheel.size(); i++) {
            if (listWheel.get(i).getTitle()==key) {
                round = listWheel.get(i).getRound();
            }

        }
    }
    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(key, null);
        Type type = new TypeToken<ArrayList<LuckyItem>>() {}.getType();
        data = gson.fromJson(json, type);
        if (data == null) {
            data = new ArrayList<>();
        }

    }
}