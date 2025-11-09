package br.com.unipe.pagamentoservice.service;

import br.com.unipe.pagamentoservice.dto.PagamentoRequestDTO;
import br.com.unipe.pagamentoservice.model.Pagamento;
import br.com.unipe.pagamentoservice.model.StatusPagamento;
import br.com.unipe.pagamentoservice.repository.PagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PagamentoService {

    @Autowired
    private PagamentoRepository pagamentoRepository;

    public Pagamento registrarPagamento(PagamentoRequestDTO dto) {
        Pagamento pagamento = new Pagamento();
        pagamento.setValor(dto.getValor());
        pagamento.setPedidoId(dto.getPedidoId());
        pagamento.setCodigoPagamento(UUID.randomUUID().toString());
        pagamento.setStatus(StatusPagamento.CONFIRMADO);
        pagamento.setDataExpiracao(LocalDateTime.now().plusHours(24));

        return pagamentoRepository.save(pagamento);
    }
}
