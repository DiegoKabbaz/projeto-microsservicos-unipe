package br.com.unipe.pagamentoservice.repository;

import br.com.unipe.pagamentoservice.model.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {
}
