package br.com.projetoitau.contacorrente.utils;

import br.com.projetoitau.contacorrente.controller.dto.ClienteDTO;
import br.com.projetoitau.contacorrente.controller.dto.ContaCorrenteDTO;
import br.com.projetoitau.contacorrente.controller.dto.HistoricoDTO;
import br.com.projetoitau.contacorrente.model.ClienteVO;
import br.com.projetoitau.contacorrente.model.ContaCorrenteVO;
import br.com.projetoitau.contacorrente.model.HistoricoVO;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BaseResource {

    public static String generateNewAccount() {
        return String.valueOf(new Random().nextInt(999999999 - 111111111) + 1 + 111111111).replaceAll("/\\D/g", "");
    }

    public static String generateNewAgency() {
        return String.valueOf(new Random().nextInt(9999 - 1111) + 1 + 1111);
    }

    public static String formatAccount(String num_conta) {
        num_conta = num_conta.replaceAll("/\\D/g", "");

        return  num_conta.replaceAll("([0-9]{8})([0-9]{1})", "$1-$2");
    }

    public static Integer getDac(String agency) {
        return Integer.parseInt(agency.replaceAll("([0-9]{3})([0-9]{1})", "$2"));
    }

    public static HistoricoDTO toHistoricoDTO(HistoricoVO historicoVO) {
        HistoricoDTO historicoDTO = new HistoricoDTO();

        historicoDTO.setId(historicoVO.getId());
        historicoDTO.setNum_conta(historicoVO.getNum_conta());
        historicoDTO.setTipo_de_operacao(historicoVO.getTipo_de_operacao());
        historicoDTO.setTipo_de_transacao(historicoVO.getTipo_de_transacao());
        historicoDTO.setData(historicoVO.getData());
        historicoDTO.setStatus(historicoVO.getStatus());

        return historicoDTO;
    }

    public static List<HistoricoDTO> toHistoricoDTOList(List<HistoricoVO> historicoVOS) {

        List<HistoricoDTO> historicoDTOS = new ArrayList<>(0);

        for (HistoricoVO vo : historicoVOS) {

            historicoDTOS.add(toHistoricoDTO(vo));
        }

        return historicoDTOS;
    }

    public static ClienteDTO toClienteDTO(ClienteVO clienteVO) {
        ClienteDTO clienteDTO = new ClienteDTO();

        clienteDTO.setNome(clienteVO.getNome());
        clienteDTO.setCpf_cnpj(clienteVO.getCpf_cnpj());
        clienteDTO.setTipo_de_cliente(clienteVO.getTipo_de_cliente());
        clienteDTO.setEndereco(clienteVO.getEndereco());
        clienteDTO.setProfissao(clienteVO.getProfissao());
        clienteDTO.setRazao_social(clienteVO.getRazao_social());
        clienteDTO.setInscr_estadual(clienteVO.getInscr_estadual());
        clienteDTO.setNum_conta(clienteVO.getNum_conta());
        clienteDTO.setAtivo(clienteVO.getAtivo());

        return clienteDTO;
    }

    public static List<ClienteDTO> toClienteDTOList(List<ClienteVO> clienteVOS) {
        List<ClienteDTO> clienteDTOS = new ArrayList<>(0);

        for (ClienteVO vo : clienteVOS) {

            if (vo.getAtivo().equals(Status.ACTIVE.getCode())) {

                clienteDTOS.add(toClienteDTO(vo));
            }
        }

        return clienteDTOS;
    }

    public static ClienteVO toClienteVO(ClienteDTO clienteDTO) {
        ValidateCPFCNPJ cpfcnpj = new ValidateCPFCNPJ(clienteDTO.getCpf_cnpj());

        ClienteVO clienteVO = new ClienteVO();

        clienteVO.setNome(clienteDTO.getNome());
        clienteVO.setCpf_cnpj(cpfcnpj.getCpfCnpj());
        clienteVO.setTipo_de_cliente(cpfcnpj.isPJ() ? "PJ" : "PF");
        clienteVO.setEndereco(clienteDTO.getEndereco());
        clienteVO.setProfissao(clienteDTO.getProfissao());
        clienteVO.setRazao_social(clienteDTO.getRazao_social());
        clienteVO.setInscr_estadual(clienteDTO.getInscr_estadual());
        clienteVO.setAtivo(Status.ACTIVE.getCode());

        return clienteVO;
    }

    public static ContaCorrenteDTO toContaCorrentDTO(ContaCorrenteVO contaCorrenteVO) {
        ContaCorrenteDTO contaCorrenteDTO = new ContaCorrenteDTO();

        contaCorrenteDTO.setNum_conta(contaCorrenteVO.getNum_conta());
        contaCorrenteDTO.setAgencia(contaCorrenteVO.getAgencia());
        contaCorrenteDTO.setDac(contaCorrenteVO.getDac());
        contaCorrenteDTO.setSaldo(contaCorrenteVO.getSaldo());
        contaCorrenteDTO.setAtivo(contaCorrenteVO.getAtivo());

        return contaCorrenteDTO;
    }

    public static List<ContaCorrenteDTO> toContaCorrentDTOList(List<ContaCorrenteVO> contaCorrenteVO) {
        List<ContaCorrenteDTO> contaCorrenteDTOS = new ArrayList<>(0);

        for (ContaCorrenteVO vo : contaCorrenteVO) {

            if (vo.getAtivo().equals(Status.ACTIVE.getCode())) {

                contaCorrenteDTOS.add(toContaCorrentDTO(vo));
            }
        }

        return contaCorrenteDTOS;
    }
}
