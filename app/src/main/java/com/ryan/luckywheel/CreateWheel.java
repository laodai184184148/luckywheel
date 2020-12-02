package com.ryan.luckywheel;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;

import rubikstudio.library.model.LuckyItem;
import rubikstudio.library.model.Wheel;

public class CreateWheel extends AppCompatActivity  implements ExampleDialog.ExampleDialogListener {

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String TEXT = "text";
    public static final String SWITCH1 = "switch1";

    ArrayList<Wheel> listWheel;
    ArrayList<LuckyItem> listItem;
    LuckyItemAdapter luckyItemAdapter;
    ListView listView;
    LuckyItem item;
    private SeekBar spinTime;
    private TextView txtSpinTime;
    private EditText txtWheelTitle;
    @SuppressLint("ResourceType")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.layout.toolbar, menu);
        setTitle("Create Wheel");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        return true;
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Spin:
                if (txtWheelTitle.getText().toString().isEmpty() || listItem.size() < 2) {
                    final AlertDialog ad = new AlertDialog.Builder(this).create();

                    String titleText = "WARNING";

                    // Initialize a new foreground color span instance
                    ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.RED);

                    // Initialize a new spannable string builder instance
                    SpannableStringBuilder ssBuilder = new SpannableStringBuilder(titleText);

                    // Apply the text color span
                    ssBuilder.setSpan(
                            foregroundColorSpan,
                            0,
                            titleText.length(),
                            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                    );

                    // Set the alert dialog title using spannable string builder
                    ad.setTitle(ssBuilder);
                    ad.setMessage("Your Wheel title is Empty or Your number of Lucky Item are less than 2");
                    ad.show();
                } else {
                    //handle clicked menuItem
                    saveDataListItem(txtWheelTitle.getText().toString());
                    Intent playWheel = new Intent(getApplicationContext(), PlayWheels.class);
                    Bundle bd = new Bundle();
                    bd.putString("key", txtWheelTitle.getText().toString());
                    playWheel.putExtras(bd);
                    startActivity(playWheel);

                }
                break;
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_test);
        txtWheelTitle=(EditText) findViewById(R.id.txtTitle);
        txtSpinTime=(TextView)findViewById(R.id.txtSpinTime);
        spinTime=(SeekBar) findViewById(R.id.sbSpinTime);

        spinTime.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int pval = 0;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                pval = progress;
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //write custom code to on start progress
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                txtSpinTime.setText(pval + "");
            }
        });

        listItem = new ArrayList<>();

        luckyItemAdapter = new LuckyItemAdapter(listItem);
        listView = findViewById(R.id.listviewItem);
        listView.setAdapter(luckyItemAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                Object o = listView.getItemAtPosition(position);
                LuckyItem item = (LuckyItem) o;
                ExampleDialog exampleDialog = ExampleDialog.newInstance(item.getTopText(),item.getColor());
                exampleDialog.show(getSupportFragmentManager(), "DialogFragmentWithSetter");


            }
        });

        LayoutInflater inflater = getLayoutInflater();
        final View alertLayout = inflater.inflate(R.layout.item_create_dialog, null);
        findViewById(R.id.btnAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();


            }


        });

    }
    public void openDialog() {
        ExampleDialog exampleDialog = new ExampleDialog();
        exampleDialog.show(getSupportFragmentManager(), "example dialog");
    }
    public void saveDataListItem(String key) {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Gson gson = new Gson();
        String json = gson.toJson(listItem);
        editor.putString(key, json);

        loadData();
        listWheel.add(new Wheel("99",key,listItem,spinTime.getProgress()));
        Gson wheelgson = new Gson();
        String wheeljson = wheelgson.toJson(listWheel);
        editor.putString("listwheel", wheeljson);
        editor.apply();


        //Toast.makeText(this, "Data saved", Toast.LENGTH_SHORT).show();
    }
    public void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("listwheel", null);
        Type type = new TypeToken<ArrayList<Wheel>>() {}.getType();
        listWheel = gson.fromJson(json, type);
        if (listWheel == null) {
            listWheel = new ArrayList<>();
        }
    }


    @Override
    public void applyTexts(String title,Bitmap icon, int color,String selectTitle) {
        item=new LuckyItem(title,icon,color);
        if (selectTitle =="") {

            listItem.add(item);
        }
        else{
            for (int i = 0; i < listItem.size(); i++) {
                if (listItem.get(i).getTopText()==selectTitle){
                    listItem.set(i,item);
                };
            }
        }
        luckyItemAdapter.notifyDataSetChanged();
    }
}
