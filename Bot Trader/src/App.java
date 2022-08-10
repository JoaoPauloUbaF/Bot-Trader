import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.jfree.ui.RefineryUtilities;

import java.awt.EventQueue;

public class App {
        private static int periodoCurta = 25;
        private static int periodoInterm = 50;
        private static int periodoLonga = 100;
        static int it = 0;
        private static DynamicDataDemo demo;
        private static ScheduledExecutorService executor;

        public static void main(String[] args) throws Exception {
                CSVReader reader = new CSVReader();
                IndicatorCalc calcs = new IndicatorCalc();
                reader.readCSV();

                List<String> datahora = reader.getDatahora();
                List<String> abertura = reader.getAbertura();
                List<String> alta = reader.getAlta();
                List<String> baixa = reader.getBaixa();
                List<String> fechamento = reader.getFechamento();
                List<String> hora = new ArrayList<String>();

                List<Double> mediaLongaSimpFecham = calcs.getMovingMediaSimple(fechamento, 100);
                List<Double> mediaLongaSimpAbert = calcs.getMovingMediaSimple(abertura, 100);
                List<Double> mediaLongaSimpAlta = calcs.getMovingMediaSimple(alta, 100);
                List<Double> mediaLongaSimpBaixa = calcs.getMovingMediaSimple(baixa, 100);

                List<Double> mediaIntermSimpFecham = calcs.getMovingMediaSimple(fechamento, 50);
                List<Double> mediaIntermSimpAbert = calcs.getMovingMediaSimple(abertura, 50);
                List<Double> mediaIntermSimpAlta = calcs.getMovingMediaSimple(alta, 50);
                List<Double> mediaIntermSimpBaixa = calcs.getMovingMediaSimple(baixa, 50);

                List<Double> mediaCurtaSimpFecham = calcs.getMovingMediaSimple(fechamento, 25);
                List<Double> mediaCurtaSimpAbert = calcs.getMovingMediaSimple(abertura, 25);
                List<Double> mediaCurtaSimpAlta = calcs.getMovingMediaSimple(alta, 25);
                List<Double> mediaCurtaSimpBaixa = calcs.getMovingMediaSimple(baixa, 25);

                // demo = new DynamicDataDemo("Gráfico de médias em tempo real",
                // calcs.getMedia(fechamento));
                // demo.pack();
                // RefineryUtilities.centerFrameOnScreen(demo);
                // demo.setVisible(true);

                // Runnable helloRunnable = new Runnable() {
                // public void run() {
                // Double result1 = null;
                // Double result2 = null;
                // Double result3 = null;

                // if (it < (fechamento.size() - (periodoLonga - 1))) {
                // result1 = calcs.getMovingMediaSimpleChart(fechamento, periodoCurta, it);
                // }
                // if (it < (fechamento.size() - (periodoLonga - 1))) {
                // result2 = calcs.getMovingMediaSimpleChart(fechamento, periodoInterm, it);
                // }
                // if (it < (fechamento.size() - (periodoLonga - 1))) {
                // result3 = calcs.getMovingMediaSimpleChart(fechamento, periodoLonga, it);
                // }
                // demo.addToSeries(result1, result2, result3);
                // it++;
                // if (it >= (fechamento.size() - (periodoLonga - 1))) {
                // executor.shutdown();
                // }
                // }
                // };

                // executor = Executors.newScheduledThreadPool(1);
                // executor.scheduleAtFixedRate(helloRunnable, 0, 100, TimeUnit.MILLISECONDS);

                // for (int i = 0; i < fechamento.size() - 49; i++) {

                // Double result = calcs.getMovingMediaSimpleChart(fechamento, 50, i);
                // System.out.println(result);
                // // TimeUnit.MILLISECONDS.sleep(10);
                // }

                double desvioPadFechamento = calcs.SD(fechamento);
                double desvioPadAbertura = calcs.SD(abertura);
                double desvioPadAlta = calcs.SD(alta);
                double desvioPadBaixa = calcs.SD(baixa);

                for (int i = 0; i < datahora.size(); i++) {
                        hora.add((datahora.get(i).charAt(11) + "" +
                                        datahora.get(i).charAt(12) + ":00"));
                        System.out.println(hora.get(i));
                }

                CSVWriter csvWriter = new CSVWriter();
                String[] fileName = { "dadosCurta.csv", "dadosIntermed.csv", "dadosLonga.csv" };

                csvWriter.writeCSV(
                                fileName[0], mediaCurtaSimpAbert, desvioPadAbertura,
                                mediaCurtaSimpFecham, desvioPadFechamento,
                                mediaCurtaSimpAlta, desvioPadAlta, mediaCurtaSimpBaixa, desvioPadBaixa);
                csvWriter.writeCSV(
                                fileName[1], mediaIntermSimpAbert, desvioPadAbertura,
                                mediaIntermSimpFecham, desvioPadFechamento,
                                mediaIntermSimpAlta, desvioPadAlta, mediaIntermSimpBaixa, desvioPadBaixa);
                csvWriter.writeCSV(
                                fileName[2], mediaLongaSimpAbert, desvioPadAbertura,
                                mediaLongaSimpFecham, desvioPadFechamento,
                                mediaLongaSimpAlta, desvioPadAlta, mediaLongaSimpBaixa, desvioPadBaixa);

                // EventQueue.invokeLater(() -> {
                // var ex = new Chart(mediaCurtaSimpBaixa, mediaIntermSimpBaixa,
                // mediaLongaSimpBaixa, hora);
                // ex.initUI();
                // ex.setVisible(true);
                // });
        }
}
