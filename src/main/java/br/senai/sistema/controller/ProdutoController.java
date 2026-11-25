
package br.senai.sistema.controller;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.senai.sistema.model.Produto;
import br.senai.sistema.service.CategoriaService;
import br.senai.sistema.service.FornecedorService;
import br.senai.sistema.service.ProdutoService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoService produtoService;
    private final CategoriaService categoriaService;
    private final FornecedorService fornecedorService;

    public ProdutoController(ProdutoService produtoService,
                             CategoriaService categoriaService,
                             FornecedorService fornecedorService) {
        this.produtoService = produtoService;
        this.categoriaService = categoriaService;
        this.fornecedorService = fornecedorService;
    }

    @GetMapping
public String listar(
        @RequestParam(required = false) String pesquisa,
        @RequestParam(defaultValue = "nomeAsc") String ordenacao,
        Model model) {

    model.addAttribute("produtos",
            produtoService.buscarPorNomeOuCodigo(pesquisa, ordenacao));

    model.addAttribute("pesquisa", pesquisa);
    model.addAttribute("ordenacao", ordenacao);

    return "produtos/lista";
}

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("produto", new Produto());
        carregarListas(model);
        return "produtos/form";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid @ModelAttribute("produto") Produto produto,
                         BindingResult resultado, Model model,
                         RedirectAttributes atributos) {
        if (resultado.hasErrors()) {
            carregarListas(model);
            return "produtos/form";
        }

        try {
            produtoService.salvar(produto);
        } catch (DataIntegrityViolationException e) {
            resultado.rejectValue("codigo", "duplicado",
                    "Já existe um registro com este código.");
            carregarListas(model);
            return "produtos/form";
        }

        atributos.addFlashAttribute("mensagem", "Produto salvo com sucesso.");
        return "redirect:/produtos";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("produto", produtoService.buscarPorId(id));
        carregarListas(model);
        return "produtos/form";
    }

    @PostMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id, RedirectAttributes atributos) {
        try {
            produtoService.excluir(id);
            atributos.addFlashAttribute("mensagem", "Produto excluído com sucesso.");
        } catch (DataIntegrityViolationException e) {
            atributos.addFlashAttribute("erro",
                    "Não é possível excluir: existem movimentações ligadas a este produto.");
        }
        return "redirect:/produtos";
    }

    private void carregarListas(Model model) {
        model.addAttribute("categorias", categoriaService.listarTodos());
        model.addAttribute("fornecedores", fornecedorService.listarTodos());
    }

    @PostMapping("/{id}/ativo")
    public String alternarAtivo(@PathVariable Long id, RedirectAttributes atributos) {
        produtoService.alternarAtivo(id);
        atributos.addFlashAttribute("mensagem", "Situação do produto atualizada.");
        return "redirect:/produtos";
    }
}
