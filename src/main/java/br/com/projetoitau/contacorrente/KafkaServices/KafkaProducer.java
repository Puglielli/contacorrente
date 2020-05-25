package br.com.projetoitau.contacorrente.KafkaServices;

import br.com.projetoitau.contacorrente.utils.ErrorCode;
import br.com.projetoitau.contacorrente.model.ClienteVO;
import br.com.projetoitau.contacorrente.model.ContaCorrenteVO;
import br.com.projetoitau.contacorrente.model.HistoricoVO;
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

    public void send(ClienteVO clienteVO, String mensagem, Status status) throws JsonProcessingException {

        HistoricoVO historicoVO = new HistoricoVO();

        historicoVO.setId(UUID.randomUUID());
        historicoVO.setNum_conta(clienteVO.getNum_conta());
        historicoVO.setTipo_de_transacao(mensagem);
        historicoVO.setData(new Timestamp(System.currentTimeMillis()));
        historicoVO.setStatus(status.getCode());

        ObjectMapper mapper = new ObjectMapper();

        kafkaTemplate.send(orderTopic, mapper.writeValueAsString(historicoVO));
    }

    public void send(ContaCorrenteVO contaCorrenteVO, String mensagem, Status status) throws JsonProcessingException {

        HistoricoVO historicoVO = new HistoricoVO();

        historicoVO.setId(UUID.randomUUID());
        historicoVO.setNum_conta(contaCorrenteVO.getNum_conta());
        historicoVO.setTipo_de_transacao(mensagem);
        historicoVO.setData(new Timestamp(System.currentTimeMillis()));
        historicoVO.setStatus(status.getCode());

        ObjectMapper mapper = new ObjectMapper();

        kafkaTemplate.send(orderTopic, mapper.writeValueAsString(historicoVO));
    }

    public void send(ErrorCode errorCode, Status status) throws JsonProcessingException {

        HistoricoVO historicoVO = new HistoricoVO();

        historicoVO.setId(UUID.randomUUID());
        historicoVO.setNum_conta(null);
        historicoVO.setTipo_de_transacao(errorCode.getCode() + " - " + errorCode.getMessage());
        historicoVO.setData(new Timestamp(System.currentTimeMillis()));
        historicoVO.setStatus(status.getCode());

        ObjectMapper mapper = new ObjectMapper();

        kafkaTemplate.send(orderTopic, mapper.writeValueAsString(historicoVO));
    }
}