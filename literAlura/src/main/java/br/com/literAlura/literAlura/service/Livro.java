package br.com.literAlura.literAlura.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Livro {

    private static final List<JsonNode> livrosRegistrados = new ArrayList<>();
    private static final String ENDERECO = "https://gutendex.com/books/";
    private final ConsumoApi consumoApi = new ConsumoApi();
    private final Autor autor = new Autor();

    //Metodo para buscar livro na API
    public void buscarLivroPorTitulo(String buscarTitulo) throws IOException, InterruptedException {
        String jsonTitulo = consumoApi.obterDados(ENDERECO + "?search=" + buscarTitulo.replace(" ", "%20"));
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(jsonTitulo);
        JsonNode booksNode = rootNode.get("results");

        if (booksNode != null && booksNode.isArray() && booksNode.size() > 0) {
            JsonNode firstBookNode = booksNode.get(0);
            livrosRegistrados.add(firstBookNode); // Adiciona o livro buscado a lista

            // Adiciona os autores à lista
            if (firstBookNode.has("authors") && firstBookNode.get("authors").isArray()) {
                for (JsonNode autorNode : firstBookNode.get("authors")) {
                    autor.adicionarAutor(autorNode); // Adiciona cada autor de forma individual
                }
            }

            String title = firstBookNode.has("title") ? firstBookNode.get("title").asText() : "Livro não encontrado";
            String author = firstBookNode.has("authors") && firstBookNode.get("authors").isArray() && firstBookNode.get("authors").size() > 0 ?
                    firstBookNode.get("authors").get(0).get("name").asText() : "Autor não encontrado";
            String language = firstBookNode.has("languages") && firstBookNode.get("languages").isArray() && firstBookNode.get("languages").size() > 0 ?
                    firstBookNode.get("languages").get(0).asText() : "Idioma não encontrado";
            int downloadCount = firstBookNode.has("formats") && firstBookNode.get("formats").has("application/json") &&
                    firstBookNode.get("formats").get("application/json").has("download_count") ?
                    firstBookNode.get("formats").get("application/json").get("download_count").asInt() : 0;

            System.out.println("Livro encontrado:");
            System.out.println("Título: " + title);
            System.out.println("Autor: " + author);
            System.out.println("Idioma: " + language);
            System.out.println("numero de Downloads: " + downloadCount);
        } else {
            System.out.println("Nenhum livro encontrado com o título: " + buscarTitulo);
        }
    }

    //Metodo para listar livros registrados na lista
    public void listarLivrosRegistrados() {
        if (livrosRegistrados.isEmpty()) {
            System.out.println("Nenhum livro registrado ainda.");
        } else {
            System.out.println("Livros Registrados:");
            for (JsonNode livroNode : livrosRegistrados) {
                String title = livroNode.has("title") ? livroNode.get("title").asText() : "Título não disponível";
                String author = livroNode.has("authors") && livroNode.get("authors").isArray() && livroNode.get("authors").size() > 0 ?
                        livroNode.get("authors").get(0).get("name").asText() : "Autor não disponível";

                System.out.println("Título: " + title + " | Autor: " + author);
            }
        }
    }

    //metodo para listar livros separados por idioma
    public void listarLivrosPorIdioma() {
        String[] idiomas = {"en", "pt", "es", "fr"};
        boolean livroEncontrado = false;

        for (String idioma : idiomas) {
            System.out.println("Livros no idioma: " + idioma);

            for (JsonNode livro : livrosRegistrados) {
                if (livro.has("languages") && livro.get("languages").isArray() && livro.get("languages").size() > 0) {
                    for (JsonNode langNode : livro.get("languages")) {
                        if (langNode.asText().equals(idioma)) {
                            String title = livro.has("title") ? livro.get("title").asText() : "livro não encontrado";
                            String author = livro.has("authors") && livro.get("authors").isArray() && livro.get("authors").size() > 0 ?
                                    livro.get("authors").get(0).get("name").asText() : "Autor não encontrado";
                            String language = langNode.asText();

                            System.out.println("Título: " + title + " | Autor: " + author + " | Idioma: " + language);
                            livroEncontrado = true;
                        }
                    }
                }
            }

            if (!livroEncontrado) {
                System.out.println("Nenhum livro encontrado no idioma: " + idioma);
            }
        }
    }
}
