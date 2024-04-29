package apllication;

import DB.DB;

import java.sql.*;

public class Program {

    static Connection conn = null;

    public static void main(String[] args) {
        conn = DB.getConexao();
        criarTabela();
    }

    public static void inserirDados() {
        try {
            PreparedStatement p1 = conn.prepareStatement("insert into department values (default, 'Militar');");
                int colunasAfetadas = p1.executeUpdate();
                if(colunasAfetadas > 0) {
                    System.out.println("Inserção feita com sucesso!");
                    DB.fecharConexao();
                } else {
                    System.out.println("Nenhuma linha afetada. A inserção não foi bem-sucedida.");
                }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void removerDados() {
        try {
            PreparedStatement p1 = conn.prepareStatement("delete from department where Id = ?");
            p1.setInt(1, 13);
            int colunasAfetadas = p1.executeUpdate();
            if(colunasAfetadas > 0) {
                System.out.println("Deletado com sucesso!");
                DB.fecharConexao();
            } else {
                System.out.println("Nenhuma linha afetada!");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void atualizarDados() {
        try {
            PreparedStatement p1 = conn.prepareStatement("UPDATE `coursejdbc`.`department` SET `Name` = 'Teste' WHERE (`Id` = ?);");
            p1.setInt(1, 12);
            int colunasAfetadas = p1.executeUpdate();
            if(colunasAfetadas > 0) {
                System.out.println("Atualizado com sucesso!");
                DB.fecharConexao();
            } else {
                System.out.println("Nenhuma linha afetada!");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void criarTabela() {
        try {
            DatabaseMetaData meta = conn.getMetaData();
            ResultSet rs = meta.getTables(null, null, "teste", null);
            if (!rs.next()) {
                PreparedStatement p1 = conn.prepareStatement("create table teste(id int primary key auto_increment);");
                p1.executeUpdate();
                System.out.println("Tabela criada com sucesso!");
                DB.fecharConexao();
            } else {
                System.out.println("Tabela já existe!");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao criar tabela " + e.getMessage());
        }
    }



}
