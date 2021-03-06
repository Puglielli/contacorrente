package br.com.projetoitau.contacorrente.model;

import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.io.Serializable;

@Table(value = "cliente")
public class ClienteVO implements Serializable {

    @Column(value = "nome")
    private String nome;

    @PrimaryKeyColumn(
            name = "cpf_cnpj",
            ordinal = 1,
            type = PrimaryKeyType.PARTITIONED
    )
    private String cpf_cnpj;

    @Column(value = "tipo_de_cliente")
    private String tipo_de_cliente;

    @Column(value = "endereco")
    private String endereco;

    @Column(value = "profissao")
    private String profissao;

    @Column(value = "razao_social")
    private String razao_social;

    @Column(value = "inscr_estadual")
    private String inscr_estadual;

    @Column(value = "num_conta")
    private String num_conta;

    @Column(value = "ativo")
    private Integer ativo;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf_cnpj() {
        return cpf_cnpj;
    }

    public void setCpf_cnpj(String cpf_cnpj) {
        this.cpf_cnpj = cpf_cnpj;
    }

    public String getTipo_de_cliente() {
        return tipo_de_cliente;
    }

    public void setTipo_de_cliente(String tipo_de_cliente) {
        this.tipo_de_cliente = tipo_de_cliente;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getProfissao() {
        return profissao;
    }

    public void setProfissao(String profissao) {
        this.profissao = profissao;
    }

    public String getRazao_social() {
        return razao_social;
    }

    public void setRazao_social(String razao_social) {
        this.razao_social = razao_social;
    }

    public String getInscr_estadual() {
        return inscr_estadual;
    }

    public void setInscr_estadual(String inscr_estadual) {
        this.inscr_estadual = inscr_estadual;
    }

    public String getNum_conta() {
        return num_conta;
    }

    public void setNum_conta(String num_conta) {
        this.num_conta = num_conta;
    }

    public Integer getAtivo() {
        return ativo;
    }

    public void setAtivo(Integer ativo) {
        this.ativo = ativo;
    }
}
