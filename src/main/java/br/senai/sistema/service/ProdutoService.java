
package br.senai.sistema.service;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.senai.sistema.model.Produto;
import br.senai.sistema.repository.ProdutoRepository;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public List<Produto> listarTodos() {
        return produtoRepository.findAll(Sort.by("nome"));
    }

    public List<Produto> buscarPorNomeOuCodigo(String pesquisa) {
        if (pesquisa == null || pesquisa.isBlank()) {
            return listarTodos();
        }

        return produtoRepository
                .findByNomeContainingIgnoreCaseOrCodigoContainingIgnoreCase(
                        pesquisa.trim(), pesquisa.trim())
                .stream()
                .sorted((a, b) -> a.getNome().compareToIgnoreCase(b.getNome()))
                .toList();
    }

    public Produto buscarPorId(Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado: " + id));
    }

    public Produto salvar(Produto produto) {
        return produtoRepository.save(produto);
    }

    public void excluir(Long id) {
        produtoRepository.deleteById(id);
    }

    public void alternarAtivo(Long id) {
        Produto produto = buscarPorId(id);
        produto.setAtivo(!produto.getAtivo());
        produtoRepository.save(produto);
    }
}
