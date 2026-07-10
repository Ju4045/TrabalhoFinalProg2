package TrabalhoTelasJexer;

import java.sql.Connection;

public class TesteConexao {

    public static void main(String[] args) {

        try {
            Connection c = Conexao.conectar();
            System.out.println("Banco funcionando!");

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

}
