package br.com.unipe.pedidoservice.service;

import br.com.unipe.pedidoservice.model.Pedido;
import br.com.unipe.pedidoservice.model.StatusPedido;
import br.com.unipe.pedidoservice.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    public List<Pedido> findAll() {
        return pedidoRepository.findAll();
    }

    public Optional<Pedido> findById(Long id) {
        return pedidoRepository.findById(id);
    }

    public Pedido save(Pedido pedido) {
       
        pedido.setStatus(StatusPedido.CRIADO); 
        return pedidoRepository.save(pedido);
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