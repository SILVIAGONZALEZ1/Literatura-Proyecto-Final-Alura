package com.LiteraAlura.Literatura.service;


import com.LiteraAlura.Literatura.dto.LibroDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Optional;

public class ConversorService {
    private static final ObjectMapper objetoMapper = new ObjectMapper();

    public static Optional<LibroDto> JsonALibroDto(String json) {
        JsonNode nodoRaiz;
        try {
            nodoRaiz = objetoMapper.readTree(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error al procesar el JSON", e);
        }

        if (esNodoConResultados(nodoRaiz)) {
            return procesarResultados(nodoRaiz);
        }

        if (esNodoDeLibro(nodoRaiz)) {
            return procesarLibro(nodoRaiz);
        }

        throw new RuntimeException("La estructura del JSON no coincide con la esperada.");
    }

    private static Optional<LibroDto> procesarResultados(JsonNode nodoResultados) {
        JsonNode listaResultados = nodoResultados.get("results");

        if (listaResultados.isArray() && !listaResultados.isEmpty()) {
            JsonNode primerResultado = listaResultados.get(0);
            return procesarLibro(primerResultado);
        }

        return Optional.empty();
    }

    private static Optional<LibroDto> procesarLibro(JsonNode nodoLibro) {
        JsonNode autorPrincipal = obtenerPrimerObjeto(nodoLibro, "authors");
        JsonNode idiomaPrincipal = obtenerPrimerObjeto(nodoLibro, "languages");

        LibroDto libroDto = convertirNodoJsonALibroDto(nodoLibro, autorPrincipal, idiomaPrincipal);

        return Optional.of(libroDto);
    }

    private static JsonNode obtenerPrimerObjeto(JsonNode nodoLibro, String nombrePropiedad) {
        JsonNode nodoPropiedad = nodoLibro.get(nombrePropiedad);

        if (nodoPropiedad.isArray() && !nodoPropiedad.isEmpty()) {
            return nodoPropiedad.get(0);
        }

        return null;
    }

    private static LibroDto convertirNodoJsonALibroDto(JsonNode nodoJsonLibro, JsonNode autor, JsonNode idioma) {
        ((ObjectNode) nodoJsonLibro).remove("authors");
        ((ObjectNode) nodoJsonLibro).set("authors", autor);
        ((ObjectNode) nodoJsonLibro).remove("languages");
        ((ObjectNode) nodoJsonLibro).set("languages", idioma);

        try {
            String r = objetoMapper.writeValueAsString(nodoJsonLibro);
            return objetoMapper.readValue(r, LibroDto.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error al convertir el nodo JSON en un objeto LibroDTO", e);
        }
    }

    private static boolean esNodoDeLibro(JsonNode nodo) {
        return contieneCamposNecesarios(nodo, "id", "title", "authors", "languages", "copyright", "media_type");
    }

    private static boolean esNodoConResultados(JsonNode nodo) {
        return contieneCamposNecesarios(nodo, "count", "next", "previous", "results");
    }

    private static boolean contieneCamposNecesarios(JsonNode nodo, String... campos) {
        for (String campo : campos) {
            if (!nodo.has(campo)) {
                return false;
            }
        }
        return true;
    }
}