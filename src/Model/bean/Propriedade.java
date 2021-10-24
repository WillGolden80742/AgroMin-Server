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
public class Propriedade implements Serializable {

    private int propriedadeId;
    private String cpnj;
    private String nome;
    private int destino;
    private int maquinas;
    private int numeroEmpregados;
    private int nivelAutomacao;
    private Endereco endereco;

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }



    public int getPropriedadeId() {
        return propriedadeId;
    }

    public void setPropriedadeId(int propriedadeId) {
        this.propriedadeId = propriedadeId;
    }

    public String getCpnj() {
        return cpnj;
    }

    public void setCpnj(String cpnj) {
        this.cpnj = cpnj;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getDestino() {
        return destino;
    }

    public void setDestino(int destino) {
        this.destino = destino;
    }

    public int getNumeroEmpregados() {
        return numeroEmpregados;
    }

    public void setNumeroEmpregados(int numeroEmpregados) {
        this.numeroEmpregados = numeroEmpregados;
    }

    public int getMaquinas() {
        return maquinas;
    }

    public void setMaquinas(int maquinas) {
        this.maquinas = maquinas;
    }
  

    public int getNivelAutomacao() {
        return nivelAutomacao;
    }

    public void setNivelAutomacao(int nivelAutomacao) {
        this.nivelAutomacao = nivelAutomacao;
    }

}
