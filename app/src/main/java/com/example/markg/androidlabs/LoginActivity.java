package com.example.markg.androidlabs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.*;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver.OnDrawListener;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class LoginActivity extends Activity {

    protected static final String ACTIVITY_NAME = "LoginActivity";
    //Intent intent = new Intent(LoginActivity.this, LoginActivity.class);
    //final Button button1 = (Button) findViewById(R.id.button1);


    //String defaultEmail = sharedPref.getString("Login", "email@domain.com");


    EditText email;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.i(ACTIVITY_NAME, "In onCreate: ");

        final SharedPreferences sharedPref = getSharedPreferences("Login",Context.MODE_PRIVATE);
        Button button1 = (Button) findViewById(R.id.button1);
        email = (EditText) findViewById(R.id.emailField);
        email.setText(sharedPref.getString("Login","email@domain.com"));

        button1.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                String useremail = email.getText().toString();
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("Login",email.toString());
                editor.commit();

                Intent intent = new Intent(LoginActivity.this, StartActivity.class);
                startActivity(intent);
            }
        });

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
