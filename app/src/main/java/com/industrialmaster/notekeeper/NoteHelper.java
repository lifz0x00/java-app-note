package com.industrialmaster.notekeeper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class NoteHelper extends SQLiteOpenHelper {

    public NoteHelper(Context context) {  //intent eken pass wela ena intent eke thiyenne context eka
        super(context,"dbNoteKeeper",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String tableQuery1 = "CREATE TABLE note (_id INTEGER PRIMARY KEY AUTOINCREMENT, header TEXT, content TEXT, date DATETIME DEFAULT CURRENT_DATE)";
        db.execSQL(tableQuery1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
