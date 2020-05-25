package br.com.projetoitau.contacorrente.controller;

import br.com.projetoitau.contacorrente.exception.AppException;
import br.com.projetoitau.contacorrente.utils.ErrorCode;
import br.com.projetoitau.contacorrente.model.HistoricoVO;
import br.com.projetoitau.contacorrente.repository.HistoricoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/{num_conta}")
    public ResponseEntity getContaByPK(@PathVariable(value = "num_conta") String num_conta) {
        try {

            num_conta = num_conta.replaceAll("/\\D/g", "");

            num_conta = num_conta.replaceAll("([0-9]{8})([0-9]{1})", "$1-$2");

            List<HistoricoVO> historicoVOS = historicoRepository.getHistoricoByNumConta(num_conta);

            if (historicoVOS.isEmpty()) {

                return ResponseEntity.status(404).body(new AppException(ErrorCode.CPF_CNPJ_NOT_FOUND));
            }

            HistoricoVO historicoVO = historicoVOS.get(0);

            return ResponseEntity.ok().body(historicoVO);

        } catch (Exception ex) {

            return ResponseEntity.status(500).body(new AppException(ErrorCode.BAD_REQUEST));
        }
    }

}
