package com.example.markg.androidlabs;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

//import static android.R.attr.data;

public class ListItemsActivity extends Activity {

    protected static final String ACTIVITY_NAME = "ListItemsActivity";
    private static final int REQUEST_IMAGE_CAPTURE = 1 ;
    //final
    ImageButton camera;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_list_items);
        Log.i(ACTIVITY_NAME, "In onCreate: ");

        camera = (ImageButton) findViewById(R.id.camera);
        Switch sw = (Switch) findViewById(R.id.switch1);
        //final Switch switch1 = (Switch) findViewByid(R.id.switch1);
        CheckBox cb = (CheckBox) findViewById(R.id.cb);


        //final int REQUEST_IMAGE_CAPTURE = 1;

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {

                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
            }
        });

        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                CharSequence text = "Switch is On";// "Switch is Off"
                CharSequence text2 = "Switch is Off";// "Switch is on"
                int duration = Toast.LENGTH_SHORT; //= Toast.LENGTH_LONG if Off

                if (isChecked==false) {
                    Toast toast = Toast.makeText(ListItemsActivity.this, text, duration); //this is the ListActivity
                    toast.show(); //display your message box\
                }
                else{
                    Toast toast = Toast.makeText(ListItemsActivity.this, text2, Toast.LENGTH_LONG); //this is the ListActivity
                    toast.show();
                }

            }
        });

        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ListItemsActivity.this);
                // 2. Chain together various setter methods to set the dialog characteristics
                builder.setMessage(R.string.dialog_message) //Add a dialog message to strings.xml

                        .setTitle(R.string.dialog_title)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User clicked OK button
                                Intent resultIntent = new Intent(  );
                                resultIntent.putExtra("Response", "Here is my response");
                                setResult(Activity.RESULT_OK, resultIntent);
                                finish();
                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog

                            }
                        })
                        .show();

            }
        });


    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            camera.setImageBitmap(imageBitmap);
        }
    }

    //protected void




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
