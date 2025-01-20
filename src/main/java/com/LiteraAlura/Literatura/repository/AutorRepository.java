package com.LiteraAlura.Literatura.repository;

import com.LiteraAlura.Literatura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    List<Autor> findByNacimientoLessThanEqualAndFallecimientoGreaterThanEqualOrFallecimientoIsNull(LocalDate fechaNacimiento, LocalDate fechaFallecimiento);


    List<Autor> findByNacimientoLessThanEqualAndFallecimientoGreaterThanEqualOrFallecimientoIsNull(int año);
}
