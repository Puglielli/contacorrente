package br.com.projetoitau.contacorrente;


import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;
import groovy.transform.builder.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
@Table(value = "projectitau")

public class DBEntity {
    UUID id;

    @Column(value = "mansagem")
    String mensagem;
}