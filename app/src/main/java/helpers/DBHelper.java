package helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

/** CLASSE QUE CRIA A BASE DO BANCO DE DADOS **/

public class DBHelper extends SQLiteOpenHelper {

    private static final String NOME_BANCO = "quantidade_agua.db";
    private static final int VERSAO_BANCO  = 3;

    public DBHelper(@Nullable Context context) {
        super(context, NOME_BANCO, null, VERSAO_BANCO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
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
        /*switch (oldVersion)
        {
            case 1:
                faz att
            case 2:
                ...
            case 3:
                ...
        }*/

        String sqlConsumo = "CREATE TABLE IF NOT EXISTS consumo (" +
                "_id INTEGER PRIMARY KEY," +
                "consumo REAL," +
                "data VARCHAR(255)" +
                ");";

        db.execSQL(sqlConsumo);
    }
}
