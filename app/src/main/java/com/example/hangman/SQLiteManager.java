package com.example.hangman;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;


public class SQLiteManager extends SQLiteOpenHelper
{
    private static SQLiteManager sqLiteManager;

    private static final String DATABASE_NAME = "mots";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "mots";
    private static final String MOT_ID = "mot_id";

    private static final String MOT = "mot";
    private static final String DEFINITION = "definition";

    @SuppressLint("SimpleDateFormat")
    private static final DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");

    public SQLiteManager(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static SQLiteManager instanceOfDatabase(Context context)
    {
        if(sqLiteManager == null)
            sqLiteManager = new SQLiteManager(context);

        return sqLiteManager;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        StringBuilder sql;
        sql = new StringBuilder()
                .append("CREATE TABLE ")
                .append(TABLE_NAME)
                .append("(")
                .append(MOT_ID)
                .append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .append(MOT)
                .append(" INT, ")
                .append(DEFINITION)
                .append(" TEXT )");
        sqLiteDatabase.execSQL(sql.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion)
    {

    }

    public void peuplementDB(String mot, String def){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MOT,mot);
        contentValues.put(DEFINITION,def);
        sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
    }

    public void add_word(String mot, String def){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MOT,mot);
        contentValues.put(DEFINITION,def);
        sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
    }

    public void delete_all_words(){
        String selectQuery = "DELETE FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(selectQuery);
    }

    public void delete_word(String word){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME,MOT+"="+word,null);
    }

    public String[] get_mots(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor=db.query(TABLE_NAME,null,null,null,null,null,null,null);
        String[] mots= new String[cursor.getCount()];
        int i=0;
        while(cursor.moveToNext()){
            mots[i]=cursor.getString(1);
            i++;
        }
        return mots;
    }

    public String[] get_definitions(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor=db.query(TABLE_NAME,null,null,null,null,null,null,null);
        String[] definitions= new String[cursor.getCount()];
        int i=0;
        while(cursor.moveToNext()){
            definitions[i]=cursor.getString(2);
            i++;
        }
        return definitions;
    }


}