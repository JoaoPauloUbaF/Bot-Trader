import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class CSVWriter {
    public void writeCSV(String fileName, List<Double> medAbertura, Double desvioPadAber, List<Double> medFecha,
            Double desvioPadFech,
            List<Double> medAlta,
            Double desvioPadAlta, List<Double> medBaixa, Double desvioPadBaixa) {
        try {

            FileWriter fw = new FileWriter(fileName);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);

            pw.println(("Media Movel"));
            pw.println(
                    ("Abertura; Desvio Padrao; Fechamento; Desvio Padrao; Alta; Desvio Padrao; Baixa; Desvio Padrao;"));

            for (int i = 0; i < medAbertura.size(); i++) {
                String result = "";
                result = String.format("%.5f", medAbertura.get(i)) + ";" + String.format("%.5f", desvioPadAber) + ";"
                        + String.format("%.5f", medFecha.get(i)) + ";" + String.format("%.5f", desvioPadFech) + ";"
                        + String.format("%.5f", medAlta.get(i)) + ";" + String.format("%.5f", desvioPadAlta) + ";"
                        + String.format("%.5f", medBaixa.get(i)) + ";" + String.format("%.5f", desvioPadBaixa) + ";";

                pw.println((result));
            }

            pw.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
