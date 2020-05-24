package br.com.projetoitau.contacorrente.controller;

import br.com.projetoitau.contacorrente.controller.dto.ClienteDTO;
import br.com.projetoitau.contacorrente.model.ClienteVO;
import br.com.projetoitau.contacorrente.model.ContaCorrenteVO;
import br.com.projetoitau.contacorrente.repository.ClienteRepository;
import br.com.projetoitau.contacorrente.repository.ContaCorrenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<ClienteVO> getAllClients() {
        return (List<ClienteVO>) clienteRepository.findAll();
    }

    @PostMapping("")
    public ClienteVO saveCliente(@RequestBody ClienteDTO clienteDTO) throws Exception {

        if (clienteDTO != null && !clienteDTO.getCpf_cnpj().trim().isEmpty()) {

//            if (clienteRepository.findById(clienteDTO.getCpf_cnpj()).isPresent()) {
//                throw new Exception("Cliente já existe");
//            }
        } else {
            throw new Exception("CPF_CNPJ não pode ser nulo");
        }

        ClienteVO clienteVO = new ClienteVO();

        clienteVO.setNome(clienteDTO.getNome());
        clienteVO.setCpf_cnpj(clienteDTO.getCpf_cnpj());
        clienteVO.setTipo_de_cliente(clienteDTO.getTipo_de_cliente());
        clienteVO.setEndereco(clienteDTO.getEndereco());
        clienteVO.setProfissao(clienteDTO.getProfissao());
        clienteVO.setRazao_social(clienteDTO.getRazao_social());
        clienteVO.setIncr_estadual(clienteDTO.getIncr_estadual());

        clienteVO = clienteRepository.save(clienteVO);

        ContaCorrenteVO contaCorrenteVO = new ContaCorrenteVO();

        contaCorrenteVO.setAgencia(String.valueOf(new Random().nextInt(9999)));
        contaCorrenteVO.setNum_conta(String.valueOf(new Random().nextInt(999999999)));
        contaCorrenteVO.setDac(Integer.parseInt(contaCorrenteVO.getAgencia().substring(contaCorrenteVO.getAgencia().length()-1)));
        contaCorrenteVO.setSaldo(0);

        contaCorrenteVO = contaCorrenteRepository.save(contaCorrenteVO);

        clienteVO.setNum_conta(contaCorrenteVO.getNum_conta());

        return clienteRepository.save(clienteVO);
    }

}
