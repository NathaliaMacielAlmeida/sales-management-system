import dao.ProdutoDAO;
import model.Produto;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        ProdutoDAO dao = new ProdutoDAO();

        int opcao;

        do {
            System.out.println("\n=== SISTEMA DE VENDAS ===");
            System.out.println("1 - Listar Produtos");
            System.out.println("2 - Inserir Produto");
            System.out.println("3 - Atualizar Produto");
            System.out.println("4 - Deletar Produto");
            System.out.println("5 - Realizar Venda");
            System.out.println("0 - Sair");
            System.out.print("Escolha: ");

            opcao = sc.nextInt();

            switch (opcao) {

                case 1:
                    List<Produto> produtos = dao.listarProdutos();

                    for (Produto p : produtos) {
                        System.out.println(
                            "ID: " + p.getId() +
                            " | Nome: " + p.getNome() +
                            " | Preço: " + p.getPreco() +
                            " | Qtd: " + p.getQuantidade()
                        );
                    }
                    break;

                case 2:
                    Produto novo = new Produto();

                    sc.nextLine();

                    System.out.print("Nome: ");
                    novo.setNome(sc.nextLine());

                    System.out.print("Preço: ");
                    novo.setPreco(sc.nextDouble());

                    System.out.print("Quantidade: ");
                    novo.setQuantidade(sc.nextInt());

                    dao.inserirProduto(novo);
                    break;

                case 3:
                    Produto atualizar = new Produto();

                    System.out.print("ID: ");
                    atualizar.setId(sc.nextInt());

                    sc.nextLine();

                    System.out.print("Novo nome: ");
                    atualizar.setNome(sc.nextLine());

                    System.out.print("Novo preço: ");
                    atualizar.setPreco(sc.nextDouble());

                    System.out.print("Nova quantidade: ");
                    atualizar.setQuantidade(sc.nextInt());

                    dao.atualizarProduto(atualizar);
                    break;

                case 4:
                    System.out.print("ID para deletar: ");
                    int idDelete = sc.nextInt();

                    dao.deletarProduto(idDelete);
                    break;

                case 5:
                    System.out.print("ID do produto: ");
                    int idVenda = sc.nextInt();

                    System.out.print("Quantidade vendida: ");
                    int qtdVenda = sc.nextInt();

                    dao.venderProduto(idVenda, qtdVenda);
                    break;

                case 0:
                    System.out.println("Saindo...");
                    break;

                default:
                    System.out.println("Opção inválida!");
            }

        } while (opcao != 0);

        sc.close();
    }
}
