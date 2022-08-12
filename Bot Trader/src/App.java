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
        private static int periodoCurta = 20;
        private static int periodoInterm = 50;
        private static int periodoLonga = 100;
        private static double multCurto = (2.0 / (periodoCurta + 1.0));
        private static double multInterm = (2.0 / (periodoInterm + 1.0));
        private static double multLong = (2.0 / (periodoLonga + 1.0));
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

                List<Double> mediaLongaExpFecham = calcs.expAverageToCsv(fechamento, multLong);
                List<Double> mediaLongaExpAbert = calcs.expAverageToCsv(abertura, multLong);
                List<Double> mediaLongaExpAlta = calcs.expAverageToCsv(alta, multLong);
                List<Double> mediaLongaExpBaixa = calcs.expAverageToCsv(baixa, multLong);

                List<Double> mediaIntermExpFecham = calcs.expAverageToCsv(fechamento, multInterm);
                List<Double> mediaIntermExpAbert = calcs.expAverageToCsv(abertura, multInterm);
                List<Double> mediaIntermExpAlta = calcs.expAverageToCsv(alta, multInterm);
                List<Double> mediaIntermExpBaixa = calcs.expAverageToCsv(baixa, multInterm);

                List<Double> mediaCurtaExpFecham = calcs.expAverageToCsv(fechamento, multCurto);
                List<Double> mediaCurtaExpAbert = calcs.expAverageToCsv(abertura, multCurto);
                List<Double> mediaCurtaExpAlta = calcs.expAverageToCsv(alta, multCurto);
                List<Double> mediaCurtaExpBaixa = calcs.expAverageToCsv(baixa, multCurto);

                demo = new DynamicDataDemo("Gráfico de médias em tempo real",
                                calcs.getMedia(fechamento));
                demo.pack();
                RefineryUtilities.centerFrameOnScreen(demo);
                demo.setVisible(true);

                Runnable helloRunnable = new Runnable() {
                        public void run() {
                                RealTimeChart(calcs, fechamento);
                        }

                        private void RealTimeChart(IndicatorCalc calcs, List<String> data) {
                                Double result1 = null;
                                Double result2 = null;
                                Double result3 = null;
                                Double result4 = null;
                                Double result5 = null;
                                Double result6 = null;

                                if (it < (fechamento.size() - (periodoLonga - 1))) {
                                        result1 = calcs.getMovingMediaSimpleChart(data, periodoCurta, it);
                                        result2 = calcs.getMovingMediaSimpleChart(data, periodoInterm, it);
                                        result3 = calcs.getMovingMediaSimpleChart(data, periodoLonga, it);
                                }
                                result4 = calcs.average(Double.parseDouble(data.get(it)), multCurto);
                                result5 = calcs.average(Double.parseDouble(data.get(it)), multInterm);
                                result6 = calcs.average(Double.parseDouble(data.get(it)), multLong);

                                demo.addToSeries(result1, result2, result3, result4, result5, result6);
                                it++;
                                if (it >= (fechamento.size() - (periodoLonga - 1))) {
                                        executor.shutdown();
                                }
                        }
                };

                executor = Executors.newScheduledThreadPool(1);
                executor.scheduleAtFixedRate(helloRunnable, 50, 100, TimeUnit.MILLISECONDS);

                double desvioPadFechamento = calcs.SD(fechamento);
                double desvioPadAbertura = calcs.SD(abertura);
                double desvioPadAlta = calcs.SD(alta);
                double desvioPadBaixa = calcs.SD(baixa);

                for (int i = 0; i < datahora.size(); i++) {
                        hora.add((datahora.get(i).charAt(11) + "" +
                                        datahora.get(i).charAt(12) + ":00"));
                }

                CSVWriter csvWriter = new CSVWriter();
                String[] fileName = { "dadosCurtaSimples.csv", "dadosIntermedSimples.csv", "dadosLongaSimples.csv",
                                "dadosCurtaExp.csv", "dadosIntermedExp.csv", "dadosLongaExp.csv" };

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
                csvWriter.writeCSV(
                                fileName[3], mediaCurtaExpAbert, desvioPadAbertura,
                                mediaCurtaExpFecham, desvioPadFechamento,
                                mediaCurtaExpAlta, desvioPadAlta, mediaCurtaExpBaixa, desvioPadBaixa);
                csvWriter.writeCSV(
                                fileName[4], mediaIntermExpAbert, desvioPadAbertura,
                                mediaIntermExpFecham, desvioPadFechamento,
                                mediaIntermExpAlta, desvioPadAlta, mediaIntermExpBaixa, desvioPadBaixa);
                csvWriter.writeCSV(
                                fileName[5], mediaLongaExpAbert, desvioPadAbertura,
                                mediaLongaExpFecham, desvioPadFechamento,
                                mediaLongaExpAlta, desvioPadAlta, mediaLongaExpBaixa, desvioPadBaixa);

        }
}
