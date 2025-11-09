package br.com.unipe.pedidoservice.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PagamentoRequestDTO {
    private BigDecimal valor;
    private Long pedidoId;
}
