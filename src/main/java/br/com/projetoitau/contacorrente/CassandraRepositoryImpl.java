package br.com.projetoitau.contacorrente;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CassandraRepositoryImpl extends CrudRepository <DBEntity, UUID >{
}
