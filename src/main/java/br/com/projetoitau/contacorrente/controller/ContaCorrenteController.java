package br.com.projetoitau.contacorrente.controller;

import br.com.projetoitau.contacorrente.controller.dto.ContaCorrenteDTO;
import br.com.projetoitau.contacorrente.exception.AppException;
import br.com.projetoitau.contacorrente.exception.ErrorCode;
import br.com.projetoitau.contacorrente.model.ClienteVO;
import br.com.projetoitau.contacorrente.model.ContaCorrenteVO;
import br.com.projetoitau.contacorrente.repository.ContaCorrenteRepository;
import br.com.projetoitau.contacorrente.utils.ValidateCPFCNPJ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/{num_conta}")
    public ResponseEntity getContaByPK(@PathVariable(value = "num_conta") String num_conta) {
        try {

            num_conta = num_conta.replaceAll("/\\D/g", "");

            num_conta = num_conta.replaceAll("([0-9]{8})([0-9]{1})", "$1-$2");

            if (contaCorrenteRepository.getContaCorrenteByNumConta(num_conta).isEmpty()) {

                return ResponseEntity.status(404).body(new AppException(ErrorCode.ACCOUNT_NOT_FOUND));
            }

            ContaCorrenteVO contaCorrenteVO = contaCorrenteRepository.getContaCorrenteByNumConta(num_conta).get(0);

            return ResponseEntity.ok().body(contaCorrenteVO);

        } catch (Exception ex) {

            return ResponseEntity.status(500).body(new AppException(ErrorCode.BAD_REQUEST));
        }
    }

    @PutMapping("/debito")
    public ResponseEntity tirarSaldo(@RequestBody ContaCorrenteDTO contaCorrenteDTO) {

        try {

            List<ContaCorrenteVO> contaCorrenteVOS = contaCorrenteRepository.getContaCorrenteByNumConta(contaCorrenteDTO.getNum_conta());

            if (contaCorrenteVOS.isEmpty()) {

                return ResponseEntity.status(404).body(new AppException(ErrorCode.ACCOUNT_NOT_FOUND));
            }

            ContaCorrenteVO contaCorrenteVO = contaCorrenteVOS.get(0);

            if (contaCorrenteVO.getSaldo() > 0 && (contaCorrenteVO.getSaldo() >= contaCorrenteDTO.getDebito())) {
                contaCorrenteVO.setSaldo(contaCorrenteVO.getSaldo() - contaCorrenteDTO.getDebito());

                contaCorrenteRepository.save(contaCorrenteVO);

                return ResponseEntity.status(204).build();

            } else {

                return ResponseEntity.status(400).body(new AppException(ErrorCode.INSUFFICIENT_FUNDS));
            }

        } catch (Exception ex) {

            return ResponseEntity.status(500).body(new AppException(ErrorCode.BAD_REQUEST));
        }
    }

    @PutMapping("/credito")
    public ResponseEntity acrescentarSaldo(@RequestBody ContaCorrenteDTO contaCorrenteDTO) {

        try {

            List<ContaCorrenteVO> contaCorrenteVOS = contaCorrenteRepository.getContaCorrenteByNumConta(contaCorrenteDTO.getNum_conta());

            if (contaCorrenteVOS.isEmpty()) {

                return ResponseEntity.status(404).body(new AppException(ErrorCode.ACCOUNT_NOT_FOUND));
            }

            ContaCorrenteVO contaCorrenteVO = contaCorrenteVOS.get(0);

            contaCorrenteVO.setSaldo(contaCorrenteVO.getSaldo() + contaCorrenteDTO.getCredito());

            contaCorrenteRepository.save(contaCorrenteVO);

            return ResponseEntity.status(204).build();

        } catch (Exception ex) {

            return ResponseEntity.status(500).body(new AppException(ErrorCode.BAD_REQUEST));
        }
    }

}
