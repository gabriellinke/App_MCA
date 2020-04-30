package com.example.MCA;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import helpers.DBHelper;

public class CursoresActivity extends AppCompatActivity
{

    private Cursor mCursor;
    private int id;
    private String data;
    private int consumoAtual;

    @Override
    protected void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    @Override
    protected void onResume() {
        super.onResume();

        Intent intent = getIntent();
        Bundle dados = intent.getExtras();  //RECUPERA DADOS DA ACTIVITY ANTERIOR

        assert dados != null;
        id = dados.getInt("id");
        consumoAtual = dados.getInt("consumo");
        data = dados.getString("data");

        switch (id) //REALIZA ALGUMA AÇÃO BASEADA NO ID RECEBIDO
        {
            case 1:
                criarRegistros();
                break;
            case 2:
                //editarRegistros();
                break;
            case 3:
                deletarRegistros();
                break;
        }

    }

    public void criarRegistros() {

        DBHelper db = new DBHelper(getBaseContext());
        SQLiteDatabase banco = db.getWritableDatabase();    //CRIA UM BANCO

        ContentValues ctv = new ContentValues();

        ctv.put("consumo", consumoAtual/1000.0f + "L");     //ARMAZENA VALORES EM UM CONTENTVALUES
        ctv.put("data", data);

        banco.insert("consumo", null, ctv); //BOTA OS DADOS NA TABELA consumo

        finish(); //FINALIZA A ACTIVITY
    }

/*   SEM UTILIDADE POR ENQUANTO
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
        finish();
    }
*/
    public void deletarRegistros()
    {
        DBHelper db = new DBHelper(getBaseContext());       //CRIA UM BANCO
        SQLiteDatabase banco = db.getWritableDatabase();

        mCursor = banco.rawQuery("SELECT _id, consumo, data FROM consumo", null);
        ContentValues ctv;

        //DELETA OS REGISTROS
        if(mCursor.moveToFirst())
        {
            do{

                banco.delete("consumo", "_id = "+ mCursor.getString(0), null);

                Log.d("Cursor: ", mCursor.getString(2));

            } while(mCursor.moveToNext());
        }
        else
            Log.d("Cursor: ", "SEM REGISTROS");


        mCursor.close();
        finish(); //FINALIZA A ACTIVITY
    }
}
