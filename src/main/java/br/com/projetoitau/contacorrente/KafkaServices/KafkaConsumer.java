package br.com.projetoitau.contacorrente.KafkaServices;

import br.com.projetoitau.contacorrente.model.HistoricoVO;
import br.com.projetoitau.contacorrente.repository.HistoricoRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@EnableKafka
@KafkaListener(topics = "bank-listener")
public class KafkaConsumer {

    @Autowired
    HistoricoRepository historicoRepository;

    @KafkaHandler
    public void consume(@Payload String obj) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();

        HistoricoVO historicoVO = mapper.readValue(obj, HistoricoVO.class);

        if (!historicoRepository.getHistoricoById(historicoVO.getId()).isEmpty()) {
            return ;
        }

        historicoRepository.save(historicoVO);

        System.out.println("Consumer Teste: " + obj);
    }
}
