package br.com.projetoitau.contacorrente.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.Date;

@Data
@Builder
@Table(value = "historico")
public class HistoricoVO {

    @PrimaryKeyColumn(
            name = "cpf_cnpj",
            ordinal = 1,
            type = PrimaryKeyType.PARTITIONED
    )
    private String cpf_cnpj;

    @Column(value = "tipo_de_transacao")
    private String tipo_de_transacao;

    @Column(value = "date")
    private Date date;

    @Column(value = "status")
    private Integer status; // Failed = 0, Success = 1
}
