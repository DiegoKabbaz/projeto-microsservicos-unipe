package br.com.unipe.pedidoservice.client;

import br.com.unipe.pedidoservice.dto.ProdutoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "produto-service")
public interface ProdutoClient {

    @GetMapping("/produtos/consultarPorIds")
    List<ProdutoDTO> consultarProdutosPorIds(@RequestParam("ids") List<Long> ids);
}
