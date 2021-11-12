package Model.bean;

import java.io.Serializable;

/**
 *
 * @author will
 */
public class Produto implements Serializable {

    private String nome;
    private double producaoAnual;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getProducaoAnual() {
        return producaoAnual;
    }

    public void setProducaoAnual(double producaoAnual) {
        this.producaoAnual = producaoAnual;
    }

}
