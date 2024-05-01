package app;

import DB.DB;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class Program {

    static Connection conn = null;
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        conn = DB.getConexao();
        menu();
    }

    public static void menu() {
        System.out.println("---------------JAVA CRUD APLICAÇÃO---------------");
        System.out.println("1- Inserir dados");
        System.out.println("2- Remover dados");
        System.out.println("3- Atualizar dados");
        System.out.println("4- Listar dados");
        System.out.println("5- Sair");
        System.out.println("-------------------------------------------------");
        System.out.print("Escolha uma opção: ");

        int escolha = scanner.nextInt();
        scanner.nextLine();

        switch (escolha) {
            case 1:
                inserirDados();
                break;
            case 2:
                removerDados();
                break;
            case 3:
                atualizarDados();
                break;
            case 4:
                listarDados();
                break;
            case 5:
                System.out.println("Saindo...");
                DB.fecharConexao();
                System.exit(0);
                break;
            default:
                System.out.println("Opção inválida");
                break;
        }
    }

    public static void inserirDados() {
        System.out.println("Em qual tabela deseja inserir? (1 para Departamento / 2 para Vendedor)");
        int escolha = scanner.nextInt();
        scanner.nextLine();

        if (escolha == 1) {
            System.out.println("Digite o nome do departamento");
            String departamento = scanner.nextLine();

            try {
                PreparedStatement p1 = conn.prepareStatement("insert into department (Name) values (?)");
                p1.setString(1, departamento);

                int colunasAfetadas = p1.executeUpdate();
                if (colunasAfetadas > 0) {
                    System.out.println("Inserção feita com sucesso!");
                } else {
                    System.out.println("Nenhuma linha afetada. A inserção não foi bem-sucedida.");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else if (escolha == 2) {
            System.out.println("Digite o nome do vendedor: ");
            String nomeVendedor = scanner.nextLine();

            System.out.println("Digite o email do vendedor :");
            String email = scanner.nextLine();

            System.out.println("Digite o salario base do vendedor :");
            double salarioBase = scanner.nextDouble();
            scanner.nextLine();

            System.out.println("Digite a data de nascimento do vendedor (dd/MM/yyyy): ");
            String dataNascimentoStr = scanner.nextLine();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            java.util.Date dataNascimento;
            try {
                dataNascimento = sdf.parse(dataNascimentoStr);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            java.sql.Date sqlDate = new java.sql.Date(dataNascimento.getTime());

            System.out.println("Digite o id do departamento relacionado: ");
            int idDepartamento = scanner.nextInt();
            scanner.nextLine();

            try {
                PreparedStatement p2 = conn.prepareStatement("insert into seller (Name, Email, BirthDate, BaseSalary, DepartmentId) values (?, ?, ?, ?, ?)");
                p2.setString(1, nomeVendedor);
                p2.setString(2, email);
                p2.setDate(3, sqlDate);
                p2.setDouble(4, salarioBase);
                p2.setInt(5, idDepartamento);

                int colunasAfetadas = p2.executeUpdate();
                if (colunasAfetadas > 0) {
                    System.out.println("Inserção feita com sucesso!");
                } else {
                    System.out.println("Nenhuma linha afetada. A inserção não foi bem-sucedida.");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.println("Opção inválida!");
        }

        menu();
    }

    public static void removerDados() {
        System.out.println("De qual tabela deseja deletar os dados? (1 para Departamento / 2 para Vendedor)");
        int escolha = scanner.nextInt();
        scanner.nextLine();

        if (escolha == 1) {
            try {
                PreparedStatement p1 = conn.prepareStatement("delete from department where Id = ?");

                System.out.println("Digite o id a ser removido");
                int id = scanner.nextInt();
                scanner.nextLine();

                p1.setInt(1, id);

                int colunasAfetadas = p1.executeUpdate();
                if (colunasAfetadas > 0) {
                    System.out.println("Deletado com sucesso!");
                } else {
                    System.out.println("Nenhuma linha afetada!");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else if (escolha == 2) {
            try {
                PreparedStatement p1 = conn.prepareStatement("delete from seller where Id = ?");

                System.out.println("Digite o id a ser removido");
                int id = scanner.nextInt();
                scanner.nextLine();

                p1.setInt(1, id);

                int colunasAfetadas = p1.executeUpdate();
                if (colunasAfetadas > 0) {
                    System.out.println("Deletado com sucesso!");
                } else {
                    System.out.println("Nenhuma linha afetada!");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.println("Opção inválida!");
        }

        menu();
    }

    public static void atualizarDados() {
        System.out.println("Qual tabela deseja atualizar os dados? (1 para Departamento / 2 para Vendedor)");
        int escolha = scanner.nextInt();
        scanner.nextLine();

        if (escolha == 1) {
            System.out.println("De qual id deseja atualizar os dados? ");
            int id = scanner.nextInt();
            scanner.nextLine();

            System.out.println("Qual será o novo nome? ");
            String nome = scanner.nextLine();
            try {
                PreparedStatement p1 = conn.prepareStatement("UPDATE department SET Name = ? WHERE Id = ?");
                p1.setString(1, nome);
                p1.setInt(2, id);
                int colunasAfetadas = p1.executeUpdate();
                if (colunasAfetadas > 0) {
                    System.out.println("Atualizado com sucesso!");
                } else {
                    System.out.println("Nenhuma linha afetada!");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else if (escolha == 2) {
            System.out.println("De qual id deseja alterar os dados");
            int id = scanner.nextInt();
            scanner.nextLine();

            System.out.println("Qual será o novo nome?");
            String nome = scanner.nextLine();

            System.out.println("Qual será o novo email");
            String email = scanner.nextLine();

            System.out.println("Qual será a nova data de nascimento (dd/MM/yyyy)?");
            String dataNascimentoStr = scanner.nextLine();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            java.util.Date dataNascimento;
            try {
                dataNascimento = sdf.parse(dataNascimentoStr);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            java.sql.Date sqlDate = new java.sql.Date(dataNascimento.getTime());

            System.out.println("Qual será o novo salário base?");
            double salarioBase = scanner.nextDouble();
            scanner.nextLine();

            System.out.println("Qual será o novo id do departamento?");
            int idDepartamento = scanner.nextInt();
            scanner.nextLine();

            try {
                PreparedStatement p1 = conn.prepareStatement("UPDATE seller SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ? WHERE Id = ?");
                p1.setString(1, nome);
                p1.setString(2, email);
                p1.setDate(3, sqlDate);
                p1.setDouble(4, salarioBase);
                p1.setInt(5, idDepartamento);
                p1.setInt(6, id);

                int colunasAfetadas = p1.executeUpdate();
                if (colunasAfetadas > 0) {
                    System.out.println("Atualizado com sucesso!");
                } else {
                    System.out.println("Nenhuma linha afetada!");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.println("Opção inválida!");
        }

        menu();
    }

    private static void listarDados() {
        System.out.println("De qual tabela deseja listar os dados? (1 para Departamento / 2 para Vendedor)");
        int escolha = scanner.nextInt();
        scanner.nextLine();

        if (escolha == 1) {
            try {
                PreparedStatement p1 = conn.prepareStatement("select * from department");

                ResultSet rs = p1.executeQuery();

                while (rs.next()) {
                    int Id = rs.getInt("Id");
                    String nomeDepartamento = rs.getString("Name");
                    System.out.println("[Id = " + Id + ", nome do departamento = " + nomeDepartamento + "]");
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else if (escolha == 2) {
            try {
                PreparedStatement p1 = conn.prepareStatement("select * from seller");

                ResultSet rs = p1.executeQuery();

                while (rs.next()) {
                    int id = rs.getInt("Id");
                    String nome = rs.getString("Name");
                    String email = rs.getString("Email");
                    Date dataDeNascimento = rs.getDate("BirthDate");
                    double salarioBase = rs.getDouble("BaseSalary");
                    int idDepartamento = rs.getInt("DepartmentId");

                    System.out.println("[Id = " + id + ", Nome = " + nome + ", Email = " + email + ", Data de Nascimento = " + dataDeNascimento +
                            ", Salário Base = " + salarioBase + ", ID do Departamento = " + idDepartamento + "]");
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.println("Escolha inválida!");
        }
        menu();
    }
}
