/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.bean;

import java.io.Serializable;

/**
 *
 * @author William
 */
public class Authenticated implements Serializable {

    private static String login = "";


    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

}
