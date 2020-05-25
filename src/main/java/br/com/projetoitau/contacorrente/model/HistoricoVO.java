package br.com.projetoitau.contacorrente.model;

import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.Date;
import java.util.UUID;

@Table(value = "historico")
public class HistoricoVO {

    @PrimaryKeyColumn(
            name = "id",
            ordinal = 1,
            type = PrimaryKeyType.PARTITIONED
    )
    private UUID id;

    @Column(value = "num_conta")
    private String num_conta;

    @Column(value = "tipo_de_transacao")
    private String tipo_de_transacao;

    @Column(value = "tipo_de_operacao")
    private String tipo_de_operacao;

    @Column(value = "data")
    private Date data;

    @Column(value = "status")
    private Integer status; // Failed = 0, Success = 1

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNum_conta() {
        return num_conta;
    }

    public void setNum_conta(String num_conta) {
        this.num_conta = num_conta;
    }

    public String getTipo_de_transacao() {
        return tipo_de_transacao;
    }

    public void setTipo_de_transacao(String tipo_de_transacao) {
        this.tipo_de_transacao = tipo_de_transacao;
    }

    public String getTipo_de_operacao() {
        return tipo_de_operacao;
    }

    public void setTipo_de_operacao(String tipo_de_operacao) {
        this.tipo_de_operacao = tipo_de_operacao;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
