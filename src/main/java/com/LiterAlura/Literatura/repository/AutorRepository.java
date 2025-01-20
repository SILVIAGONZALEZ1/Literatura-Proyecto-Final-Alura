package com.LiterAlura.Literatura.repository;

import com.LiterAlura.Literatura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AutorRepository extends JpaRepository<Autor,Long> {
    List<Autor> findByNacimientoLessThanEqualAndFallecimientoGreaterThanEqualOrFallecimientoIsNull(int n, int f);
}
