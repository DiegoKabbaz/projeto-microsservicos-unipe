package br.com.unipe.pagamentoservice.rabbitmq;

import br.com.unipe.pagamentoservice.dto.PagamentoRequestDTO;
import br.com.unipe.pagamentoservice.model.Pagamento;
import br.com.unipe.pagamentoservice.service.PagamentoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RabbitMQConsumer {

    @Autowired
    private PagamentoService pagamentoService;

    @RabbitListener(queues = "${pagamento.rabbitmq.queue}")
    public void receiveMessage(String message) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            PagamentoRequestDTO dto = mapper.readValue(message, PagamentoRequestDTO.class);
            
            // Processa o pagamento
            Pagamento pagamento = pagamentoService.registrarPagamento(dto);
            
            System.out.println("Pagamento processado e registrado: " + pagamento);
            
            // TODO: Implementar a chamada síncrona de volta para o pedido-service (Fluxo Extra Opcional)

        } catch (IOException e) {
            System.err.println("Erro ao desserializar a mensagem do RabbitMQ: " + e.getMessage());
        }
    }
}
