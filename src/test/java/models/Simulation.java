
package models;

import java.math.BigDecimal;
import java.util.Objects;

public class Simulation {

    private Long id;
    private String nome;
    private String cpf;
    private String email;
    private BigDecimal valor;
    private Integer parcelas;
    private Boolean seguro;

    public Simulation(String nome, String cpf, String email, BigDecimal valor, Integer parcelas, Boolean seguro) {
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.valor = valor;
        this.parcelas = parcelas;
        this.seguro = seguro;
    }

    public Simulation() {
    }

    public Long getId() {
        return this.id;
    }

    public String getNome() {
        return this.nome;
    }

    public String getCpf() {
        return this.cpf;
    }

    public String getEmail() {
        return this.email;
    }

    public BigDecimal getValor() {
        return this.valor;
    }

    public Integer getParcelas() {
        return this.parcelas;
    }

    public Boolean getSeguro() {
        return this.seguro;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public void setParcelas(Integer parcelas) {
        this.parcelas = parcelas;
    }

    public void setSeguro(Boolean seguro) {
        this.seguro = seguro;
    }

    public String toString() {
        return "Simulation(id=" + this.getId() + ", name=" + this.getNome() + ", cpf=" + this.getCpf() + ", email="
            + this.getEmail() + ", amount=" + this.getValor() + ", installments=" + this.getParcelas()
            + ", insurance=" + this.getSeguro() + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Simulation that = (Simulation) o;
        return Objects.equals(id, that.id) &&
            Objects.equals(nome, that.nome) &&
            Objects.equals(cpf, that.cpf) &&
            Objects.equals(email, that.email) &&
            Objects.equals(valor, that.valor) &&
            Objects.equals(parcelas, that.parcelas) &&
            Objects.equals(seguro, that.seguro);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, cpf, email, valor, parcelas, seguro);
    }
}
