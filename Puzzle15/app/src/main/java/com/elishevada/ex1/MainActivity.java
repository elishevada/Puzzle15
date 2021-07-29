package com.elishevada.ex1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    private SharedPreferences sp;
    private Button btnPlay;
    private Switch switch1;
    private String strmusic;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        switch1=(Switch)findViewById(R.id.switch1id);
        //get a pointer to the file
        sp = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String state = sp.getString("status", null);
//in case we clicked the switch last time
        if (state.equals("on")) {
            switch1.setChecked(true);
        }


        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    strmusic="on";
                    // get a pointer to MyPref.xml Shared Preferences file & update counter value
                    sp = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("status", strmusic);
                    editor.commit();
                }
                else
                     strmusic="off";
                // get a pointer to MyPref.xml Shared Preferences file & update counter value
                sp = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("status", strmusic);
                editor.commit();
            }
        });


        // Get UI reference from the Layout XML file
        btnPlay = findViewById(R.id.btnPlayid);
        // Set Events Listeners
        btnPlay.setOnClickListener(this);
        btnPlay.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Log.d("mylog",">>> btnplay Clicked");
                jumpToSecondActivity(v);
            }
        });

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        super.onCreateOptionsMenu(menu);

        // Set 3 Dots Menu on activity action bar
        MenuItem aboutMenu = menu.add("About");
        MenuItem exitMenu = menu.add("Exit");

        exitMenu.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener()
        {
            @Override
            public boolean onMenuItemClick(MenuItem item)
            {
                Log.d("mylog",">>> Exit Menu Clicked");
                showExitDialog();
                return true;
            }
        });


        aboutMenu.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener()
        {
            @Override
            public boolean onMenuItemClick(MenuItem item)
            {
                Log.d("mylog",">>> About Menu Clicked");
                showAboutDialog();
                return true;
            }
        });
        return true;
    }

    // Show AlertDialog to about app
    private void showAboutDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
        dialog.setIcon(R.drawable.ic_about);
        dialog.setTitle("About App");
        dialog.setMessage("Puzzle 15 (com.elishevada.ex1)" + "\n\n" + "By Elisheva Dayan, 07/04/2021.");
        dialog.setCancelable(false);
        dialog.setNegativeButton("ok", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                Log.d("mylog",">>> ok, got it!");
                dialog.dismiss();  // close the dialog
            }
        });
        dialog.show();

    }

    // Show AlertDialog to exit app
    private void showExitDialog()
    {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setIcon(R.drawable.ic_exit);
        dialog.setTitle("Exit App");
        dialog.setMessage("Do you really want to exit?");
        dialog.setCancelable(false);

        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                Log.d("mylog",">>> Yes, Exit APP!");
                finish(); // exit & close this Activity
            }
        });

        dialog.setNegativeButton("No", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                Log.d("mylog",">>> No, dont Exit APP!");
                dialog.dismiss();  // close the dialog
            }
        });

        dialog.show();
    }

    // Jump to Second Activity using Intent & startActivity
    public void jumpToSecondActivity(View view)
    {
        Intent intent = new Intent(MainActivity.this, GameActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
    }


    @Override
    protected void onStart()
    {
        super.onStart();
        Log.d("mylog", ">> onStart()MainActivity");
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        Log.d("mylog", ">> onResume()MainActivity");
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        Log.d("mylog", ">> onPause()MainActivity");
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        Log.d("mylog", ">> onStop()MainActivity");
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        Log.d("mylog", ">> onDestroy()");
    }

}




