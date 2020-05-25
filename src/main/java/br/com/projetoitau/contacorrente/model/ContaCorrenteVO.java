package br.com.projetoitau.contacorrente.model;

import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

@Table(value = "conta")
public class ContaCorrenteVO {

    @PrimaryKeyColumn(
            name = "num_conta",
            ordinal = 1,
            type = PrimaryKeyType.PARTITIONED
    )
    private String num_conta;

    @Column(value = "agencia")
    private String agencia;

    @Column(value = "dac")
    private Integer dac;

    @Column(value = "saldo")
    private double saldo;

    @Column(value = "ativo")
    private Integer ativo;

    public String getNum_conta() {
        return num_conta;
    }

    public void setNum_conta(String num_conta) {
        this.num_conta = num_conta;
    }

    public String getAgencia() {
        return agencia;
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    public Integer getDac() {
        return dac;
    }

    public void setDac(Integer dac) {
        this.dac = dac;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public Integer getAtivo() {
        return ativo;
    }

    public void setAtivo(Integer ativo) {
        this.ativo = ativo;
    }
}
