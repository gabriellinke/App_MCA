package com.example.tutorial;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteTableLockedException;
import android.os.Bundle;
import android.util.Log;

import helpers.DBHelper;

public class CursoresActivity extends AppCompatActivity {

    private Cursor mCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_cursores);
    }

    @Override
    protected void onResume() {
        super.onResume();

    criarRegistros();


    }

    public void criarRegistros()
    {

        DBHelper db = new DBHelper(getBaseContext());
        SQLiteDatabase banco = db.getWritableDatabase();

        ContentValues ctv;


        for(int i = 1; i<21; i++)
        {
            ctv = new ContentValues();

            ctv.put("consumo", 0.355*i+"L");
            ctv.put("data", Integer.toString(i)+"/03/2020");

            banco.insert("consumo", null, ctv);
        }


    }

    public void editarRegistros()
    {
        DBHelper db = new DBHelper(getBaseContext());
        SQLiteDatabase banco = db.getWritableDatabase();

        mCursor = banco.rawQuery("SELECT _id, consumo, data FROM consumo", null);
        ContentValues ctv;
        if(mCursor.moveToFirst())
        {
            //tem registros

            do{
                ctv = new ContentValues();
                ctv.put("data", mCursor.getString(mCursor.getColumnIndex("data"))+ " ALTERADA" );

                banco.update("consumo", ctv, "_id = "+ mCursor.getString(mCursor.getColumnIndex("_id")), null);
                Log.d("Cursor: ", mCursor.getString(2));

            } while(mCursor.moveToNext());
        }

        mCursor.close();
    }

    public void deletarRegistros()
    {
        DBHelper db = new DBHelper(getBaseContext());
        SQLiteDatabase banco = db.getWritableDatabase();

        mCursor = banco.rawQuery("SELECT _id, consumo, data FROM consumo", null);
        ContentValues ctv;

        if(mCursor.moveToFirst())
        {
            //tem registros

            do{

                banco.delete("consumo", "_id = "+ mCursor.getString(0), null);

                Log.d("Cursor: ", mCursor.getString(2));

            } while(mCursor.moveToNext());
        }
        else
            Log.d("Cursor: ", "SEM REGISTROS");


        mCursor.close();
    }
}
