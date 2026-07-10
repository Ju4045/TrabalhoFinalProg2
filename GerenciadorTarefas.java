package TrabalhoTelasJexer;

import java.util.ArrayList;

public class GerenciadorTarefas {

    private final ArrayList<Tarefas> tarefas = new ArrayList<>();

    public void adicionar(Tarefas tarefa) {
        tarefas.add(tarefa);
    }

    public ArrayList<Tarefas> listar() {
        return tarefas;
    }

    public void remover(int id) {

        for (int i = 0; i < tarefas.size(); i++) {

            if (tarefas.get(i).getId() == id) {
                tarefas.remove(i);
                break;
            }
        }
    }

    public void concluir(int id) {

        for (Tarefas tarefa : tarefas) {

            if (tarefa.getId() == id) {
                tarefa.setConcluida(true);
                break;
            }
        }
    }
}
