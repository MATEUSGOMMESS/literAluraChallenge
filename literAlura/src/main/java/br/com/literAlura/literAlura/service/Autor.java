package br.com.literAlura.literAlura.service;

import com.fasterxml.jackson.databind.JsonNode;
import java.util.ArrayList;
import java.util.List;

public class Autor {

    private static final List<JsonNode> autoresRegistrados = new ArrayList<>();

    public void adicionarAutor(JsonNode autor) {
        autoresRegistrados.add(autor);
    }

    //Metodo para listar autores registrados na lista
    public void listarAutoresRegistrados() {
        if (autoresRegistrados.isEmpty()) {
            System.out.println("Nenhum autor registrado ainda.");
        } else {
            System.out.println("Autores Registrados:");
            for (JsonNode autorNode : autoresRegistrados) {
                String nome = autorNode.has("name") ? autorNode.get("name").asText() : "Nome do autor não disponível";
                int birthYear = autorNode.has("birth_year") ? autorNode.get("birth_year").asInt() : 0;
                int deathYear = autorNode.has("death_year") ? autorNode.get("death_year").asInt() : 0;

                System.out.println("Nome: " + nome);
                System.out.println("Ano de nascimento: " + (birthYear != 0 ? birthYear : "Informação não disponível"));
                System.out.println("Ano de falecimento: " + (deathYear != 0 ? deathYear : "Informação não disponível"));
            }
        }
    }

    //Metodo para listar autores vivos em determinado ano escolhido pelo usuário
    public void listarAutoresVivos(int ano) {
        boolean autorEncontrado = false;
        System.out.println("Autores vivos no ano " + ano + ":");
        for (JsonNode autor : autoresRegistrados) {
            int birthYear = autor.has("birth_year") ? autor.get("birth_year").asInt() : 0;
            int deathYear = autor.has("death_year") ? autor.get("death_year").asInt() : 0;

            if (birthYear <= ano && (deathYear == 0 || deathYear >= ano)) {
                String nome = autor.has("name") ? autor.get("name").asText() : "Nome não disponível";
                System.out.println("Nome: " + nome + " | Ano de nascimento: " + (birthYear != 0 ? birthYear : "Informação não disponível") + " | Ano de falecimento: " + (deathYear != 0 ? deathYear : "Informação não disponível"));
                autorEncontrado = true;
            }
        }

        if (!autorEncontrado) {
            System.out.println("Nenhum autor encontrado vivo no ano " + ano);
        }
    }
}
