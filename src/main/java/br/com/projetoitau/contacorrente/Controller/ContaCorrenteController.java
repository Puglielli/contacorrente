package br.com.projetoitau.contacorrente.controller;

import br.com.projetoitau.contacorrente.model.ContaCorrenteVO;
import br.com.projetoitau.contacorrente.repository.ContaCorrenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("conta-corrente")
public class ContaCorrenteController {

    @Autowired
    ContaCorrenteRepository contaCorrenteRepository;

    @GetMapping("")
    public List<ContaCorrenteVO> getAllAccount() {
        return (List<ContaCorrenteVO>) contaCorrenteRepository.findAll();
    }
}
