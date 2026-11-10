package br.senai.sistema.config;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import br.senai.sistema.model.Categoria;
import br.senai.sistema.model.Fornecedor;
import br.senai.sistema.model.Movimentacao;
import br.senai.sistema.model.Perfil;
import br.senai.sistema.model.Produto;
import br.senai.sistema.model.TipoMovimentacao;
import br.senai.sistema.model.Usuario;
import br.senai.sistema.repository.CategoriaRepository;
import br.senai.sistema.repository.FornecedorRepository;
import br.senai.sistema.repository.MovimentacaoRepository;
import br.senai.sistema.repository.ProdutoRepository;
import br.senai.sistema.repository.UsuarioRepository;

/**
 * Carga inicial de dados: executa uma vez sempre que a aplicação sobe.
 * Cada bloco só insere os dados se a tabela ainda estiver vazia (por isso não duplica).
 */
@Component
public class DadosIniciais implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final ProdutoRepository produtoRepository;
    private final CategoriaRepository categoriaRepository;
    private final MovimentacaoRepository movimentacaoRepository;
    private final FornecedorRepository fornecedorRepository;

    public DadosIniciais(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder,
                         ProdutoRepository produtoRepository, CategoriaRepository categoriaRepository,
                         MovimentacaoRepository movimentacaoRepository,
                         FornecedorRepository fornecedorRepository) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.produtoRepository = produtoRepository;
        this.categoriaRepository = categoriaRepository;
        this.movimentacaoRepository = movimentacaoRepository;
        this.fornecedorRepository = fornecedorRepository;
    }

    @Override
    public void run(String... args) {
        if (usuarioRepository.count() == 0) {
            usuarioRepository.save(new Usuario("Administrador", "admin",
                    passwordEncoder.encode("admin123"), Perfil.ADMIN));
            usuarioRepository.save(new Usuario("Almoxarife", "operador",
                    passwordEncoder.encode("operador123"), Perfil.ALMOXARIFE));
        }

        if (categoriaRepository.count() == 0) {
            Categoria categoria1 = new Categoria("Periféricos", "Produtos utilizados para conectar e interagir com computadores, como mouses e teclados.");
            Categoria categoria2 = new Categoria("Áudio", "Fone de ouvido com microfone para chamadas, jogos e uso no computador.");
            Categoria categoria3 = new Categoria("Memórias", "Produtos utilizados para armazenar e transferir arquivos digitais, como pen drives, SSDs e HDs.");
            categoriaRepository.save(categoria1);
            categoriaRepository.save(categoria2);
            categoriaRepository.save(categoria3);
        }

        if (produtoRepository.count() == 0) {
            Produto produto1 = new Produto("PRD-001", "Mouse USB", "UN", new BigDecimal("45.00"), 5, 20);
            Produto produto2 = new Produto("PRD-002", "Teclado USB", "UN", new BigDecimal("80.00"), 5,15);
            Produto produto3 = new Produto("PRD-003", "Headset", "UN", new BigDecimal("90.00"), 3, 10);
            produto1.setLocalizacao("Prateleira A1");
            produto2.setLocalizacao("Armário 2");
            produto3.setLocalizacao("Prateleira B3");
            // Cada produto recebe uma categoria (relacionamento N:1)
            List<Categoria> categorias = categoriaRepository.findAll(Sort.by("id"));
            if (!categorias.isEmpty()) {
                produto1.setCategoria(categorias.get(0));
                produto2.setCategoria(categorias.get(1 % categorias.size()));
                produto3.setCategoria(categorias.get(2 % categorias.size()));
            }
            produtoRepository.save(produto1);
            produtoRepository.save(produto2);
            produtoRepository.save(produto3);
        }

        // Operação principal: cada registro aponta para registros que já existem (relacionamento).
        // O "%" (resto da divisão) evita erro se existirem menos de 3 registros cadastrados.
        if (movimentacaoRepository.count() == 0) {
            List<Produto> produtos = produtoRepository.findAll(Sort.by("id"));
            if (!produtos.isEmpty()) {
                Movimentacao movimentacao1 = new Movimentacao(TipoMovimentacao.ENTRADA, 200, LocalDate.of(2026, 10, 14),
                        produtos.get(0));
                movimentacao1.setObservacao("Compra do fornecedor");
                Movimentacao movimentacao2 = new Movimentacao(TipoMovimentacao.SAIDA, 2, LocalDate.of(2026, 10, 15),
                        produtos.get(1 % produtos.size()));
                movimentacao2.setObservacao("Retirada pela equipe de manutenção");
                Movimentacao movimentacao3 = new Movimentacao(TipoMovimentacao.ENTRADA, 10, LocalDate.of(2026, 10, 16),
                        produtos.get(2 % produtos.size()));
                movimentacaoRepository.save(movimentacao1);
                movimentacaoRepository.save(movimentacao2);
                movimentacaoRepository.save(movimentacao3);
            }
        }

        // Nova entidade (guia de personalização)
        if (fornecedorRepository.count() == 0) {
            Fornecedor fornecedor1 = new Fornecedor("Oderço Distribuidora", "12.345.678/0001-90", "(43) 3321-1000");
            Fornecedor fornecedor2 = new Fornecedor("Hayamax", "23.456.789/0001-01", "(43) 3322-2000");
            Fornecedor fornecedor3 = new Fornecedor("RLS Comércio de Informática", "34.567.890/0001-12", "(43) 3323-3000");
            fornecedorRepository.save(fornecedor1);
            fornecedorRepository.save(fornecedor2);
            fornecedorRepository.save(fornecedor3);
        }
    }
}