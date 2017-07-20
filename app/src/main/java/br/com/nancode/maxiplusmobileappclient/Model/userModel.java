package br.com.nancode.maxiplusmobileappclient.Model;

/**
 * Created by martins on 19/07/2017.
 */

public class userModel {

    private Integer ID;
    private String  login;
    private String  senha;
    private Integer  id;
    private String  data;


    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

}