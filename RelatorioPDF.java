package TrabalhoTelasJexer;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.ResultSet;

public class RelatorioPDF {

    public static void gerarRelatorio() {

        Document documento = new Document();

        try {

            PdfWriter.getInstance(documento,
                    new FileOutputStream("RelatorioTarefas.pdf"));

            documento.open();

            documento.add(new Paragraph("RELATÓRIO DE TAREFAS"));
            documento.add(new Paragraph(" "));

            ResultSet rs = Conexao.listar();

            while (rs.next()) {

                String linha
                        = "ID: " + rs.getInt("id")
                        + " | Título: " + rs.getString("titulo")
                        + " | Descrição: " + rs.getString("descricao")
                        + " | Concluída: " + rs.getBoolean("concluida");

                documento.add(new Paragraph(linha));
            }

            documento.close();

            Desktop.getDesktop().open(new File("RelatorioTarefas.pdf"));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
