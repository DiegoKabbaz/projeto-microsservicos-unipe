package br.com.unipe.pedidoservice.service;

import br.com.unipe.pedidoservice.model.Pedido;
import br.com.unipe.pedidoservice.model.StatusPedido;
import br.com.unipe.pedidoservice.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.unipe.pedidoservice.client.ProdutoClient;
import br.com.unipe.pedidoservice.dto.PagamentoRequestDTO;
import br.com.unipe.pedidoservice.dto.ProdutoDTO;
import br.com.unipe.pedidoservice.rabbitmq.RabbitMQProducer;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ProdutoClient produtoClient;

    @Autowired
    private RabbitMQProducer rabbitMQProducer;

    public List<Pedido> findAll() {
        return pedidoRepository.findAll();
    }

    public Optional<Pedido> findById(Long id) {
        return pedidoRepository.findById(id);
    }

    public Pedido save(Pedido pedido) {
        // 1. Chamada síncrona ao serviço de produtos para consultar todos os IDs
        List<Long> idsProdutos = pedido.getIdProdutos();
        List<ProdutoDTO> produtos = produtoClient.consultarProdutosPorIds(idsProdutos);

        // 2. Validar disponibilidade e calcular valor total
        BigDecimal valorTotal = BigDecimal.ZERO;
        for (ProdutoDTO produto : produtos) {
            // Simplificação: Assumindo que a quantidade pedida é 1 para cada ID na lista
            if (produto.getQuantidadeDisponivel() <= 0) {
                throw new RuntimeException("Produto " + produto.getNome() + " indisponível.");
            }
            valorTotal = valorTotal.add(produto.getPreco());
        }
        pedido.setValorTotal(valorTotal);

        // 3. Persistir o pedido
        pedido.setStatus(StatusPedido.CRIADO);
        Pedido pedidoSalvo = pedidoRepository.save(pedido);

        // 4. Chamada assíncrona ao serviço de pagamentos
        PagamentoRequestDTO pagamentoDTO = new PagamentoRequestDTO();
        pagamentoDTO.setValor(valorTotal);
        pagamentoDTO.setPedidoId(pedidoSalvo.getId());
        rabbitMQProducer.sendMessage(pagamentoDTO);

        return pedidoSalvo;
    }

    public void deleteById(Long id) {
        pedidoRepository.deleteById(id);
    }
    
    
    public Optional<Pedido> confirmarPedido(Long id) {
        return pedidoRepository.findById(id)
                .map(pedido -> {
                    pedido.setStatus(StatusPedido.CONFIRMADO);
                    return pedidoRepository.save(pedido);
                });
    }


    public Optional<Pedido> cancelarPedido(Long id) {
        return pedidoRepository.findById(id)
                .map(pedido -> {
                    pedido.setStatus(StatusPedido.CANCELADO);
                    return pedidoRepository.save(pedido);
                });
    }
}