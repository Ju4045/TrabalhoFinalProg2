package TrabalhoTelasJexer;

import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.sql.SQLException;
import jexer.TAction;
import jexer.TComboBox;
import jexer.TWindow;
import jexer.TApplication;
import jexer.TField;
import jexer.TTableWidget;
import jexer.event.TMenuEvent;
import jexer.menu.TMenu;
import jexer.TEditColorThemeWindow;

/**
 *
 * @author dboemo
 */
public class Jexer extends TApplication {

    TComboBox comboBox = null;
    private TTableWidget tableField;

    public Jexer(BackendType backendType) throws UnsupportedEncodingException {
        super(backendType);
    }

    public Jexer() throws Exception {
        super(TApplication.BackendType.SWING);
        menu01();
        menu02();
        Principal();
    }

    public void Principal() {
        TMenu menu = addMenu("&Sistema");
        menu.addItem(5000, "&Sair");
        menu.addItem(5001, "&Alterar Cores");
    }

    public void menu01() {
        TMenu menu = addMenu("&Tarefas");
        menu.addItem(2000, "&Nova Tarefa");
        menu.addSeparator();
        menu.addItem(2001, "&Listar Tarefas");
        menu.addSeparator();
        menu.addItem(2002, "&Concluir Tarefa");
        menu.addSeparator();
        menu.addItem(2003, "&Excluir Tarefa");
    }

    public void menu02() {
        TMenu menu = addMenu("&Relatórios");
        menu.addItem(3000, "&Gerar PDF");
    }

    public void telaCadastro() {

        TWindow window = new TWindow(this, "Cadastro de Tarefa", 50, 18);

        window.addLabel("Título", 2, 2);
        TField titulo = new TField(window, 12, 2, 25, true, "");

        window.addLabel("Descrição", 2, 4);
        TField descricao = new TField(window, 12, 4, 25, true, "");

        window.addButton("Salvar", 10, 10, new TAction() {
            public void DO() {

                try {

                    Tarefas tarefa = new Tarefas();

                    tarefa.setTitulo(titulo.getText());
                    tarefa.setDescricao(descricao.getText());

                    Conexao.inserir(tarefa);

                    window.getApplication().messageBox(
                            "Sucesso",
                            "Tarefa cadastrada com sucesso!"
                    );

                } catch (Exception e) {

                    window.getApplication().messageBox(
                            "Erro",
                            e.getMessage()
                    );

                }

            }
        });

        window.addButton("Fechar", 25, 10, new TAction() {
            public void DO() {
                window.getApplication().closeWindow(window);
            }
        });

    }

    public void telaTabela() {

        TWindow window = new TWindow(this,
                "Lista de Tarefas",
                70,
                20);

        tableField = new TTableWidget(window, 0, 2, 60, 12);

        tableField.setColumnLabel(0, "ID");
        tableField.setColumnLabel(1, "Título");
        tableField.setColumnLabel(2, "Descrição");
        tableField.setColumnLabel(3, "Concluída");

        try {

            ResultSet rs = Conexao.listar();

            int linha = 0;

            while (rs.next()) {

                tableField.setCellText(0, linha, String.valueOf(rs.getInt("id")));
                tableField.setCellText(1, linha, rs.getString("titulo"));
                tableField.setCellText(2, linha, rs.getString("descricao"));
                tableField.setCellText(3, linha,
                        rs.getBoolean("concluida") ? "Sim" : "Não");

                linha++;
            }

        } catch (SQLException e) {
            messageBox("Erro", e.getMessage());
        }

    }

    public void telaConcluir() {

        TWindow window = new TWindow(this, "Concluir Tarefa", 40, 10);

        window.addLabel("ID:", 2, 2);

        TField id = new TField(window, 8, 2, 10, true, "");

        window.addButton("Concluir", 5, 6, new TAction() {
            public void DO() {

                try {

                    Conexao.concluir(Integer.parseInt(id.getText()));

                    messageBox("Sucesso", "Tarefa concluída!");

                    window.getApplication().closeWindow(window);

                } catch (Exception e) {

                    messageBox("Erro", e.getMessage());

                }

            }
        });

        window.addButton("Fechar", 20, 6, new TAction() {
            public void DO() {
                window.getApplication().closeWindow(window);
            }
        });
    }

    public void telaExcluir() {

        TWindow window = new TWindow(this, "Excluir Tarefa", 40, 10);

        window.addLabel("ID:", 2, 2);

        TField id = new TField(window, 8, 2, 10, true, "");

        window.addButton("Excluir", 5, 6, new TAction() {
            public void DO() {

                try {

                    Conexao.excluir(Integer.parseInt(id.getText()));

                    messageBox("Sucesso", "Tarefa excluída!");

                    window.getApplication().closeWindow(window);

                } catch (Exception e) {

                    messageBox("Erro", e.getMessage());

                }

            }
        });

        window.addButton("Fechar", 20, 6, new TAction() {
            public void DO() {
                window.getApplication().closeWindow(window);
            }
        });
    }

    @Override
    public boolean onMenu(TMenuEvent event) {
        switch (event.getId()) {

            case 2000:
                telaCadastro();
                return true;

            case 5001:
                new TEditColorThemeWindow(this);
                return true;

            case 2001:
                telaTabela();
                return true;

            case 2002:
                telaConcluir();
                return true;

            case 2003:
                telaExcluir();
                return true;

            case 3000:
                RelatorioPDF.gerarRelatorio();
                return true;

            case 5000:
                exit();
                return true;
        }
        return false;

    }

    public static void main(String[] args) throws Exception {
        Jexer app = new Jexer();
        app.run();
    }
}
