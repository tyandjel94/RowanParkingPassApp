package com.example.android.rowanparkingpass.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import com.example.android.rowanparkingpass.R;

public class SettingActivity extends BaseActivity implements View.OnClickListener {

    Button changePassword;
    Button syncNow;
    Switch syncSwitch;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_settings);

        changePassword = (Button) findViewById(R.id.changePassButton);
        syncNow = (Button) findViewById(R.id.syncNowButton);
        syncSwitch = (Switch) findViewById(R.id.mySwitch);

        changePassword.setOnClickListener(this);
        syncNow.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent myIntent;

        switch (item.getItemId()) {
            // action with ID action_drivers was selected
            case R.id.action_home:
                Toast.makeText(this, "Home Selected", Toast.LENGTH_SHORT).show();
                myIntent = new Intent(this, HomePageActivity.class);
                myIntent.putExtra(MODE, mode.HOME_PAGE);
                startActivity(myIntent);
                break;
            default:
                break;
        }

        return true;
    }

    @Override
    public void onClick(View v) {
        // Change to new activity
        switch (v.getId()) {
            case R.id.changePassButton:
                Toast.makeText(this, "Change password was clicked", Toast.LENGTH_SHORT).show();
                Intent myIntent = new Intent(this, RowanWebPageActivity.class);
                myIntent.putExtra(MODE, RowanWebPageActivity.mode.CHANGE_PASSWORD.name());
                startActivity(myIntent);
                break;
            case R.id.syncNowButton:
                Toast.makeText(this, "Sync now was clicked", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
}