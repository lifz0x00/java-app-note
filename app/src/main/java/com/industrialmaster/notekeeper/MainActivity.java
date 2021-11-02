package com.industrialmaster.notekeeper;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences welcome = getSharedPreferences("welcome", Context.MODE_PRIVATE);

        boolean loginStatus = LoginActivity.getLoginStatus();

        if(welcome.contains("FIRST_NAME")){

            if(welcome.getString("LOGIN_SECURITY", "").equals("ON") && loginStatus != true){

                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
            }

            else if((welcome.getString("LOGIN_SECURITY", "").equals("ON") && loginStatus == true) || welcome.getString("LOGIN_SECURITY", "").equals("OFF")){
                setContentView(R.layout.activity_main);

                Toolbar toolbar = findViewById(R.id.toolbar);
                setSupportActionBar(toolbar);
                FloatingActionButton fab = findViewById(R.id.fab);
                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(MainActivity.this, NoteActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });

                DrawerLayout drawer = findViewById(R.id.drawer_layout);
                NavigationView navigationView = findViewById(R.id.nav_view);

                View headerView = navigationView.getHeaderView(0);
                TextView navUsername = headerView.findViewById(R.id.tv_navigation_name);
                navUsername.setText(welcome.getString("EMAIL", ""));

                ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
                drawer.addDrawerListener(toggle);
                toggle.syncState();
                navigationView.setNavigationItemSelectedListener(this);
            }
        }

        else {
                Intent intent = new Intent(this, welcomeActivity.class);
                startActivity(intent);
                finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        ListView listView = findViewById(R.id.note_list);

        NoteHelper dbNoteKeeper = new NoteHelper(this);
        SQLiteDatabase db = dbNoteKeeper.getReadableDatabase();

        String countQuery = "SELECT COUNT(*) FROM note";
        Cursor countCursor = db.rawQuery(countQuery, null);
        countCursor.moveToFirst();
        int noteCount = countCursor.getInt(0);

        // check whether note count is empty or not
        if(noteCount > 0){
            String sql = "SELECT * from note";
            Cursor cursor = db.rawQuery(sql, null);

            int layout = R.layout.home_card_view;

            String[] cols = { "_id", "header", "content", "date"};
            int[] views = { R.id.tv_note_id, R.id.tv_note_header, R.id.tv_note_content, R.id.tv_note_date};

            SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(this, layout, cursor, cols, views);

            listView.setAdapter(simpleCursorAdapter);
        }

        else {

            ImageView imageView = findViewById(R.id.empty_screen);
            imageView.setBackgroundResource(R.drawable.emptyscreen);

            Toast.makeText(this, "Ketuk ikon + untuk menambahkan catatan", Toast.LENGTH_SHORT).show();
        }



    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Tolong klik lagi untuk keluar", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce=false;
                }
            }, 2000);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        SharedPreferences welcome = getSharedPreferences("welcome", Context.MODE_PRIVATE);

        if(welcome.getString("LOGIN_SECURITY", "").equals("ON")){
            //noinspection SimplifiableIfStatement
            if (id == R.id.nav_logout) {
                LoginActivity.setLoginStatus(false);
                finish();
            }
            else {
                findViewById(R.id.nav_logout).setEnabled(false);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        View view = findViewById(id);

        if (id == R.id.nav_home) {
            showHome(view);
        } else if (id == R.id.nav_profile) {
            showProfile(view);
        } else if (id == R.id.nav_settings) {
            showSettings(view);
        } else if (id == R.id.nav_about) {
            showAbout(view);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void showHome(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void showProfile(View view){
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
        finish();
    }

    public void showSettings(View view){
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
        finish();
    }

    public void showAbout(View view){
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
        finish();
    }

    public void editNote(View view){
        LinearLayout parent = (LinearLayout) view.getParent();
        TextView textView = parent.findViewById(R.id.tv_note_id);
        String noteId = textView.getText().toString();

        Intent intent = new Intent(this, NoteEditActivity.class);
        intent.putExtra("noteId", noteId);
        startActivity(intent);
        finish();
    }

    public void deleteNote(View view) {
        LinearLayout parent = (LinearLayout) view.getParent();
        TextView textView = parent.findViewById(R.id.tv_note_id);
        String noteId = textView.getText().toString();

        NoteHelper dbNoteKeeper = new NoteHelper(this);
        SQLiteDatabase db = dbNoteKeeper.getWritableDatabase();

        SQLiteStatement statement = db.compileStatement("DELETE FROM note WHERE _id = ? ");
        statement.bindString(1, noteId);
        statement.executeUpdateDelete();

        finish();
        startActivity(getIntent());
        Toast.makeText(this, "Catatan Dihapus", Toast.LENGTH_SHORT).show();
    }
}
