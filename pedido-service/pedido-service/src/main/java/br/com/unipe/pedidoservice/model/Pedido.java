package br.com.unipe.pedidoservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private LocalDateTime dataPedido = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    private StatusPedido status = StatusPedido.CRIADO;

        @ElementCollection
    @CollectionTable(name="pedido_produtos", joinColumns = @JoinColumn(name
            = "pedido_id"))
    @Column(name = "produto_id")
    private List<Long> idProdutos;

    private BigDecimal valorTotal;
}