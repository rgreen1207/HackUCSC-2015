package com.plantronics.device.example;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

import com.plantronics.device.Device;

public class AfterLogin extends Activity implements OnClickListener{

    private static final String TAG = "com.plantronics.device.example.MainActivity";

    private Context _context;
    private Device _device;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.afterlogin);

        _context = this;

        // Set up a click listener for the setup button.
        View setupButton = findViewById(R.id.setup_button);
        setupButton.setOnClickListener(this);

        // Set up a click listener for the testing button.
        View testingButton = findViewById(R.id.testing_button);
        testingButton.setOnClickListener(this);

        // Set up a click listener for the settings button.
        View settingsButton = findViewById(R.id.settings_button);
        settingsButton.setOnClickListener(this);

        // Set up a click listener for the debug button.
        View debugButton = findViewById(R.id.debug_button);
        debugButton.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_activity2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void onClick(View v) {
        if(v.getId() == R.id.setup_button)
        {
            Intent i = new Intent(this, LogIn.class);
            startActivity(i);
        }
        else if(v.getId() == R.id.testing_button)
        {
            Intent i = new Intent(this, Testing.class);
            startActivity(i);
        }
        else if(v.getId() == R.id.settings_button)
        {
            Intent i = new Intent(this, Settings.class);
            startActivity(i);
        }
        else if(v.getId() == R.id.debug_button)
        {
            Intent i = new Intent(this, Debug.class);
            startActivity(i);
        }
    }
}