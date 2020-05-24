package br.com.projetoitau.contacorrente;


import lombok.Builder;
import lombok.Data;
import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.Table;

import java.util.UUID;

@Data
@Builder
@Table(value = "projectitau")
class DBEntity {
    UUID id;

    @Column(value = "mansagem")
    String mensagem;
}
