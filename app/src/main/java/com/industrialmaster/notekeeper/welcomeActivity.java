package com.industrialmaster.notekeeper;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class welcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
    }

    public void saveProfile(View view){
        // Create shared preferences set called welcome
        SharedPreferences profile = getSharedPreferences("welcome", Context.MODE_PRIVATE);
        SharedPreferences.Editor profileEditor = profile.edit();

        // Read values from form
        EditText etWelcomeFirstName = findViewById(R.id.et_welcome_first_name);
        EditText etWelcomeLastName = findViewById(R.id.et_welcome_last_name);
        EditText etWelcomeEmail = findViewById(R.id.et_welcome_email);
        EditText etWelcomePassword = findViewById(R.id.et_welcome_password);
        EditText etWelcomeConfirmedPassword = findViewById(R.id.et_welcome_confirmed_password);

        String firstName = etWelcomeFirstName.getText().toString();
        String lastName = etWelcomeLastName.getText().toString();
        String email = etWelcomeEmail.getText().toString();
        String password = etWelcomePassword.getText().toString();
        String confirmedPassword = etWelcomeConfirmedPassword.getText().toString();

        // Check for empty fields
        if(firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty() || password.isEmpty() ||confirmedPassword.isEmpty()){
            Snackbar.make(view, "Tolong isi semua kolom!", Snackbar.LENGTH_LONG).setAction("Action", null).show();
        }

        else {
            // Check for password validations
            if(password.equals(confirmedPassword)){
                // Add values to sharedPreferences set
                profileEditor.putString("FIRST_NAME", etWelcomeFirstName.getText().toString());
                profileEditor.putString("LAST_NAME", etWelcomeLastName.getText().toString());
                profileEditor.putString("EMAIL", etWelcomeEmail.getText().toString());
                profileEditor.putString("PASSWORD", etWelcomePassword.getText().toString());
                profileEditor.putString("LOGIN_SECURITY", "ON");

                profileEditor.commit();

                Toast.makeText(this, "Data Tersimpan!", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
            }

            else {
                Snackbar.make(view, "Kata Sandi Tidak Sesuai!", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        }

    }
}
