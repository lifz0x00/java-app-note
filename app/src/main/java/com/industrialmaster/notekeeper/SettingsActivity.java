package com.industrialmaster.notekeeper;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        SharedPreferences welcome = getSharedPreferences("welcome", Context.MODE_PRIVATE);
        String securityStatus = welcome.getString("LOGIN_SECURITY", "");

        Switch securitySwitch = (Switch) findViewById(R.id.switch_security_status);

        if(securityStatus.equals("OFF")){
            securitySwitch.setChecked(true);
        }
        else {
            securitySwitch.setChecked(false);
        }
    }

    public void saveSettings(View view){
        Switch securitySwitch = findViewById(R.id.switch_security_status);

        SharedPreferences welcome = getSharedPreferences("welcome", Context.MODE_PRIVATE);
        SharedPreferences.Editor profileEditor = welcome.edit();

        if(securitySwitch.isChecked()){

            profileEditor.putString("LOGIN_SECURITY", "OFF");
            profileEditor.commit();
            Toast.makeText(this, "Login Otomatis Telah Dinyalakan", Toast.LENGTH_SHORT).show();
        }

        if(!securitySwitch.isChecked()) {

            profileEditor.putString("LOGIN_SECURITY", "ON");
            profileEditor.commit();
            Toast.makeText(this, "Login Otomatis Telah Dimatikan", Toast.LENGTH_SHORT).show();
        }

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
