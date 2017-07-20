package br.com.nancode.maxiplusmobileappclient.Repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import br.com.nancode.maxiplusmobileappclient.Model.logModel;
import br.com.nancode.maxiplusmobileappclient.Utils.DatabaseUtils;

/**
 * Created by martins on 20/07/2017.
 */

public class logRepository {

    DatabaseUtils databaseUtil;

    /***
     * CONSTRUTOR
     * @param context
     */
    public logRepository(Context context){

        databaseUtil =  new DatabaseUtils(context);

    }

    /***
     * SALVA UM NOVO REGISTRO NA BASE DE DADOS
     * @param logModel
     */
    public void Salvar(logModel logModel){

        ContentValues contentValues =  new ContentValues();
        /*MONTANDO OS PARAMETROS PARA SEREM SALVOS*/
        contentValues.put("log_evento",       logModel.getEvento());
        contentValues.put("log_data",         logModel.getDate());

        /*EXECUTANDO INSERT DE UM NOVO REGISTRO*/
        databaseUtil.GetConexaoDataBase().insert("tb_log",null,contentValues);

    }

    /***
     * ATUALIZA UM REGISTRO JÁ EXISTENTE NA BASE
     * @param logModel
     */
    public void Atualizar(logModel logModel){

        ContentValues contentValues =  new ContentValues();

        /*MONTA OS PARAMENTROS PARA REALIZAR UPDATE NOS CAMPOS*/
        contentValues.put("log_evento",       logModel.getEvento());
        contentValues.put("log_data",         logModel.getDate());

        /*REALIZANDO UPDATE PELA CHAVE DA TABELA*/
        databaseUtil.GetConexaoDataBase().update("tb_log", contentValues, "log_ID = ?", new String[]{Integer.toString(logModel.getID())});
    }

    /***
     * EXCLUI UM REGISTRO PELO ID
     * @param ID
     * @return
     */
    public Integer Excluir(int ID){

        //EXCLUINDO  REGISTRO E RETORNANDO O NÚMERO DE LINHAS AFETADAS
        return databaseUtil.GetConexaoDataBase().delete("tb_log","log_ID = ?", new String[]{Integer.toString(ID)});
    }

    /***
     * CONSULTA UM LOG CADASTRADO PELO ID
     * @param ID
     * @return
     */
    public logModel GetLog(int ID){


        Cursor cursor =  databaseUtil.GetConexaoDataBase().rawQuery("SELECT * FROM tb_log WHERE log_ID= "+ ID,null);

        cursor.moveToFirst();

        ///CRIANDO UM NOVO LOG
        logModel logModel =  new logModel();

        //ADICIONANDO OS DADOS DO LOG
        logModel.setID(cursor.getInt(cursor.getColumnIndex("log_ID")));
        logModel.setDate(cursor.getString(cursor.getColumnIndex("log_date")));
        logModel.setEvento(cursor.getString(cursor.getColumnIndex("log_evento")));

        //RETORNANDO O LOG
        return logModel;

    }

    /***
     * CONSULTA TODOS OS LOGS CADASTRADOS NA BASE
     * @return
     */
    public List<logModel> SelecionarTodos(){

        List<logModel> logs = new ArrayList<logModel>();


        //MONTA A QUERY A SER EXECUTADA
        StringBuilder stringBuilderQuery = new StringBuilder();
        stringBuilderQuery.append(" SELECT log_ID,      ");
        stringBuilderQuery.append("        log_evento,        ");
        stringBuilderQuery.append("        log_date        ");
        stringBuilderQuery.append("  FROM  tb_log       ");
        stringBuilderQuery.append(" ORDER BY log_date ASC      ");


        //CONSULTANDO OS REGISTROS CADASTRADOS
        Cursor cursor = databaseUtil.GetConexaoDataBase().rawQuery(stringBuilderQuery.toString(), null);

        /*POSICIONA O CURSOR NO PRIMEIRO REGISTRO*/
        cursor.moveToFirst();


        logModel logModel;

        //REALIZA A LEITURA DOS REGISTROS ENQUANTO NÃO FOR O FIM DO CURSOR
        while (!cursor.isAfterLast()){

            /* CRIANDO UM NOVO LOG */
            logModel =  new logModel();

            //ADICIONANDO OS DADOS DO LOG
            logModel.setID(cursor.getInt(cursor.getColumnIndex("log_ID")));
            logModel.setDate(cursor.getString(cursor.getColumnIndex("log_date")));
            logModel.setEvento(cursor.getString(cursor.getColumnIndex("log_evento")));

            //ADICIONANDO UM LOG NA LISTA
            logs.add(logModel);

            //VAI PARA O PRÓXIMO REGISTRO
            cursor.moveToNext();
        }

        //RETORNANDO A LISTA DE LOGS
        return logs;

    }

    /***
     * REMOVE TODOS OS LOGS CADASTRADOS NA BASE
     * @return
     */
    public Integer DeletarTodos(){

        //REMOVENDO OS REGISTROS CADASTRADOS
        return databaseUtil.GetConexaoDataBase().delete("tb_log","log_ID = log_ID",null);

    }
}