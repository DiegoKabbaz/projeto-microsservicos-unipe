package br.com.unipe.pagamentoservice.controller;

import br.com.unipe.pagamentoservice.dto.PagamentoRequestDTO;
import br.com.unipe.pagamentoservice.model.Pagamento;
import br.com.unipe.pagamentoservice.service.PagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pagamentos")
public class PagamentoController {

    @Autowired
    private PagamentoService pagamentoService;

    @PostMapping("/registrarPagamento")
    public ResponseEntity<Pagamento> registrarPagamento(@RequestBody PagamentoRequestDTO dto) {
        Pagamento pagamento = pagamentoService.registrarPagamento(dto);
        return ResponseEntity.ok(pagamento);
    }
}
