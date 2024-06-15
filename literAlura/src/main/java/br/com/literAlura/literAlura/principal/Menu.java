package br.com.literAlura.literAlura.principal;

import br.com.literAlura.literAlura.service.*;

import java.io.IOException;
import java.util.Scanner;

public class Menu {

    private static final Livro LIVRO = new Livro();
    private static final Autor AUTOR = new Autor();


    public static void exibeMenu() throws IOException, InterruptedException {
        Scanner input = new Scanner(System.in);

        while (true) {
            //Menu para interação no terminal
            System.out.println("----------------------------------------------");
            System.out.println("*** Selecione uma operação a ser realizada ***");
            System.out.println("----------------------------------------------");
            System.out.println("   | Opção 1 - Buscar livro pelo título                     |   ");
            System.out.println("   | Opção 2 - Listar livros registrados                    |   ");
            System.out.println("   | Opção 3 - Listar autores registrados                   |   ");
            System.out.println("   | Opção 4 - Listar autores vivos em um determinado ano   |   ");
            System.out.println("   | Opção 5 - Listar livros em um determinado idioma       |   ");
            System.out.println("   | Opção 6 - Sair                                         |   ");

            System.out.println("Digite a operação a ser realizada: ");
            int operacao = Integer.parseInt(input.nextLine());

            switch (operacao) {
                //Switch para selecionar operação a ser realizada
                case 1:
                    System.out.println("Insira o título do livro: ");
                    String buscarTitulo = input.nextLine();
                    LIVRO.buscarLivroPorTitulo(buscarTitulo);
                    break;
                case 2:
                    LIVRO.listarLivrosRegistrados();
                    break;
                case 3:
                    AUTOR.listarAutoresRegistrados();
                    break;
                case 4:
                    System.out.println("Digite o ano para verificar autores vivos: ");
                    int ano = Integer.parseInt(input.nextLine());
                    AUTOR.listarAutoresVivos(ano);
                    break;
                case 5:
                    LIVRO.listarLivrosPorIdioma();
                    break;
                case 6:
                    System.out.println("Obrigado por usar nossos serviços!");
                    return; // encerrar o programa
                default:
                    System.out.println("Opção inválida! ");
                    break;
            }
        }
    }
}
