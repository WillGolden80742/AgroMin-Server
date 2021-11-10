/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.bean;

import java.io.Serializable;

/**
 *
 * @author will
 */
public class Agrotoxico implements Serializable {

    private String nome;
    private String ingrediente;
    private int agroId;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getIngrediente() {
        return ingrediente;
    }

    public void setIngrediente(String ingrediente) {
        this.ingrediente = ingrediente;
    }

    public int getAgroId() {
        return agroId;
    }

    public void setAgroId(int agroId) {
        this.agroId = agroId;
    }

}
