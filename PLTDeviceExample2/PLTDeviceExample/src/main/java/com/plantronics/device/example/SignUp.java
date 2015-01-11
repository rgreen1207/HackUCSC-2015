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

public class SignUp extends Activity implements OnClickListener{

    private static final String TAG = "com.plantronics.device.example.MainActivity";

    private Context _context;
    private Device _device;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        _context = this;

        // Set up a click listener for the Log In button.
        View loginButton = findViewById(R.id.login_button);
        loginButton.setOnClickListener(this);

        // Set up a click listener for the New User button.
        View NewUserButton = findViewById(R.id.new_user_button);
        NewUserButton.setOnClickListener(this);
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
        if(v.getId() == R.id.login_button)
        {
            Intent i = new Intent(this, afterlogin.class);
            startActivity(i);
        }
        else if(v.getId() == R.id.signup_button_activity_main)
        {
            Intent i = new Intent(this, SignUp.class);
            startActivity(i);
        }
    }
}