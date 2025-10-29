package com.exemplo.livros.controller;

import com.exemplo.livros.model.Livro;
import com.exemplo.livros.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String salvar(Livro livro, RedirectAttributes redirectAttributes) {
        try {
            repo.save(livro);
            redirectAttributes.addFlashAttribute("mensagem", "Livro salvo com sucesso!");
            redirectAttributes.addFlashAttribute("tipo", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagem", "Erro ao salvar livro: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipo", "error");
        }
        return "redirect:/livros";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Livro livro = repo.findById(id).orElse(null);
        if (livro == null) {
            redirectAttributes.addFlashAttribute("mensagem", "Livro não encontrado!");
            redirectAttributes.addFlashAttribute("tipo", "error");
            return "redirect:/livros";
        }
        model.addAttribute("livro", livro);
        return "form";
    }

    @GetMapping("/visualizar/{id}")
    public String visualizar(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Livro livro = repo.findById(id).orElse(null);
        if (livro == null) {
            redirectAttributes.addFlashAttribute("mensagem", "Livro não encontrado!");
            redirectAttributes.addFlashAttribute("tipo", "error");
            return "redirect:/livros";
        }
        model.addAttribute("livro", livro);
        return "visualizar";
    }

    @PostMapping("/deletar/{id}")
    public String deletar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            if (repo.existsById(id)) {
                repo.deleteById(id);
                redirectAttributes.addFlashAttribute("mensagem", "Livro deletado com sucesso!");
                redirectAttributes.addFlashAttribute("tipo", "success");
            } else {
                redirectAttributes.addFlashAttribute("mensagem", "Livro não encontrado!");
                redirectAttributes.addFlashAttribute("tipo", "error");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagem", "Erro ao deletar livro: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipo", "error");
        }
        return "redirect:/livros";
    }
}