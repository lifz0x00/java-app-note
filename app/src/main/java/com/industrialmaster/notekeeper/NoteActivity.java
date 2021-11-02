package com.industrialmaster.notekeeper;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class NoteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
    }

    public void saveNote(View view){
        NoteHelper dbNoteKeeper = new NoteHelper(this);

        SQLiteDatabase db = dbNoteKeeper.getWritableDatabase();

        EditText etNoteHeader = findViewById(R.id.et_note_header);
        EditText etNoteContent = findViewById(R.id.et_note_content);

        String noteHeader = etNoteHeader.getText().toString();
        String noteContent = etNoteContent.getText().toString();

        if(noteHeader.isEmpty() && noteContent.isEmpty()){
            Toast.makeText(this, "Kolom Tidak Boleh Kosong!", Toast.LENGTH_SHORT).show();
        }

        else if(noteHeader.isEmpty() && !noteContent.isEmpty()){
            Toast.makeText(this, "Tolong tambahkan Judul!", Toast.LENGTH_SHORT).show();
        }

        else {
            String sqlQuery = "INSERT INTO note (header, content) VALUES('" + noteHeader + "', '" + noteContent + "')";
            db.execSQL(sqlQuery);
            Toast.makeText(this, "Catatan Tersimpan!", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }

    }

    public void discardNote(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void saveUpdatedNote(View view){
        String noteId = getIntent().getStringExtra("noteId");

        EditText etNoteHeader = findViewById(R.id.et_edit_note_header);
        EditText etNoteContent = findViewById(R.id.et_edit_note_content);

        String noteHeader = etNoteHeader.getText().toString();
        String noteContent = etNoteContent.getText().toString();

        if(noteHeader.isEmpty() && noteContent.isEmpty()){
            Toast.makeText(this, "Kolom tidak boleh kosong!", Toast.LENGTH_SHORT).show();
        }

        else if(noteHeader.isEmpty() && !noteContent.isEmpty()){
            Toast.makeText(this, "Tolong tambahkan judul", Toast.LENGTH_SHORT).show();
        }

        else {
            NoteHelper dbNoteKeeper = new NoteHelper(this);
            SQLiteDatabase db = dbNoteKeeper.getWritableDatabase();

            SQLiteStatement statement = db.compileStatement("UPDATE note SET header = ?, content= ? WHERE _id = ? ");
            statement.bindString(1, noteHeader);
            statement.bindString(2, noteContent);
            statement.bindString(3, noteId);
            statement.executeUpdateDelete();

            Toast.makeText(this, "Catatan Telah Diubah", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
