package Model.bean;

import java.io.Serializable;

/**
 *
 * @author will
 */
public class Imposto implements Serializable {

    private String nome;
    private String tipo;
    private double subsidio;
    private Double valor;
    private int pago;
    private String lancamento;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getValorSubsidiado() {
        return ((valor * subsidio) / 100);
    }

    public double getValorLiquido() {
        return (valor - ((valor * subsidio) / 100));
    }

    public void setSubsidio(double subsidio) {
        this.subsidio = subsidio;
    }

    public double getSubsidio() {
        return subsidio;
    }

    public Double getValorBruto() {
        return valor;
    }

    public void setValorBruto(Double valor) {
        this.valor = valor;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getPago() {
        return pago;
    }

    public void setPago(int pago) {
        this.pago = pago;
    }

    public String getLancamento() {
        return lancamento;
    }

    public void setLancamento(String lancamento) {
        this.lancamento = lancamento;
    }

}
