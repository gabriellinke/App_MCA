package helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    private static final String NOME_BANCO = "quantidade_agua.db";
    private static final int VERSAO_BANCO  = 3;

    public DBHelper(@Nullable Context context) {
        super(context, NOME_BANCO, null, VERSAO_BANCO);
        Log.d("DBHELPER", "Constructor");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("DBHELPER", "onCreate");


        String sqlConfiguracoes = "CREATE TABLE IF NOT EXISTS configuracoes (" +
                "_id INTEGER PRIMARY KEY," +
                "consumo REAL," +
                "data VARCHAR(255)" +
                ");";

        db.execSQL(sqlConfiguracoes);

        this.onUpgrade(db, 1, VERSAO_BANCO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


        Log.d("DBHELPER", "onUpgrade");

        Log.d("DBHELPER", "ATT 1");
        Log.d("DBHELPER", "ATT 2");
        Log.d("DBHELPER", "ATT 3");
        Log.d("DBHELPER", "ATT 4");

        String sqlConsumo = "CREATE TABLE IF NOT EXISTS consumo (" +
                "_id INTEGER PRIMARY KEY," +
                "consumo REAL," +
                "data VARCHAR(255)" +
                ");";

        db.execSQL(sqlConsumo);

    }
}
