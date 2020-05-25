package br.com.projetoitau.contacorrente.repository;

import br.com.projetoitau.contacorrente.model.ClienteVO;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClienteRepository extends CrudRepository<ClienteVO, String> {

    @Query(value = "select * from cliente where cpf_cnpj = (:cpf_cnpj) ALLOW FILTERING")
    List<ClienteVO> getClienteByCPFCNPJ(@Param("cpf_cnpj") String cpf_cnpj);

    @Query(value = "select * from cliente ALLOW FILTERING")
    List<ClienteVO> getAllClientes();
}
