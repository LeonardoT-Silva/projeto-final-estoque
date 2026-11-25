
package br.senai.sistema.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.senai.sistema.model.Produto;

/**
 * Acesso ao banco de dados para a entidade Produto.
 */
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    List<Produto> findByNomeContainingIgnoreCaseOrCodigoContainingIgnoreCase(
            String nome, String codigo);
}
