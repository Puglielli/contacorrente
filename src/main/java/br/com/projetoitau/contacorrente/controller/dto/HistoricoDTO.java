package br.com.projetoitau.contacorrente.controller.dto;

import java.sql.Timestamp;
import java.util.UUID;

public class HistoricoDTO {

    private UUID id;
    private String num_conta;
    private String tipo_de_transacao;
    private Timestamp data;
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

    public Timestamp getData() {
        return data;
    }

    public void setData(Timestamp data) {
        this.data = data;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
