package br.com.projetoitau.contacorrente.controller;

import br.com.projetoitau.contacorrente.exception.AppException;
import br.com.projetoitau.contacorrente.utils.BaseResource;
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
import java.util.UUID;

import static br.com.projetoitau.contacorrente.utils.BaseResource.formatAccount;
import static br.com.projetoitau.contacorrente.utils.BaseResource.toHistoricoDTO;
import static br.com.projetoitau.contacorrente.utils.BaseResource.toHistoricoDTOList;

@RestController
@RequestMapping("historico")
public class HistoricoController {

    @Autowired
    HistoricoRepository historicoRepository;

    @GetMapping("")
    public List<HistoricoVO> getAllHistory() {
        return (List<HistoricoVO>) historicoRepository.findAll();
    }

    @GetMapping("/id/{id}")
    public ResponseEntity getContaById(@PathVariable(value = "id") String id) {

        try {

            List<HistoricoVO> historicoVOS = historicoRepository.getHistoricoById(UUID.fromString(id));

            if (historicoVOS.isEmpty()) {

                return ResponseEntity.status(404).body(new AppException(ErrorCode.ID_NOT_FOUND));
            }

            return ResponseEntity.ok().body(toHistoricoDTO(historicoVOS.get(0)));

        } catch(IllegalArgumentException i) {

            return ResponseEntity.status(500).body(new AppException(ErrorCode.ID_NOT_FOUND));

        } catch (Exception ex) {

            return ResponseEntity.status(500).body(new AppException(ErrorCode.BAD_REQUEST));
        }
    }

    @GetMapping("/num-conta/{num_conta}")
    public ResponseEntity getContaByNumConta(@PathVariable(value = "num_conta") String num_conta) {

        try {

            List<HistoricoVO> historicoVOS = historicoRepository.getHistoricoByNumConta(formatAccount(num_conta));

            if (historicoVOS.isEmpty()) {

                return ResponseEntity.status(404).body(new AppException(ErrorCode.CPF_CNPJ_NOT_FOUND));
            }

            return ResponseEntity.ok().body(toHistoricoDTOList(historicoVOS));

        } catch (Exception ex) {

            return ResponseEntity.status(500).body(new AppException(ErrorCode.BAD_REQUEST));
        }
    }

}
