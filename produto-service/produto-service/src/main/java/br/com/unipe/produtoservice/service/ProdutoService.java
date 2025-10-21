package br.com.unipe.produtoservice.service;

import br.com.unipe.produtoservice.model.Produto;
import br.com.unipe.produtoservice.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public List<Produto> findAll() {
        return produtoRepository.findAll();
    }

    public Optional<Produto> findById(Long id) {
        return produtoRepository.findById(id);
    }

    public Produto save(Produto produto) {
        return produtoRepository.save(produto);
    }

    public void deleteById(Long id) {
        produtoRepository.deleteById(id);
    }
    
    public Optional<Produto> update(Long id, Produto produtoDetails) {
        return produtoRepository.findById(id)
                .map(produto -> {
                    produto.setNome(produtoDetails.getNome());
                    produto.setQuantidade(produtoDetails.getQuantidade());
                    produto.setDescricao(produtoDetails.getDescricao());
                    produto.setPreco(produtoDetails.getPreco());
                    return produtoRepository.save(produto);
                });
    }
}