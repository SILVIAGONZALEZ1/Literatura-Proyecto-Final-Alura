package com.LiteraAlura.Literatura.principal;

import com.LiteraAlura.Literatura.dto.LibroDto;
import com.LiteraAlura.Literatura.model.Autor;
import com.LiteraAlura.Literatura.model.Libro;
import com.LiteraAlura.Literatura.repository.AutorRepository;
import com.LiteraAlura.Literatura.repository.LibroRepository;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class PrincipalApp {

    private final LibroRepository libroRepository;
    private final AutorRepository autorRepository;
    private final Scanner escaner = new Scanner(System.in);

    public PrincipalApp(LibroRepository libroRepository, AutorRepository autorRepository) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }

    public void ejecutar() {
        int opcionMenu;
        while (true) {
            System.out.print("""
                    \s
                    =========================================================================== 
                    ||            Menú de búsqueda de libros en Project Gutenberg            ||
                    =========================================================================== 
                    ||                                                                       ||
                    ||   1. Buscar libro por título                                         ||
                    ||   2. Listar libros buscados anteriormente                            ||
                    ||   3. Listar libros buscados anteriormente por idioma                 ||
                    ||   4. Listar autores de los libros buscados anteriormente             ||
                    ||   5. Listar autores vivos en un determinado año                      ||
                    ||   6. Cantidad de libros por idioma                                   ||
                    ||                                                                       ||
                    ||   0. Salir                                                           ||
                    ||                                                                       ||
                    =========================================================================== 
                    \s
                    Elige una opción (0-6):\s""");
            opcionMenu = escanearOpcion();
            switch (opcionMenu) {
                case 0 -> {
                    System.out.println("¡Hasta luego!");
                    escaner.close();
                    System.exit(0);
                }
                case 1 -> buscarPrimerLibroPorTitulo();
                case 2 -> listarLibrosBuscadosAnteriormente();
                case 3 -> buscarPrimerLibroConAlMenosUnoDeLosIdiomasSeleccionados();
                case 4 -> listarAutoresDeLibrosBuscadosAnteriormente();
                case 5 -> listarAutoresVivosEnA();
                case 6 -> cantidadLibrosPorIdioma();
                default -> System.out.println("Opción inválida. Por favor, elige una opción entre 1 y 6.");
            }
        }
    }

    private void buscarPrimerLibroPorTitulo() {
        System.out.print("Escriba el título o parte del título del libro que desea buscar: ");
        String titulo = escaner.nextLine().trim();
        if (titulo.isEmpty()) {
            System.out.println("Búsqueda vacía. Por favor, ingrese un título válido.");
            return;
        }

        Optional<LibroDto> respuesta = Optional.empty(); // Aquí deberías usar el servicio real

        if (respuesta.isPresent()) {
            LibroDto libroDto = respuesta.get();
            System.out.println("¡Libro encontrado!");
            imprimir(libroDto);
            List<Libro> libroExistente = libroRepository.findByTitulo(libroDto.titulo());
            if (libroExistente.isEmpty()) {
                Libro libro = new Libro(libroDto);
                libroRepository.save(libro);
                System.out.println("Libro guardado con éxito: " + libro);
            }
        } else {
            System.out.println("No se encontró un libro con ese título.");
        }
    }

    private void listarLibrosBuscadosAnteriormente() {
        List<Libro> libros = libroRepository.findAll();
        if (libros.isEmpty()) {
            System.out.println("No se han encontrado libros buscados anteriormente.");
        } else {
            System.out.println("Listado de libros guardados previamente:");
            libros.forEach(this::imprimir);
        }
    }

    private void buscarPrimerLibroConAlMenosUnoDeLosIdiomasSeleccionados() {
        System.out.print("Escriba el idioma que desea buscar (ej. en, fr, es): ");
        String idioma = escaner.nextLine().trim();
        if (idioma.isEmpty()) {
            System.out.println("Búsqueda vacía. Por favor, ingrese un idioma válido.");
            return;
        }

        List<Libro> libros = libroRepository.findByIdioma(idioma);
        if (libros.isEmpty()) {
            System.out.println("No se encontraron libros en el idioma seleccionado.");
        } else {
            System.out.println("Libros encontrados en el idioma " + idioma + ":");
            libros.forEach(this::imprimir);
        }
    }

    private void listarAutoresDeLibrosBuscadosAnteriormente() {
        List<Libro> libros = libroRepository.findAll();
        if (libros.isEmpty()) {
            System.out.println("No se han encontrado libros buscados anteriormente.");
        } else {
            System.out.println("Autores de los libros buscados anteriormente:");
            libros.forEach(libro -> imprimir(libro.getAutor()));
        }
    }

    private void listarAutoresVivosEnA() {
        System.out.print("Ingrese el año para listar autores vivos en ese año: ");
        int año = escaner.nextInt();
        escaner.nextLine(); // Limpiar el buffer de entrada

        List<Autor> autores = autorRepository.findByNacimientoLessThanEqualAndFallecimientoGreaterThanEqualOrFallecimientoIsNull(año);
        if (autores.isEmpty()) {
            System.out.println("No se encontraron autores vivos en ese año.");
        } else {
            System.out.println("Autores vivos en el año " + año + ":");
            autores.forEach(this::imprimir);
        }
    }

    private void cantidadLibrosPorIdioma() {
        System.out.print("Ingrese el idioma para contar los libros (ej. en, fr, es): ");
        String idioma = escaner.nextLine().trim();
        if (idioma.isEmpty()) {
            System.out.println("Por favor, ingrese un idioma válido.");
            return;
        }

        long cantidad = libroRepository.countByIdioma(idioma);
        System.out.println("Cantidad de libros en el idioma " + idioma + ": " + cantidad);
    }

    private int escanearOpcion() {
        try {
            return escaner.nextInt();
        } catch (InputMismatchException e) {
            escaner.nextLine(); // Limpiar el buffer
            return -1;
        }
    }

    private <T> void imprimir(T objeto) {
        System.out.println("//////////////////////////////////////////////////////////////////////////////////////////////////");
        System.out.println(objeto);
        System.out.println("//////////////////////////////////////////////////////////////////////////////////////////////////");
    }
}
