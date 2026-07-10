package TrabalhoTelasJexer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Conexao {

    private static final String url = "jdbc:postgresql://localhost:5432/Tarefas";
    private static final String usuario = "postgres";
    private static final String senha = "Jujuba";

    public static Connection conectar() throws SQLException {
        Connection conexao;
        conexao = DriverManager.getConnection(url, usuario, senha);
        System.out.println("Conectado com sucesso!");
        return conexao;
    }

    public static void inserir(Tarefas tarefa) throws SQLException {

        Connection conexao = conectar();

        String sql = "INSERT INTO adicionartarefa(titulo, descricao, concluida) VALUES (?,?,?)";

        PreparedStatement stmt = conexao.prepareStatement(sql);

        stmt.setString(1, tarefa.getTitulo());
        stmt.setString(2, tarefa.getDescricao());
        stmt.setBoolean(3, tarefa.isConcluida());

        stmt.executeUpdate();

        stmt.close();
        conexao.close();
    }

    public static void excluir(int id) throws SQLException {

        Connection conexao = conectar();

        String sql = "DELETE FROM adicionartarefa WHERE id = ?";

        PreparedStatement stmt = conexao.prepareStatement(sql);

        stmt.setInt(1, id);

        stmt.executeUpdate();

        stmt.close();
        conexao.close();
    }

    public static void concluir(int id) throws SQLException {

        Connection conexao = conectar();

        String sql = "UPDATE adicionartarefa SET concluida = true WHERE id = ?";

        PreparedStatement stmt = conexao.prepareStatement(sql);

        stmt.setInt(1, id);

        stmt.executeUpdate();

        stmt.close();
        conexao.close();
    }

    public static ResultSet listar() throws SQLException {
        Connection conexao = conectar();

        String sql = "SELECT * FROM adicionartarefa";

        PreparedStatement stmt = conexao.prepareStatement(sql);

        return stmt.executeQuery();
    }
}
