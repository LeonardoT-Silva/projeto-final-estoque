package br.senai.sistema.config;

import java.math.BigDecimal;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import br.senai.sistema.model.Categoria;
import br.senai.sistema.model.Produto;
import br.senai.sistema.model.Perfil;
import br.senai.sistema.model.Usuario;
import br.senai.sistema.repository.CategoriaRepository;
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

    public DadosIniciais(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder,
                         ProdutoRepository produtoRepository, CategoriaRepository categoriaRepository) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.produtoRepository = produtoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    @Override
    public void run(String... args) {
        if (usuarioRepository.count() == 0) {
            usuarioRepository.save(new Usuario("Administrador", "admin",
                    passwordEncoder.encode("admin123"), Perfil.ADMIN));
            usuarioRepository.save(new Usuario("Operador", "operador",
                    passwordEncoder.encode("operador123"), Perfil.OPERADOR));
        }

        if (produtoRepository.count() == 0) {
            Produto produto1 = new Produto("PRD-001", "Mouse USB", "UN", new BigDecimal("35.00"), 5, 20);
            Produto produto2 = new Produto("PRD-002", "Teclado USB", "UN", new BigDecimal("65.00"), 5, 15);
            Produto produto3 = new Produto("PRD-003", "Headset", "UN", new BigDecimal("90.00"), 3, 10);
            produtoRepository.save(produto1);
            produtoRepository.save(produto2);
            produtoRepository.save(produto3);
        }

        if (categoriaRepository.count() == 0) {
            Categoria categoria1 = new Categoria("Periféricos", "Produtos utilizados para conectar e interagir com computadores, como mouses e teclados.");
            Categoria categoria2 = new Categoria("Áudio", "Produtos de som para computadores, como headsets e fones de ouvido.");
            Categoria categoria3 = new Categoria("Armazenamento", "Produtos utilizados para armazenar e transferir arquivos digitais, como pen drives, SSDs e HDs.");
            categoriaRepository.save(categoria1);
            categoriaRepository.save(categoria2);
            categoriaRepository.save(categoria3);
        }
    }
}