package dao;

import database.ConexaoSQLite;
import model.Produto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {

    // ===== LISTAR =====
    public List<Produto> listarProdutos() {

        List<Produto> lista = new ArrayList<>();

        String sql = "SELECT * FROM produtos";

        try {
            Connection conn = ConexaoSQLite.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                Produto p = new Produto();

                p.setId(rs.getInt("id"));
                p.setNome(rs.getString("nome"));
                p.setPreco(rs.getDouble("preco"));
                p.setQuantidade(rs.getInt("quantidade"));

                lista.add(p);
            }

        } catch (Exception e) {
            System.out.println("Erro ao listar: " + e.getMessage());
        }

        return lista;
    }

    // ===== INSERT =====
    public void inserirProduto(Produto produto) {

        String sql = "INSERT INTO produtos (nome, preco, quantidade) VALUES (?, ?, ?)";

        try {
            Connection conn = ConexaoSQLite.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, produto.getNome());
            stmt.setDouble(2, produto.getPreco());
            stmt.setInt(3, produto.getQuantidade());

            stmt.executeUpdate();

            System.out.println("Produto inserido com sucesso!");

        } catch (Exception e) {
            System.out.println("Erro ao inserir: " + e.getMessage());
        }

    }
     // ===== UPDATE =====
    public void atualizarProduto(Produto produto) {

        String sql = "UPDATE produtos SET nome = ?, preco = ?, quantidade = ? WHERE id = ?";

        try {
            Connection conn = ConexaoSQLite.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, produto.getNome());
            stmt.setDouble(2, produto.getPreco());
            stmt.setInt(3, produto.getQuantidade());
            stmt.setInt(4, produto.getId());

            stmt.executeUpdate();

            System.out.println("Produto atualizado com sucesso!");

            stmt.close();
            conn.close();

        } catch (Exception e) {
            System.out.println("Erro ao atualizar: " + e.getMessage());
        }
    }

    // ===== DELETE =====
    public void deletarProduto(int id) {

        String sql = "DELETE FROM produtos WHERE id = ?";

        try {
            Connection conn = ConexaoSQLite.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, id);
            stmt.executeUpdate();

            System.out.println("Produto deletado com sucesso!");

            stmt.close();
            conn.close();

        } catch (Exception e) {
            System.out.println("Erro ao deletar: " + e.getMessage());
        }
    }

    // ===== VENDA =====
    public void venderProduto(int id, int quantidadeVendida) {

        String sqlBusca = "SELECT quantidade FROM produtos WHERE id = ?";
        String sqlAtualiza = "UPDATE produtos SET quantidade = ? WHERE id = ?";

        try {
            Connection conn = ConexaoSQLite.conectar();

            PreparedStatement stmtBusca = conn.prepareStatement(sqlBusca);
            stmtBusca.setInt(1, id);

            ResultSet rs = stmtBusca.executeQuery();

            if (rs.next()) {

                int estoqueAtual = rs.getInt("quantidade");

                if (estoqueAtual >= quantidadeVendida) {

                    int novoEstoque = estoqueAtual - quantidadeVendida;

                    PreparedStatement stmtAtualiza = conn.prepareStatement(sqlAtualiza);
                    stmtAtualiza.setInt(1, novoEstoque);
                    stmtAtualiza.setInt(2, id);
                    stmtAtualiza.executeUpdate();

                    System.out.println("Venda realizada com sucesso!");

                    stmtAtualiza.close();

                } else {
                    System.out.println("Estoque insuficiente!");
                }

            } else {
                System.out.println("Produto não encontrado!");
            }

            rs.close();
            stmtBusca.close();
            conn.close();

        } catch (Exception e) {
            System.out.println("Erro na venda: " + e.getMessage());
        }
    }
}
