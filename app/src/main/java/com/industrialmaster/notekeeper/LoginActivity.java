package com.industrialmaster.notekeeper;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    public static boolean loginStatus;

    public static boolean getLoginStatus(){
        return loginStatus;
    }

    public static void setLoginStatus(boolean status){
        loginStatus = status;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SharedPreferences welcome = getSharedPreferences("welcome", Context.MODE_PRIVATE);
        String firstName = welcome.getString("FIRST_NAME", "");
        String password = welcome.getString("PASSWORD", "");

        TextView welcomeNote = findViewById(R.id.welcome_note);
        welcomeNote.setText("Hello " + firstName + ", Selamat Datang!");
    }


    public void login(View view) {

        SharedPreferences welcome = getSharedPreferences("welcome", Context.MODE_PRIVATE);
        String password = welcome.getString("PASSWORD", "");

        EditText etPassword = findViewById(R.id.login_password);
        String enteredPassword = etPassword.getText().toString();

        if (enteredPassword.equals(password)) {
            setLoginStatus(true);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            Snackbar.make(view, "Otentikasi Gagal, Coba Lagi!", Snackbar.LENGTH_LONG).setAction("Action", null).show();
        }
    }

}
