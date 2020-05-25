package br.com.projetoitau.contacorrente.KafkaServices;

import br.com.projetoitau.contacorrente.controller.dto.HistoricoDTO;
import br.com.projetoitau.contacorrente.utils.ErrorCode;
import br.com.projetoitau.contacorrente.model.ClienteVO;
import br.com.projetoitau.contacorrente.model.ContaCorrenteVO;
import br.com.projetoitau.contacorrente.utils.Status;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.UUID;

@Component
public class KafkaProducer {

    @Value("${spring.kafka.consumer.topic}")
    private String orderTopic;

    private final KafkaTemplate kafkaTemplate;

    public KafkaProducer(final KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(ClienteVO clienteVO, String operacao, String transacao, Status status) throws JsonProcessingException {

        HistoricoDTO historicoDTO = new HistoricoDTO();

        historicoDTO.setId(UUID.randomUUID());
        historicoDTO.setNum_conta(clienteVO.getNum_conta());
        historicoDTO.setTipo_de_operacao(operacao);
        historicoDTO.setTipo_de_transacao(transacao);
        historicoDTO.setData(new Timestamp(System.currentTimeMillis()));
        historicoDTO.setStatus(status.getCode());

        ObjectMapper mapper = new ObjectMapper();

        kafkaTemplate.send(orderTopic, mapper.writeValueAsString(historicoDTO));
    }

    public void send(ContaCorrenteVO contaCorrenteVO, String operacao, String transacao, Status status) throws JsonProcessingException {

        HistoricoDTO historicoDTO = new HistoricoDTO();

        historicoDTO.setId(UUID.randomUUID());
        historicoDTO.setNum_conta(contaCorrenteVO.getNum_conta());
        historicoDTO.setTipo_de_operacao(operacao);
        historicoDTO.setTipo_de_transacao(transacao);
        historicoDTO.setData(new Timestamp(System.currentTimeMillis()));
        historicoDTO.setStatus(status.getCode());

        ObjectMapper mapper = new ObjectMapper();

        kafkaTemplate.send(orderTopic, mapper.writeValueAsString(historicoDTO));
    }

    public void send(ErrorCode errorCode, String operacao, Status status) throws JsonProcessingException {

        HistoricoDTO historicoDTO = new HistoricoDTO();

        historicoDTO.setId(UUID.randomUUID());
        historicoDTO.setNum_conta(null);
        historicoDTO.setTipo_de_operacao(operacao);
        historicoDTO.setTipo_de_transacao(errorCode.getCode() + " - " + errorCode.getMessage());
        historicoDTO.setData(new Timestamp(System.currentTimeMillis()));
        historicoDTO.setStatus(status.getCode());

        ObjectMapper mapper = new ObjectMapper();

        kafkaTemplate.send(orderTopic, mapper.writeValueAsString(historicoDTO));
    }
}