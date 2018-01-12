package com.example.markg.androidlabs;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import android.widget.Toast;



public class TestToolbar extends AppCompatActivity {
    String x;
    public View dialgoview ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_toolbar);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);


        x=new String("No Message added");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, x, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });




    }

    @Override
    public boolean onCreateOptionsMenu(Menu m){
        getMenuInflater().inflate(R.menu.toolbar_menu, m);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem mi){
        switch (mi.getItemId()){
            case R.id.action1:
                Log.i("Toolbar", "onOptionsItemSelected: Option 1 Selected");
                Snackbar.make(findViewById(R.id.action1), x, Snackbar.LENGTH_LONG).setAction("Action", null).show();
                Toast.makeText(this, "Add Selected", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action2:
                Toast.makeText(this, "Delete Selected", Toast.LENGTH_SHORT).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(TestToolbar.this);
                builder.setTitle("Warning");
                // Add the buttons
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK button
                        Intent intent = new Intent(TestToolbar.this,StartActivity.class);
                        startActivityForResult(intent, 0);
                    }
                });
                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
                // Create the AlertDialog
                AlertDialog dialog = builder.create();
                dialog.show();

                break;
            case R.id.action3:
                Toast.makeText(this, "Message Selected", Toast.LENGTH_SHORT).show();
                AlertDialog.Builder builder2 = new AlertDialog.Builder(TestToolbar.this);
                // Get the layout inflater
                LayoutInflater inflater = getLayoutInflater();
                // Inflate and set the layout for the dialog
                // Pass null as the parent view because its going in the dialog layout
                dialgoview =  inflater.inflate(R.layout.alert_dialog_layout, null) ;
                builder2.setView (dialgoview)
                        // Add action buttons
                        .setPositiveButton("Set", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                try {
                                    EditText mesg = dialgoview.findViewById(R.id.msg) ; //findViewById(R.id.msg);
                                    x = mesg.getText().toString();
                                    dialog.dismiss();
                                    Toast.makeText(TestToolbar.this, x + "Text Set", Toast.LENGTH_SHORT).show();
                                }catch (Exception e){
                                    Toast.makeText(TestToolbar.this, "ERRORRRRRRRR", Toast.LENGTH_LONG).show();
                                }
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        });
                AlertDialog dialog2 = builder2.create();
                dialog2.show();
                break;
            case R.id.about:
                Toast.makeText(this, "Version 1 by Mark Girgis", Toast.LENGTH_LONG).show();
                break;
        }
        return true;
    }

}
