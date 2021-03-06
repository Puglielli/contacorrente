package br.com.projetoitau.contacorrente.controller;

import br.com.projetoitau.contacorrente.exception.AppException;
import br.com.projetoitau.contacorrente.utils.ErrorCode;
import br.com.projetoitau.contacorrente.controller.dto.ClienteDTO;
import br.com.projetoitau.contacorrente.model.ClienteVO;
import br.com.projetoitau.contacorrente.model.ContaCorrenteVO;
import br.com.projetoitau.contacorrente.repository.ClienteRepository;
import br.com.projetoitau.contacorrente.repository.ContaCorrenteRepository;
import br.com.projetoitau.contacorrente.KafkaServices.KafkaProducer;
import br.com.projetoitau.contacorrente.utils.Status;
import br.com.projetoitau.contacorrente.utils.ValidateCPFCNPJ;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.kafka.common.metrics.Stat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static br.com.projetoitau.contacorrente.utils.BaseResource.*;

@RestController
@RequestMapping("cliente")
public class ClienteController {

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    ContaCorrenteRepository contaCorrenteRepository;

    private final KafkaProducer kafkaProducer;

    public ClienteController(KafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    @GetMapping("")
    public ResponseEntity getAllClientes() throws JsonProcessingException {

        try {

            return ResponseEntity.ok().body(toClienteDTOList(clienteRepository.getAllClientes()));

        } catch (Exception ex) {

            kafkaProducer.send(ErrorCode.BAD_REQUEST, "Buscar todos os Clientes", Status.FAILED);

            return ResponseEntity.status(500).body(new AppException(ErrorCode.BAD_REQUEST));
        }
    }

    @GetMapping("/{cpf_cnpj}")
    public ResponseEntity getClienteByPK(@PathVariable(value = "cpf_cnpj") String cpf_cnpj) throws JsonProcessingException {

        try {

            ValidateCPFCNPJ cpfcnpj = new ValidateCPFCNPJ(cpf_cnpj);

            if (cpfcnpj.getCpfCnpj() != null && clienteRepository.getClienteByCPFCNPJ(cpfcnpj.getCpfCnpj()).isEmpty()) {

                kafkaProducer.send(ErrorCode.CPF_CNPJ_NOT_FOUND, "Buscar Cliente", Status.FAILED);

                return ResponseEntity.status(404).body(new AppException(ErrorCode.CPF_CNPJ_NOT_FOUND));
            }

            return ResponseEntity.ok().body(toClienteDTO(clienteRepository.getClienteByCPFCNPJ(cpfcnpj.getCpfCnpj()).get(0)));

        } catch (Exception ex) {

            kafkaProducer.send(ErrorCode.BAD_REQUEST, "Buscar Cliente", Status.FAILED);

            return ResponseEntity.status(500).body(new AppException(ErrorCode.BAD_REQUEST));
        }
    }

    @PostMapping("")
    public ResponseEntity saveCliente(@RequestBody ClienteDTO clienteDTO) throws JsonProcessingException {

        try {

            ValidateCPFCNPJ cpfcnpj = new ValidateCPFCNPJ(clienteDTO.getCpf_cnpj());

            if (clienteDTO != null && cpfcnpj.getCpfCnpj() != null) {

                if (!clienteRepository.getClienteByCPFCNPJ(cpfcnpj.getCpfCnpj()).isEmpty()) {

                    kafkaProducer.send(ErrorCode.CPF_CNPJ_ALREADY_EXISTS, "Cadastro de Cliente", Status.FAILED);

                    return ResponseEntity.status(400).body(new AppException(ErrorCode.CPF_CNPJ_ALREADY_EXISTS));

                } else if (!new ValidateCPFCNPJ(clienteDTO.getCpf_cnpj()).isValid()) {

                    kafkaProducer.send(ErrorCode.CPF_CNPJ_INVALID, "Cadastro de Cliente", Status.FAILED);

                    return ResponseEntity.status(400).body(new AppException(ErrorCode.CPF_CNPJ_INVALID));
                }

            } else {

                kafkaProducer.send(ErrorCode.CPF_CNPJ_CANNOT_BE_NULL_OR_EMPTY, "Cadastro de Cliente", Status.FAILED);

                return ResponseEntity.status(400).body(new AppException(ErrorCode.CPF_CNPJ_CANNOT_BE_NULL_OR_EMPTY));
            }

            ClienteVO clienteVO = clienteRepository.save(toClienteVO(clienteDTO));

            ContaCorrenteVO contaCorrenteVO = new ContaCorrenteVO();

            contaCorrenteVO.setAgencia(generateNewAgency());
            contaCorrenteVO.setNum_conta(formatAccount(generateNewAccount()));
            contaCorrenteVO.setDac(getDac(contaCorrenteVO.getAgencia()));
            contaCorrenteVO.setSaldo(0);
            contaCorrenteVO.setAtivo(Status.ACTIVE.getCode());

            contaCorrenteVO = contaCorrenteRepository.save(contaCorrenteVO);

            clienteVO.setNum_conta(contaCorrenteVO.getNum_conta());

            clienteVO = clienteRepository.save(clienteVO);

            kafkaProducer.send(clienteVO, "Cadastro de Cliente", "Novo cliente Cadastrado com sucesso", Status.SUCCESS);

            return ResponseEntity.ok().body(clienteVO);

        } catch (Exception ex) {

            kafkaProducer.send(ErrorCode.BAD_REQUEST, "Cadastro de Cliente", Status.FAILED);

            return ResponseEntity.status(500).body(new AppException(ErrorCode.BAD_REQUEST));
        }
    }

    @PutMapping("/{cpf_cnpj}")
    public ResponseEntity atualizaCliente(@PathVariable(value = "cpf_cnpj") String cpf_cnpj, @RequestBody ClienteDTO clienteDTO) throws JsonProcessingException {

        try {

            ValidateCPFCNPJ cpfcnpj = new ValidateCPFCNPJ(cpf_cnpj);

            if (cpfcnpj.getCpfCnpj() != null && clienteRepository.getClienteByCPFCNPJ(cpfcnpj.getCpfCnpj()).isEmpty()) {

                kafkaProducer.send(ErrorCode.CPF_CNPJ_NOT_FOUND, "Atualizar Cliente", Status.FAILED);

                return ResponseEntity.status(404).body(new AppException(ErrorCode.CPF_CNPJ_NOT_FOUND));
            }

            ClienteVO clienteVO = clienteRepository.getClienteByCPFCNPJ(cpfcnpj.getCpfCnpj()).get(0);

            clienteVO.setNome(clienteDTO.getNome());
            clienteVO.setEndereco(clienteDTO.getEndereco());
            clienteVO.setProfissao(clienteDTO.getProfissao());
            clienteVO.setRazao_social(clienteDTO.getRazao_social());
            clienteVO.setInscr_estadual(clienteDTO.getInscr_estadual());

            clienteRepository.save(clienteVO);

            kafkaProducer.send(clienteVO, "Atualizar Cliente", "Dados atualizados com sucesso", Status.SUCCESS);

            return ResponseEntity.status(204).build();

        } catch (Exception ex) {

            kafkaProducer.send(ErrorCode.BAD_REQUEST, "Atualizar Cliente", Status.FAILED);

            return ResponseEntity.status(500).body(new AppException(ErrorCode.BAD_REQUEST));
        }
    }

    @DeleteMapping("/{cpf_cnpj}")
    public ResponseEntity deletarCliente(@PathVariable(value = "cpf_cnpj") String cpf_cnpj) throws JsonProcessingException {

        try {

            ValidateCPFCNPJ cpfcnpj = new ValidateCPFCNPJ(cpf_cnpj);

            if (clienteRepository.getClienteByCPFCNPJ(cpfcnpj.getCpfCnpj()).isEmpty()) {

                kafkaProducer.send(ErrorCode.CPF_CNPJ_NOT_FOUND, "Deletar Cliente", Status.FAILED);

                return ResponseEntity.status(404).body(new AppException(ErrorCode.CPF_CNPJ_NOT_FOUND));
            }

            ClienteVO clienteVO = clienteRepository.getClienteByCPFCNPJ(cpfcnpj.getCpfCnpj()).get(0);

            if (clienteVO.getAtivo().equals(Status.INATIVE.getCode())) {

                kafkaProducer.send(ErrorCode.CLIENT_HAS_ALREADY_BEEN_DELETED, "Deletar Cliente", Status.FAILED);

                return ResponseEntity.status(404).body(new AppException(ErrorCode.CLIENT_HAS_ALREADY_BEEN_DELETED));
            }

            clienteVO.setAtivo(Status.INATIVE.getCode());

            clienteRepository.save(clienteVO); // Inativando Cliente

            ContaCorrenteVO contaCorrenteVO = contaCorrenteRepository.getContaCorrenteByNumConta(clienteVO.getNum_conta()).get(0);

            contaCorrenteVO.setAtivo(Status.INATIVE.getCode());

            contaCorrenteRepository.save(contaCorrenteVO);  // Inativando Conta

            kafkaProducer.send(clienteVO, "Deletar Cliente", "Exclusão realizada com sucesso", Status.SUCCESS);

            return ResponseEntity.status(204).build();

        } catch (Exception ex) {

            kafkaProducer.send(ErrorCode.BAD_REQUEST, "Deletar Cliente", Status.FAILED);

            return ResponseEntity.status(500).body(new AppException(ErrorCode.BAD_REQUEST));
        }
    }
}
