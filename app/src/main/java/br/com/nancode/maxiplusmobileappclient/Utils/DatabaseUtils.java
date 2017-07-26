package br.com.nancode.maxiplusmobileappclient.Utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by martins on 19/07/2017.
 */

public class DatabaseUtils extends SQLiteOpenHelper {

    //NOME DA BASE DE DADOS
    private static final String NOME_BASE_DE_DADOS   = "MAXIMOBILE.db";

    //VERSÃO DO BANCO DE DADOS
    private static final int    VERSAO_BASE_DE_DADOS = 1;

    //CONSTRUTOR
    public DatabaseUtils(Context context){

        super(context,NOME_BASE_DE_DADOS,null,VERSAO_BASE_DE_DADOS);
    }

    /*NA INICIALIZAÇÃO DA CLASSE VAMOS CRIAR A TABELA QUE VAMOS USAR*/
    @Override
    public void onCreate(SQLiteDatabase db) {

        StringBuilder stringBuilderCreateTable = new StringBuilder();

        stringBuilderCreateTable.append(" CREATE TABLE tb_user (");
        stringBuilderCreateTable.append("        us_ID      INTEGER PRIMARY KEY AUTOINCREMENT, ");
        stringBuilderCreateTable.append("        us_login       TEXT    NOT NULL,            ");
        stringBuilderCreateTable.append("        us_pass        TEXT    NOT NULL,            ");
        stringBuilderCreateTable.append("        us_idexterno      INTEGER     NOT NULL,            ");
        stringBuilderCreateTable.append("        us_data        TEXT    NOT NULL )            ");


        db.execSQL(stringBuilderCreateTable.toString());

        StringBuilder stringBuilderCreateTable2 = new StringBuilder();

        stringBuilderCreateTable2.append(" CREATE TABLE tb_log (");
        stringBuilderCreateTable2.append("        log_ID      INTEGER PRIMARY KEY AUTOINCREMENT, ");
        stringBuilderCreateTable2.append("        log_evento       TEXT        NOT NULL,            ");
        stringBuilderCreateTable2.append("        log_date         TEXT        NOT NULL )            ");


        db.execSQL(stringBuilderCreateTable2.toString());

    }

    /*SE TROCAR A VERSÃO DO BANCO DE DADOS VOCÊ PODE EXECUTAR ALGUMA ROTINA
      COMO CRIAR COLUNAS, EXCLUIR ENTRE OUTRAS */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS tb_user");
        db.execSQL("DROP TABLE IF EXISTS tb_log");
        onCreate(db);

    }

    /*MÉTODO QUE VAMOS USAR NA CLASSE QUE VAI EXECUTAR AS ROTINAS NO
    BANCO DE DADOS*/
    public SQLiteDatabase GetConexaoDataBase(){

        return this.getWritableDatabase();
    }
}