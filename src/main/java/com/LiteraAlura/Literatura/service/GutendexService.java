package com.LiteraAlura.Literatura.service;

import com.LiteraAlura.Literatura.dto.LibroDto;

import java.util.Optional;

public class GutendexService {
    private static final String URL_BASE = "https://gutendex.com/books/";

    public static Optional<LibroDto> consultarLibrosPorAutoresVivosDesde(int inicio) {
        return consultar("?author_year_start=" + inicio);
    }

    public static Optional<LibroDto> consultarLibrosPorAutoresVivosAntes(int fin) {
        return consultar("?author_year_end=" + fin);
    }

    public static Optional<LibroDto> consultarLibrosPorAutoresVivosEntre(int inicio, int fin) {
        return consultar("?author_year_start=" + inicio + "&author_year_end=" + fin);
    }

    public static Optional<LibroDto> consultarLibrosConDerechosDeAutor() {
        return consultar("?copyright=true");
    }

    public static Optional<LibroDto> consultarLibrosSinDerechosDeAutor() {
        return consultar("?copyright=false");
    }

    public static Optional<LibroDto> consultarLibrosConYSinDerechosDeAutor() {
        return consultar("?copyright=true,false");
    }

    public static Optional<LibroDto> consultarLibrosPorId(String parametros) {
        return consultar("?ids=" + parametros);
    }

    public static Optional<LibroDto> consultarLibrosPorIdioma(String parametros) {
        return consultar("?languages=" + parametros);
    }

    public static Optional<LibroDto> consultarLibrosPorTipoFormato(String parametro) {
        return consultar("?mime_type=" + parametro);
    }

    public static Optional<LibroDto> consultarCoincidenciasTituloAutor(String busqueda) {
        return consultar("?search=" + busqueda);
    }

    public static Optional<LibroDto> consultarLibrosOrdenadosPorIdAscendente() {
        return consultar("?sort=ascending");
    }

    public static Optional<LibroDto> consultarLibrosOrdenadosPorIdDescendente() {
        return consultar("?sort=descending");
    }

    public static Optional<LibroDto> consultarLibrosOrdenadosPorTotalDescargasDescendente() {
        return consultar("?sort=popular");
    }

    public static Optional<LibroDto> consultarCoincidenciasCategoriaTema(String busqueda) {
        return consultar("?topic=" + busqueda);
    }

    public static Optional<LibroDto> consultarLibroPorId(long id) {
        return consultar(String.valueOf(id) + "/");
    }

    private static Optional<LibroDto> consultar(String consulta) {
        return ConversorService.JsonALibroDto(ClienteService.obtenerRespuesta(URL_BASE + consulta));
    }
}