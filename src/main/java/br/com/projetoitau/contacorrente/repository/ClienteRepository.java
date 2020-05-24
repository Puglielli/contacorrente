package br.com.projetoitau.contacorrente.repository;

import br.com.projetoitau.contacorrente.model.ClienteVO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClienteRepository extends CrudRepository<ClienteVO, String> { }
