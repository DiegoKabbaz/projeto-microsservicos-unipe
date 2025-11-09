package br.com.unipe.pagamentoservice.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "pagamentos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal valor;

    private String codigoPagamento; // Código do pagamento gerado aleatoriamente

    private Long pedidoId; // ID do pedido associado

    @Enumerated(EnumType.STRING)
    private StatusPagamento status;

    private LocalDateTime dataExpiracao; // Data/hora atual + 24h
}
