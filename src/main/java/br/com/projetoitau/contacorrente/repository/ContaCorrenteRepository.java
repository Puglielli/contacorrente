package br.com.projetoitau.contacorrente.repository;

import br.com.projetoitau.contacorrente.model.ClienteVO;
import br.com.projetoitau.contacorrente.model.ContaCorrenteVO;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContaCorrenteRepository extends CrudRepository<ContaCorrenteVO, String> {

    @Query(value = "select * from conta where num_conta = (:num_conta)")
    List<ContaCorrenteVO> getContaCorrenteByNumConta(@Param("num_conta") String num_conta);

    @Query(value = "select * from conta where ativo = (:ativo)")
    List<ClienteVO> getAllContasAtivos(@Param("ativo") Integer ativo);

}
