package com.exemplo.livros.controller;

import com.exemplo.livros.model.Livro;
import com.exemplo.livros.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/livros")
public class LivroController {

    @Autowired
    private LivroRepository repo;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("livros", repo.findAll());
        return "listar";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("livro", new Livro());
        return "form";
    }

    @PostMapping
    public String salvar(Livro livro) {
        repo.save(livro);
        return "redirect:/livros";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("livro", repo.findById(id).orElse(new Livro()));
        return "form";
    }

    @GetMapping("/visualizar/{id}")
    public String visualizar(@PathVariable Long id, Model model) {
        model.addAttribute("livro", repo.findById(id).orElse(new Livro()));
        return "visualizar";
    }
}
