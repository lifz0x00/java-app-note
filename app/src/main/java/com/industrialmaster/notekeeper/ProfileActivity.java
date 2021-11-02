package com.industrialmaster.notekeeper;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Get shared preferences file
        SharedPreferences welcome = getSharedPreferences("welcome", Context.MODE_PRIVATE);

        // Get values from shared preferences
        String storedFirstName = welcome.getString("FIRST_NAME", "");
        String storedLastName = welcome.getString("LAST_NAME", "");
        String storedEmail = welcome.getString("EMAIL", "");
        String storedPassword = welcome.getString("PASSWORD", "");

        // Create reference to form fields
        EditText etProfileFirstName = findViewById(R.id.profile_first_name);
        EditText etProfileLastName = findViewById(R.id.profile_last_name);
        EditText etProfileEmail = findViewById(R.id.profile_email);
        EditText etProfilePassword = findViewById(R.id.profile_password);
        EditText etProfileConfirmedPassword = findViewById(R.id.profile_confirmed_password);

        // Set existing values to edit text fields
        etProfileFirstName.setText(storedFirstName);
        etProfileLastName.setText(storedLastName);
        etProfileEmail.setText(storedEmail);
        etProfilePassword.setText(storedPassword);
        etProfileConfirmedPassword.setText(storedPassword);
    }

    public void update(View view){
        SharedPreferences welcome = getSharedPreferences("welcome", Context.MODE_PRIVATE);
        SharedPreferences.Editor profileEditor = welcome.edit();

        EditText etProfileFirstName = findViewById(R.id.profile_first_name);
        EditText etProfileLastName = findViewById(R.id.profile_last_name);
        EditText etProfileEmail = findViewById(R.id.profile_email);
        EditText etProfilePassword = findViewById(R.id.profile_password);
        EditText etProfileConfirmedPassword = findViewById(R.id.profile_confirmed_password);

        String firstName = etProfileFirstName.getText().toString();
        String lastName = etProfileLastName.getText().toString();
        String email = etProfileEmail.getText().toString();
        String password = etProfilePassword.getText().toString();
        String confirmedPassword = etProfileConfirmedPassword.getText().toString();

        // Check for empty fields
        if(firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty() || password.isEmpty() ||confirmedPassword.isEmpty()){
            Snackbar.make(view, "Tolong isi semua kolom!", Snackbar.LENGTH_LONG).setAction("Action", null).show();
        }
        else {
            // Check for password validations
            if (password.equals(confirmedPassword)) {
                // Add values to sharedPreferences set
                profileEditor.putString("FIRST_NAME", etProfileFirstName.getText().toString());
                profileEditor.putString("LAST_NAME", etProfileLastName.getText().toString());
                profileEditor.putString("EMAIL", etProfileEmail.getText().toString());
                profileEditor.putString("PASSWORD", etProfilePassword.getText().toString());

                profileEditor.commit();

                Toast.makeText(this, "Profil Telah Diubah", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            } else {
                Snackbar.make(view, "Kata Sandi Tidak Sesuai!", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
