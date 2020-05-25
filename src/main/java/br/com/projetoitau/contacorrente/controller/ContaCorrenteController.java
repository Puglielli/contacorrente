package br.com.projetoitau.contacorrente.controller;

import br.com.projetoitau.contacorrente.KafkaServices.KafkaProducer;
import br.com.projetoitau.contacorrente.controller.dto.ContaCorrenteDTO;
import br.com.projetoitau.contacorrente.exception.AppException;
import br.com.projetoitau.contacorrente.utils.ErrorCode;
import br.com.projetoitau.contacorrente.model.ContaCorrenteVO;
import br.com.projetoitau.contacorrente.repository.ContaCorrenteRepository;
import br.com.projetoitau.contacorrente.utils.Status;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static java.text.MessageFormat.format;
import static br.com.projetoitau.contacorrente.utils.BaseResource.formatAccount;
import java.util.List;

@RestController
@RequestMapping("conta-corrente")
public class ContaCorrenteController {

    @Autowired
    ContaCorrenteRepository contaCorrenteRepository;

    private final KafkaProducer kafkaProducer;

    public ContaCorrenteController(KafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    @GetMapping("")
    public ResponseEntity getAllAccount() throws JsonProcessingException {

        try {

            return ResponseEntity.ok().body(contaCorrenteRepository.getAllContasAtivos(Status.ACTIVE.getCode()));

        } catch (Exception ex) {

            kafkaProducer.send(ErrorCode.BAD_REQUEST, "Buscar todas as contas", Status.FAILED);

            return ResponseEntity.status(500).body(new AppException(ErrorCode.BAD_REQUEST));
        }
    }

    @GetMapping("/{num_conta}")
    public ResponseEntity getContaByPK(@PathVariable(value = "num_conta") String num_conta) throws JsonProcessingException {

        try {

            if (contaCorrenteRepository.getContaCorrenteByNumConta(formatAccount(num_conta)).isEmpty()) {

                return ResponseEntity.status(404).body(new AppException(ErrorCode.ACCOUNT_NOT_FOUND));
            }

            ContaCorrenteVO contaCorrenteVO = contaCorrenteRepository.getContaCorrenteByNumConta(num_conta).get(0);

            return ResponseEntity.ok().body(contaCorrenteVO);

        } catch (Exception ex) {

            kafkaProducer.send(ErrorCode.BAD_REQUEST, "Buscar Conta", Status.FAILED);

            return ResponseEntity.status(500).body(new AppException(ErrorCode.BAD_REQUEST));
        }
    }

    @PutMapping("/debito")
    public ResponseEntity tirarSaldo(@RequestBody ContaCorrenteDTO contaCorrenteDTO) throws JsonProcessingException {

        try {

            List<ContaCorrenteVO> contaCorrenteVOS = contaCorrenteRepository.getContaCorrenteByNumConta(contaCorrenteDTO.getNum_conta());

            if (contaCorrenteVOS.isEmpty()) {

                kafkaProducer.send(ErrorCode.ACCOUNT_NOT_FOUND, "Debito", Status.FAILED);

                return ResponseEntity.status(404).body(new AppException(ErrorCode.ACCOUNT_NOT_FOUND));
            }

            ContaCorrenteVO contaCorrenteVO = contaCorrenteVOS.get(0);

            if (contaCorrenteVO.getSaldo() > 0 && (contaCorrenteVO.getSaldo() >= contaCorrenteDTO.getDebito())) {
                contaCorrenteVO.setSaldo(contaCorrenteVO.getSaldo() - contaCorrenteDTO.getDebito());

                contaCorrenteRepository.save(contaCorrenteVO);

                kafkaProducer.send(contaCorrenteVO, "Debito", format("Foi retirado {0} do saldo", contaCorrenteDTO.getDebito()), Status.SUCCESS);

                return ResponseEntity.status(204).build();

            } else {

                return ResponseEntity.status(400).body(new AppException(ErrorCode.INSUFFICIENT_FUNDS));
            }

        } catch (Exception ex) {

            kafkaProducer.send(ErrorCode.BAD_REQUEST, "Debito", Status.FAILED);

            return ResponseEntity.status(500).body(new AppException(ErrorCode.BAD_REQUEST));
        }
    }

    @PutMapping("/credito")
    public ResponseEntity acrescentarSaldo(@RequestBody ContaCorrenteDTO contaCorrenteDTO) throws JsonProcessingException {

        try {

            List<ContaCorrenteVO> contaCorrenteVOS = contaCorrenteRepository.getContaCorrenteByNumConta(contaCorrenteDTO.getNum_conta());

            if (contaCorrenteVOS.isEmpty()) {

                kafkaProducer.send(ErrorCode.ACCOUNT_NOT_FOUND, "Crédito", Status.FAILED);

                return ResponseEntity.status(404).body(new AppException(ErrorCode.ACCOUNT_NOT_FOUND));
            }

            ContaCorrenteVO contaCorrenteVO = contaCorrenteVOS.get(0);

            contaCorrenteVO.setSaldo(contaCorrenteVO.getSaldo() + contaCorrenteDTO.getCredito());

            contaCorrenteRepository.save(contaCorrenteVO);

            kafkaProducer.send(contaCorrenteVO, "Crédito", format("Foi acrescentado {0} ao saldo", contaCorrenteDTO.getCredito()), Status.SUCCESS);

            return ResponseEntity.status(204).build();

        } catch (Exception ex) {

            kafkaProducer.send(ErrorCode.BAD_REQUEST, "Crédito", Status.FAILED);

            return ResponseEntity.status(500).body(new AppException(ErrorCode.BAD_REQUEST));
        }
    }

}
