package br.com.projetoitau.contacorrente.controller.dto;

import lombok.Builder;
import lombok.Data;
import java.util.Date;

@Data
@Builder
public class HistoricoDTO {

    private String cpf_cnpj;
    private String tipo_de_transacao;
    private Date date;
    private Integer status; // Failed = 0, Success = 1
}
