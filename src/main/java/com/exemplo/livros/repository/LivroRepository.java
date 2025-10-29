package com.exemplo.livros.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.exemplo.livros.model.Livro;

public interface LivroRepository extends JpaRepository<Livro, Long> {}
