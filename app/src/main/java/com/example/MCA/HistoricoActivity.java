package com.example.MCA;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import helpers.DBHelper;

public class HistoricoActivity extends AppCompatActivity {

    private Cursor mCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_historico);
    }

    @Override
    protected void onResume() {
        super.onResume();

        DBHelper db = new DBHelper(getBaseContext());
        SQLiteDatabase banco = db.getWritableDatabase();

        //CURSOR PARA PERCORRER O BANCO DE DADOS
        mCursor = banco.rawQuery("SELECT _id, consumo, data FROM consumo", null);

        //PEGA OS DADOS DA COLUNA DE CONSUMO E DE DATA
        String[] from = {
           "consumo",
           "data"
        };

        //COLOCA OS DADOS NAS TEXTVIEWS
        int[] to = {
            R.id.txvConsumo,
            R.id.txvData
        };

        //CRIA O ADAPTER COM OS VETORES from E to INSTANCIADOS ANTERIORMENTE
        MeuAdapter adapter = new MeuAdapter(getBaseContext(), R.layout.lista_consumo, mCursor, from ,to ,0);

        //COLOCA OS DADOS DO ADAPTER NA LISTVIEW QUE MOSTRA O HISTÓRICO
        ListView ltvConsumo = (ListView)findViewById(R.id.ltvConsumo);
        ltvConsumo.setAdapter(adapter);
    }

    @Override
    protected void onPause() {
        super.onPause();

        //FECHA O CURSOR
        mCursor.close();
    }

    //ADAPTER CUSTOMIZADO PARA MOSTRAR OS DADOS EM VERDE SE O CONSUMO DO DIA FOR MAIOR QUE 2L E EM VERMELHO CASO O CONTRÁRIO
    public class MeuAdapter extends SimpleCursorAdapter {

        public MeuAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
            super(context, layout, c, from, to, flags);
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            super.bindView(view, context, cursor);

            TextView txvConsumo = (TextView) view.findViewById(R.id.txvConsumo);

            if(cursor.getFloat(1) >= 2)
                txvConsumo.setTextColor(Color.GREEN);
            else
                txvConsumo.setTextColor(Color.RED);
        }
    }
}
