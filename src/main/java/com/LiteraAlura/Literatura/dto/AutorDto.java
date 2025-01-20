package com.LiteraAlura.Literatura.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

public record AutorDto(
        @JsonAlias("name") String nombre,
        @JsonAlias("birth_year") Integer nacimiento,
        @JsonAlias("death_year") Integer fallecimiento
){
    @Override
    public String toString() {
        return String.format(
                "Nombre: %s\nNacimiento: %s\nFallecimiento: %s",
                nombre !=  null ? nombre : "N/A",
                nacimiento != null ? String.valueOf(nacimiento) : "N/A",
                fallecimiento != null ? String.valueOf(fallecimiento) : "N/A"
        );
    }
}
