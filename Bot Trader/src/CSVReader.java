
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVReader {
    // String path = "Bot Trader/files/Read.csv";
    String line = "";
    List<String> datahora = new ArrayList<String>();
    List<String> abertura = new ArrayList<String>();
    List<String> alta = new ArrayList<String>();
    List<String> baixa = new ArrayList<String>();
    List<String> fechamento = new ArrayList<String>();

    public void readCSV(String path) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            int i = 0;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                datahora.add(values[0]);
                abertura.add(values[1]);
                alta.add(values[2]);
                baixa.add(values[3]);
                fechamento.add(values[4]);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // for (String string : datahora) {
        // System.out.println(string);
        // }

    }

    public List<String> getDatahora() {
        return datahora;
    }

    public List<String> getAbertura() {
        return abertura;
    }

    public List<String> getAlta() {
        return alta;
    }

    public List<String> getBaixa() {
        return baixa;
    }

    public List<String> getFechamento() {
        return fechamento;
    }

}
