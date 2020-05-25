package br.com.projetoitau.contacorrente.repository;

import br.com.projetoitau.contacorrente.model.ClienteVO;
import br.com.projetoitau.contacorrente.model.HistoricoVO;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoricoRepository extends CrudRepository<HistoricoVO, String> {

    @Query(value = "select * from historico where cpf_cnpj = (:cpf_cnpj)")
    List<HistoricoVO> getHistoricoByCPFCNPJ(@Param("cpf_cnpj") String cpf_cnpj);
}
