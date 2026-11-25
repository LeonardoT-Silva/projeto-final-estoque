
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

    public List<Produto> buscarPorNomeOuCodigo(String pesquisa, String ordenacao) {

    Sort sort = switch (ordenacao) {
        case "nomeDesc" -> Sort.by(Sort.Direction.DESC, "nome");
        case "codigo" -> Sort.by(Sort.Direction.ASC, "codigo");
        case "saldo" -> Sort.by(Sort.Direction.ASC, "saldo");
        default -> Sort.by(Sort.Direction.ASC, "nome");
    };

    if (pesquisa == null || pesquisa.isBlank()) {
        return produtoRepository.findAll(sort);
    }

    String termo = pesquisa.trim();

    return produtoRepository
            .findByNomeContainingIgnoreCaseOrCodigoContainingIgnoreCase(
                    termo, termo, sort);
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
