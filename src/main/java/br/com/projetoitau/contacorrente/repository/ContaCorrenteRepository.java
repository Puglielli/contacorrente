package br.com.projetoitau.contacorrente.repository;

import br.com.projetoitau.contacorrente.model.ContaCorrenteVO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContaCorrenteRepository extends CrudRepository<ContaCorrenteVO, String> {
}
