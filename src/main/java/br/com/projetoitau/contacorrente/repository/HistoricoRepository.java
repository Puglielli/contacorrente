package br.com.projetoitau.contacorrente.repository;

import br.com.projetoitau.contacorrente.model.HistoricoVO;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface HistoricoRepository extends CrudRepository<HistoricoVO, String> {

    @Query(value = "select * from historico where num_conta = (:num_conta)")
    List<HistoricoVO> getHistoricoByNumConta(@Param("num_conta") String num_conta);

    @Query(value = "select * from historico where id = (:id)")
    List<HistoricoVO> getHistoricoById(@Param("id") UUID id);
}
