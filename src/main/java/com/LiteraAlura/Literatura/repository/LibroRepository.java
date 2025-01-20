package com.LiteraAlura.Literatura.repository;

import com.LiteraAlura.Literatura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface LibroRepository extends JpaRepository<Libro,Long> {
    List<Libro> findByTitulo(String titulo);
    List<Libro> findByIdioma(String idioma);
    @Query("SELECT COUNT(l) FROM Libro l WHERE l.idioma = :idioma")
    Map<String, Long> contarLibrosPorIdioma();

    long countByIdioma(String idioma);
}
