package br.com.projetoitau.contacorrente.controller;

import br.com.projetoitau.contacorrente.model.HistoricoVO;
import br.com.projetoitau.contacorrente.repository.HistoricoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("historico")
public class HistoricoController {

    @Autowired
    HistoricoRepository historicoRepository;

    @GetMapping("")
    public List<HistoricoVO> getAllHistory() {
        return (List<HistoricoVO>) historicoRepository.findAll();
    }

}
