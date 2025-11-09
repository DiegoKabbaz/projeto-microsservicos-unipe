package br.com.unipe.pedidoservice.rabbitmq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQProducer {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    @Value("${pagamento.rabbitmq.queue}")
    private String queueName;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public void sendMessage(Object message) {
        try {
            String jsonMessage = objectMapper.writeValueAsString(message);
            rabbitTemplate.convertAndSend(queueName, jsonMessage);
            System.out.println("Mensagem enviada para a fila " + queueName + ": " + jsonMessage);
        } catch (JsonProcessingException e) {
            System.err.println("Erro ao serializar a mensagem para JSON: " + e.getMessage());
        }
    }
}
