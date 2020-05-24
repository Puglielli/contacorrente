package br.com.projetoitau.contacorrente.repository;

import br.com.projetoitau.contacorrente.model.HistoricoVO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoricoRepository extends CrudRepository<HistoricoVO, String> {
}
