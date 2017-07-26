package br.com.nancode.maxiplusmobileappclient.Repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import br.com.nancode.maxiplusmobileappclient.Utils.DatabaseUtils;
import br.com.nancode.maxiplusmobileappclient.Model.userModel;

/**
 * Created by martins on 19/07/2017.
 */

public class userRepository {

    DatabaseUtils databaseUtil;

    /***
     * CONSTRUTOR
     * @param context
     */
    public userRepository(Context context){

        databaseUtil =  new DatabaseUtils(context);

    }

    /***
     * SALVA UM NOVO REGISTRO NA BASE DE DADOS
     * @param userModel
     */
    public void Salvar(userModel userModel){

        ContentValues contentValues =  new ContentValues();
        /*MONTANDO OS PARAMETROS PARA SEREM SALVOS*/
        contentValues.put("us_login",       userModel.getLogin());
        contentValues.put("us_pass",        userModel.getSenha());
        contentValues.put("us_data",        userModel.getData());
        contentValues.put("us_idexterno",          userModel.getId());

        /*EXECUTANDO INSERT DE UM NOVO REGISTRO*/
        databaseUtil.GetConexaoDataBase().insert("tb_user",null,contentValues);

    }

    /***
     * ATUALIZA UM REGISTRO JÁ EXISTENTE NA BASE
     * @param userModel
     */
    public void Atualizar(userModel userModel){

        ContentValues contentValues =  new ContentValues();

        /*MONTA OS PARAMENTROS PARA REALIZAR UPDATE NOS CAMPOS*/
        contentValues.put("us_login",       userModel.getLogin());
        contentValues.put("us_pass",        userModel.getSenha());
        contentValues.put("us_data",        userModel.getData());
        contentValues.put("us_idexterno",          userModel.getId());

        /*REALIZANDO UPDATE PELA CHAVE DA TABELA*/
        databaseUtil.GetConexaoDataBase().update("tb_user", contentValues, "us_ID = ?", new String[]{Integer.toString(userModel.getID())});
    }

    /***
     * EXCLUI UM REGISTRO PELO CÓDIGO
     * @param ID
     * @return
     */
    public Integer Excluir(int ID){

        //EXCLUINDO  REGISTRO E RETORNANDO O NÚMERO DE LINHAS AFETADAS
        return databaseUtil.GetConexaoDataBase().delete("tb_user","us_ID = ?", new String[]{Integer.toString(ID)});
    }

    /***
     * CONSULTA UMA USUÁRIO CADASTRADO PELO ID
     * @param ID
     * @return
     */
    public userModel GetUser(int ID){


        Cursor cursor =  databaseUtil.GetConexaoDataBase().rawQuery("SELECT * FROM tb_user WHERE us_ID= "+ ID,null);

        cursor.moveToFirst();

        ///CRIANDO UM NOVO USUÁRIO
        userModel userModel =  new userModel();

        //ADICIONANDO OS DADOS DO USUÁRIO
        userModel.setID(cursor.getInt(cursor.getColumnIndex("us_ID")));
        userModel.setId(cursor.getInt(cursor.getColumnIndex("us_idexterno")));
        userModel.setData(cursor.getString(cursor.getColumnIndex("us_data")));
        userModel.setLogin(cursor.getString(cursor.getColumnIndex("us_login")));
        userModel.setSenha(cursor.getString(cursor.getColumnIndex("us_pass")));

        //RETORNANDO O USUÁRIO
        return userModel;

    }

    /***
     * CONSULTA TODOS OS USUÁRIOS CADASTRADOS NA BASE
     * @return
     */
    public List<userModel> SelecionarTodos(){

        List<userModel> users = new ArrayList<userModel>();


        //MONTA A QUERY A SER EXECUTADA
        StringBuilder stringBuilderQuery = new StringBuilder();
        stringBuilderQuery.append(" SELECT *      ");
        stringBuilderQuery.append("  FROM  tb_user       ");


        //CONSULTANDO OS REGISTROS CADASTRADOS
        Cursor cursor = databaseUtil.GetConexaoDataBase().rawQuery(stringBuilderQuery.toString(), null);

        /*POSICIONA O CURSOR NO PRIMEIRO REGISTRO*/
        cursor.moveToFirst();


        userModel userModel;

        //REALIZA A LEITURA DOS REGISTROS ENQUANTO NÃO FOR O FIM DO CURSOR
        while (!cursor.isAfterLast()){

            /* CRIANDO UM NOVO USUÁRIO */
            userModel =  new userModel();

            //ADICIONANDO OS DADOS DO USUÁRIO
            userModel.setID(cursor.getInt(cursor.getColumnIndex("us_ID")));
            userModel.setId(cursor.getInt(cursor.getColumnIndex("us_idexterno")));
            userModel.setData(cursor.getString(cursor.getColumnIndex("us_data")));
            userModel.setLogin(cursor.getString(cursor.getColumnIndex("us_login")));
            userModel.setSenha(cursor.getString(cursor.getColumnIndex("us_pass")));

            //ADICIONANDO UM USUÁRIO NA LISTA
            users.add(userModel);

            //VAI PARA O PRÓXIMO REGISTRO
            cursor.moveToNext();
        }

        //RETORNANDO A LISTA DE USUÁRIOS
        return users;

    }

    /***
     * REMOVE TODOS OS USUÁRIOS SALVOS NA BASE
     * @return
     */
    public Integer LogOut(){

        //REMOVENDO OS REGISTROS CADASTRADOS
        return databaseUtil.GetConexaoDataBase().delete("tb_user","us_ID = us_ID",null);

    }

}