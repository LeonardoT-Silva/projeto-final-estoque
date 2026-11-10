package br.senai.sistema.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.senai.sistema.model.Categoria;

/**
 * Acesso ao banco de dados para a entidade Categoria.
 *
 * Basta declarar a interface: o Spring Data cria a implementação sozinho,
 * com métodos prontos como findAll, findById, save, deleteById e count.
 * JpaRepository<Categoria, Long>  ->  entidade Categoria, chave primária do tipo Long.
 */
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    // Consulta derivada do nome do método: o Spring Data monta o SQL sozinho.
    // "Containing" = contém o texto; "IgnoreCase" = sem diferenciar maiúsculas; "OrderByNome" = ordenado.
    List<Categoria> findByNomeContainingIgnoreCaseOrderByNome(String nome);
}