package com.example.markg.androidlabs;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener.*;
import android.widget.Toast;

public class StartActivity extends Activity {

    protected static final String ACTIVITY_NAME = "StartActivity";
    //final Button button = (Button) findViewById(R.id.button);
    Button button;
    Button startChatButton;
    Button startWeatherButton;
    Button startTB;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Log.i(ACTIVITY_NAME, "In onCreate: ");

        startTB = (Button) findViewById(R.id.startTB);
        startTB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartActivity.this, TestToolbar.class);
                startActivity(intent);
            }
        });

        button = (Button) findViewById(R.id.button11);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent getResult = new Intent(StartActivity.this,ListItemsActivity.class);
                startActivityForResult(getResult,10);

            }
        });

        startChatButton = (Button) findViewById(R.id.startChatButton);
        startChatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(ACTIVITY_NAME, "user clicked start chat ");
                Intent getResult = new Intent(StartActivity.this, ChatWindow.class);
                startActivity(getResult);
            }
        });

        startWeatherButton = (Button) findViewById(R.id.startWeatherButton);
        startWeatherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(ACTIVITY_NAME, "user clicked start weather ");
                Intent getResult = new Intent(StartActivity.this, WeatherForcast.class);
                startActivity(getResult);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int responseCode, Intent data){
        if (requestCode==10){
            Log.i(ACTIVITY_NAME, "Returned to StartActivity.onActivityResult: ");
        }
        if (responseCode==Activity.RESULT_OK){
            String messagePassed = (String) data.getStringExtra("Response");
            Toast toast = Toast.makeText(StartActivity.this, (CharSequence) messagePassed, Toast.LENGTH_LONG);
            toast.show();
        }
    }

    protected void onResume() {
        super.onResume();
        Log.i(ACTIVITY_NAME,"In onResume: ");
    }

    protected void onStart() {
        super.onStart();
        Log.i(ACTIVITY_NAME,"In onStart: ");
    }

    protected void onPause() {
        super.onPause();
        Log.i(ACTIVITY_NAME,"In onPause: ");
    }

    protected void onStop() {
        super.onStop();
        Log.i(ACTIVITY_NAME,"In onStop: ");
    }

    protected void onDestroy() {
        super.onDestroy();
        Log.i(ACTIVITY_NAME,"In onDestroy: ");
    }






}
