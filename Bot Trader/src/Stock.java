
import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.jfree.date.DateUtilities;
import org.jfree.ui.RefineryUtilities;

public class Stock {
    private static int periodoCurta = 20;
    private static int periodoInterm = 50;
    private static int periodoLonga = 100;
    private static double multCurto = (2.0 / (periodoCurta + 1.0));
    private static double multInterm = (2.0 / (periodoInterm + 1.0));
    private static double multLong = (2.0 / (periodoLonga + 1.0));
    static int it = 0;
    private static DynamicDataDemo demo;
    private static ScheduledExecutorService executor;
    public String stockSymbol, filepath;

    private List<String> datahora = new ArrayList<String>();

    private List<String> abertura = new ArrayList<String>();
    private List<String> alta = new ArrayList<String>();
    private List<String> baixa = new ArrayList<String>();
    private List<String> fechamento = new ArrayList<String>();
    private List<String> hora = new ArrayList<String>();
    private List<Date> time = new ArrayList<Date>();

    public Stock(String stockSymbol, String filepath) {
        this.filepath = filepath;
        this.stockSymbol = stockSymbol;
    }

    public String getStockSymbol() {
        return stockSymbol;
    }

    public void stockAnalysis(Stock stock) {

        CSVReader reader = new CSVReader();
        IndicatorCalc calcs = new IndicatorCalc();
        reader.readCSV(stock.filepath);

        datahora = reader.getDatahora();
        hora = reader.getHora();
        abertura = reader.getAbertura();
        alta = reader.getAlta();
        baixa = reader.getBaixa();
        fechamento = reader.getFechamento();

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

        for (String string : datahora) {
            try {
                string = string + ":01";
                Date data = new SimpleDateFormat("yyyy.MM.dd kk:mm:ss").parse(string);
                this.time.add(data);
                // System.out.println(data);
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        String graphWindowTitle = "Gráfico de médias em tempo real" + " " + stock.stockSymbol;
        demo = new DynamicDataDemo(
                graphWindowTitle,
                calcs.getMedia(mediaIntermSimpFecham),
                stock.stockSymbol);
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
                // System.out.println(result1);
                demo.addToSeries(result1, result2, result3, result4, result5, result6, time.get(it));
                it++;
                if (it >= (fechamento.size() - (periodoLonga - 1))) {
                    executor.shutdown();
                }
            }
        };

        executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(helloRunnable, 50, 500, TimeUnit.MILLISECONDS);

        double desvioPadFechamento = calcs.SD(fechamento);
        double desvioPadAbertura = calcs.SD(abertura);
        double desvioPadAlta = calcs.SD(alta);
        double desvioPadBaixa = calcs.SD(baixa);

        CSVWriter csvWriter = new CSVWriter();
        String[] fileName = {
                stock.stockSymbol + "dadosCurtaSimples.csv",
                stock.stockSymbol + "dadosIntermedSimples.csv",
                stock.stockSymbol + "dadosLongaSimples.csv",
                stock.stockSymbol + "dadosCurtaExp.csv",
                stock.stockSymbol + "dadosIntermedExp.csv",
                stock.stockSymbol + "dadosLongaExp.csv" };

        csvWriter.writeCSV(fileName[0], mediaCurtaSimpAbert, desvioPadAbertura, mediaCurtaSimpFecham,
                desvioPadFechamento, mediaCurtaSimpAlta, desvioPadAlta, mediaCurtaSimpBaixa, desvioPadBaixa);
        csvWriter.writeCSV(fileName[1], mediaIntermSimpAbert, desvioPadAbertura, mediaIntermSimpFecham,
                desvioPadFechamento, mediaIntermSimpAlta, desvioPadAlta, mediaIntermSimpBaixa, desvioPadBaixa);
        csvWriter.writeCSV(fileName[2], mediaLongaSimpAbert, desvioPadAbertura, mediaLongaSimpFecham,
                desvioPadFechamento, mediaLongaSimpAlta, desvioPadAlta, mediaLongaSimpBaixa, desvioPadBaixa);
        csvWriter.writeCSV(fileName[3], mediaCurtaExpAbert, desvioPadAbertura, mediaCurtaExpFecham,
                desvioPadFechamento,
                mediaCurtaExpAlta, desvioPadAlta, mediaCurtaExpBaixa, desvioPadBaixa);
        csvWriter.writeCSV(fileName[4], mediaIntermExpAbert, desvioPadAbertura, mediaIntermExpFecham,
                desvioPadFechamento, mediaIntermExpAlta, desvioPadAlta, mediaIntermExpBaixa, desvioPadBaixa);
        csvWriter.writeCSV(fileName[5], mediaLongaExpAbert, desvioPadAbertura, mediaLongaExpFecham,
                desvioPadFechamento,
                mediaLongaExpAlta, desvioPadAlta, mediaLongaExpBaixa, desvioPadBaixa);
    }

    public Double getValor(Date hora) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd kk:mm");
        String strDate = dateFormat.format(hora);
        return (Double.parseDouble(this.fechamento.get(this.datahora.indexOf(strDate))));
    }
}
