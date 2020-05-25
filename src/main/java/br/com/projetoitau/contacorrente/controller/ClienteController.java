package br.com.projetoitau.contacorrente.controller;

import br.com.projetoitau.contacorrente.exception.AppException;
import br.com.projetoitau.contacorrente.exception.ErrorCode;
import br.com.projetoitau.contacorrente.controller.dto.ClienteDTO;
import br.com.projetoitau.contacorrente.model.ClienteVO;
import br.com.projetoitau.contacorrente.model.ContaCorrenteVO;
import br.com.projetoitau.contacorrente.repository.ClienteRepository;
import br.com.projetoitau.contacorrente.repository.ContaCorrenteRepository;
import br.com.projetoitau.contacorrente.utils.ValidateCPFCNPJ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("cliente")
public class ClienteController {

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    ContaCorrenteRepository contaCorrenteRepository;

    @GetMapping("")
    public ResponseEntity getAllClients() {
        try {

            return ResponseEntity.ok().body((List<ClienteVO>) clienteRepository.findAll());

        } catch (Exception ex) {

            return ResponseEntity.status(500).body(new AppException(ErrorCode.BAD_REQUEST));
        }
    }

    @GetMapping("/{cpf_cnpj}")
    public ResponseEntity getClienteByPK(@PathVariable(value = "cpf_cnpj") String cpf_cnpj) {
        try {

            ValidateCPFCNPJ cpfcnpj = new ValidateCPFCNPJ(cpf_cnpj);

            if (cpfcnpj.getCpfCnpj() != null && clienteRepository.getClienteByCPFCNPJ(cpfcnpj.getCpfCnpj()).isEmpty()) {

                return ResponseEntity.status(404).body(new AppException(ErrorCode.CPF_CNPJ_NOT_FOUND));
            }

            ClienteVO clienteVO = clienteRepository.getClienteByCPFCNPJ(cpfcnpj.getCpfCnpj()).get(0);

            return ResponseEntity.ok().body(clienteVO);

        } catch (Exception ex) {

            return ResponseEntity.status(500).body(new AppException(ErrorCode.BAD_REQUEST));
        }
    }

    @PostMapping("")
    public ResponseEntity saveCliente(@RequestBody ClienteDTO clienteDTO) {

        try {

            ValidateCPFCNPJ cpfcnpj = new ValidateCPFCNPJ(clienteDTO.getCpf_cnpj());

            if (clienteDTO != null && cpfcnpj.getCpfCnpj() != null) {

                if (!clienteRepository.getClienteByCPFCNPJ(cpfcnpj.getCpfCnpj()).isEmpty()) {

                    return ResponseEntity.status(400).body(new AppException(ErrorCode.CPF_CNPJ_ALREADY_EXISTS));

                } else if (!new ValidateCPFCNPJ(clienteDTO.getCpf_cnpj()).isValid()) {

                    return ResponseEntity.status(400).body(new AppException(ErrorCode.CPF_CNPJ_INVALID));
                }

            } else {

                return ResponseEntity.status(400).body(new AppException(ErrorCode.CPF_CNPJ_CANNOT_BE_NULL_OR_EMPTY));
            }

            ClienteVO clienteVO = new ClienteVO();

            clienteVO.setNome(clienteDTO.getNome());
            clienteVO.setCpf_cnpj(cpfcnpj.getCpfCnpj());
            clienteVO.setTipo_de_cliente(cpfcnpj.isPJ() ? "PJ" : "PF");
            clienteVO.setEndereco(clienteDTO.getEndereco());
            clienteVO.setProfissao(clienteDTO.getProfissao());
            clienteVO.setRazao_social(clienteDTO.getRazao_social());
            clienteVO.setIncr_estadual(clienteDTO.getIncr_estadual());

            clienteVO = clienteRepository.save(clienteVO);

            ContaCorrenteVO contaCorrenteVO = new ContaCorrenteVO();

            contaCorrenteVO.setAgencia(String.valueOf(new Random().nextInt(9999 - 1111) + 1 + 1111));

            String num_conta = String.valueOf(new Random().nextInt(999999999 - 111111111) + 1 + 111111111).replaceAll("/\\D/g", "");
            num_conta = num_conta.replaceAll("([0-9]{8})([0-9]{1})", "$1-$2");

            contaCorrenteVO.setNum_conta(num_conta);
            contaCorrenteVO.setDac(Integer.parseInt(contaCorrenteVO.getAgencia().replaceAll("([0-9]{3})([0-9]{1})", "$2")));
            contaCorrenteVO.setSaldo(0);

            contaCorrenteVO = contaCorrenteRepository.save(contaCorrenteVO);

            clienteVO.setNum_conta(contaCorrenteVO.getNum_conta());

            clienteVO = clienteRepository.save(clienteVO);

            return ResponseEntity.ok().body(clienteVO);

        } catch (Exception ex) {

            return ResponseEntity.status(500).body(new AppException(ErrorCode.BAD_REQUEST));
        }
    }

    @PutMapping("/{cpf_cnpj}")
    public ResponseEntity atualizaCliente(@PathVariable(value = "cpf_cnpj") String cpf_cnpj, @RequestBody ClienteDTO clienteDTO) {

        try {

            ValidateCPFCNPJ cpfcnpj = new ValidateCPFCNPJ(cpf_cnpj);

            if (cpfcnpj.getCpfCnpj() != null && clienteRepository.getClienteByCPFCNPJ(cpfcnpj.getCpfCnpj()).isEmpty()) {

                return ResponseEntity.status(404).body(new AppException(ErrorCode.CPF_CNPJ_NOT_FOUND));
            }

            ClienteVO clienteVO = clienteRepository.getClienteByCPFCNPJ(cpfcnpj.getCpfCnpj()).get(0);

            clienteVO.setNome(clienteDTO.getNome());
            clienteVO.setEndereco(clienteDTO.getEndereco());
            clienteVO.setProfissao(clienteDTO.getProfissao());
            clienteVO.setRazao_social(clienteDTO.getRazao_social());
            clienteVO.setIncr_estadual(clienteDTO.getIncr_estadual());

            clienteRepository.save(clienteVO);

            return ResponseEntity.status(204).build();

        } catch (Exception ex) {

            return ResponseEntity.status(500).body(new AppException(ErrorCode.BAD_REQUEST));
        }
    }

    @DeleteMapping("/{cpf_cnpj}")
    public ResponseEntity deletarCliente(@PathVariable(value = "cpf_cnpj") String cpf_cnpj) {
        try {

            ValidateCPFCNPJ cpfcnpj = new ValidateCPFCNPJ(cpf_cnpj);

            if (clienteRepository.getClienteByCPFCNPJ(cpfcnpj.getCpfCnpj()).isEmpty()) {

                return ResponseEntity.status(404).body(new AppException(ErrorCode.CPF_CNPJ_NOT_FOUND));
            }

            ClienteVO clienteVO = clienteRepository.getClienteByCPFCNPJ(cpfcnpj.getCpfCnpj()).get(0);

            clienteRepository.delete(clienteVO);

            return ResponseEntity.status(204).build();

        } catch (Exception ex) {

            return ResponseEntity.status(500).body(new AppException(ErrorCode.BAD_REQUEST));
        }
    }
}
