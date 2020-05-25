package br.com.projetoitau.contacorrente.controller;

import br.com.projetoitau.contacorrente.exception.AppException;
import br.com.projetoitau.contacorrente.exception.ErrorCode;
import br.com.projetoitau.contacorrente.model.ContaCorrenteVO;
import br.com.projetoitau.contacorrente.model.HistoricoVO;
import br.com.projetoitau.contacorrente.repository.HistoricoRepository;
import br.com.projetoitau.contacorrente.utils.ValidateCPFCNPJ;
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

    @GetMapping("/{cpf_cnpj}")
    public ResponseEntity getContaByPK(@PathVariable(value = "cpf_cnpj") String cpf_cnpj) {
        try {

            ValidateCPFCNPJ cpfcnpj = new ValidateCPFCNPJ(cpf_cnpj);

            if (historicoRepository.getHistoricoByCPFCNPJ(cpfcnpj.getCpfCnpj()).isEmpty()) {

                return ResponseEntity.status(404).body(new AppException(ErrorCode.CPF_CNPJ_NOT_FOUND));
            }

            HistoricoVO historicoVO = historicoRepository.getHistoricoByCPFCNPJ(cpfcnpj.getCpfCnpj()).get(0);

            return ResponseEntity.ok().body(historicoVO);

        } catch (Exception ex) {

            return ResponseEntity.status(500).body(new AppException(ErrorCode.BAD_REQUEST));
        }
    }

}
