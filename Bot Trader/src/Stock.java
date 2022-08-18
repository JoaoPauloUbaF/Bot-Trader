
import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.jfree.data.time.Millisecond;
import org.jfree.data.time.TimeSeries;
import org.jfree.date.DateUtilities;
import org.jfree.ui.RefineryUtilities;

public class Stock implements Runnable {
    private static int periodoCurta = 20;
    private static int periodoInterm = 50;
    private static int periodoLonga = 100;
    private double multCurto = (2.0 / (periodoCurta + 1.0));
    private double multInterm = (2.0 / (periodoInterm + 1.0));
    private double multLong = (2.0 / (periodoLonga + 1.0));
    private DynamicDataDemo demo;
    private static ScheduledExecutorService executor;
    public String stockSymbol, filepath;
    int it = 0;

    private List<String> datahora = new ArrayList<String>();
    private List<String> abertura = new ArrayList<String>();
    private List<String> alta = new ArrayList<String>();
    private List<String> baixa = new ArrayList<String>();
    private List<String> fechamento = new ArrayList<String>();
    private List<Date> time = new ArrayList<Date>();

    List<Double> mediaLongaSimpFecham = new ArrayList<Double>();
    List<Double> mediaLongaSimpAbert = new ArrayList<Double>();
    List<Double> mediaLongaSimpAlta = new ArrayList<Double>();
    List<Double> mediaLongaSimpBaixa = new ArrayList<Double>();

    List<Double> mediaIntermSimpFecham = new ArrayList<Double>();
    List<Double> mediaIntermSimpAbert = new ArrayList<Double>();
    List<Double> mediaIntermSimpAlta = new ArrayList<Double>();
    List<Double> mediaIntermSimpBaixa = new ArrayList<Double>();

    List<Double> mediaCurtaSimpFecham = new ArrayList<Double>();
    List<Double> mediaCurtaSimpAbert = new ArrayList<Double>();
    List<Double> mediaCurtaSimpAlta = new ArrayList<Double>();
    List<Double> mediaCurtaSimpBaixa = new ArrayList<Double>();

    List<Double> mediaLongaExpFecham = new ArrayList<Double>();
    List<Double> mediaLongaExpAbert = new ArrayList<Double>();
    List<Double> mediaLongaExpAlta = new ArrayList<Double>();
    List<Double> mediaLongaExpBaixa = new ArrayList<Double>();

    List<Double> mediaIntermExpFecham = new ArrayList<Double>();
    List<Double> mediaIntermExpAbert = new ArrayList<Double>();
    List<Double> mediaIntermExpAlta = new ArrayList<Double>();
    List<Double> mediaIntermExpBaixa = new ArrayList<Double>();

    List<Double> mediaCurtaExpFecham = new ArrayList<Double>();
    List<Double> mediaCurtaExpAbert = new ArrayList<Double>();
    List<Double> mediaCurtaExpAlta = new ArrayList<Double>();
    List<Double> mediaCurtaExpBaixa = new ArrayList<Double>();

    private Double result1 = null;
    private Double result2 = null;
    private Double result3 = null;
    private Double result4 = null;
    private Double result5 = null;
    private Double result6 = null;

    // private TimeSeries series1 = new TimeSeries("Med Curta Simples",
    // Millisecond.class);;
    // private TimeSeries series2 = new TimeSeries("Med Intermediária Simples",
    // Millisecond.class);
    // private TimeSeries series3 = new TimeSeries("Med Longa Simples",
    // Millisecond.class);

    // /** The most recent value added. */
    // private double lastValue = 100.0;
    // private TimeSeries series4 = new TimeSeries("Med Curta Exponencial",
    // Millisecond.class);
    // private TimeSeries series5 = new TimeSeries("Med Intermediária Exponencial",
    // Millisecond.class);
    // private TimeSeries series6 = new TimeSeries("Med Longa Exponencial",
    // Millisecond.class);

    private IndicatorCalc calcs = new IndicatorCalc();

    public Stock(String stockSymbol, String filepath) {

        TimeSeries series1 = new TimeSeries("Med Curta Simples", Millisecond.class);
        TimeSeries series2 = new TimeSeries("Med Intermediária Simples", Millisecond.class);
        TimeSeries series3 = new TimeSeries("Med Longa Simples", Millisecond.class);

        /** The most recent value added. */
        TimeSeries series4 = new TimeSeries("Med Curta Exponencial", Millisecond.class);
        TimeSeries series5 = new TimeSeries("Med Intermediária Exponencial", Millisecond.class);
        TimeSeries series6 = new TimeSeries("Med Longa Exponencial", Millisecond.class);

        this.filepath = filepath;
        this.stockSymbol = stockSymbol;
        stockAnalysis();
        String graphWindowTitle = "Gráfico de médias em tempo real" + " " + stockSymbol;
        demo = new DynamicDataDemo(
                graphWindowTitle,
                stockSymbol, series1, series2, series3, series4, series5, series6);
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);
        // Thread t1 = new Thread(this);
        // t1.start();
    }

    public String getStockSymbol() {
        return stockSymbol;
    }

    public void stockAnalysis() {
        CSVReader reader = new CSVReader();
        reader.readCSV(filepath);

        datahora = reader.getDatahora();
        abertura = reader.getAbertura();
        alta = reader.getAlta();
        baixa = reader.getBaixa();
        fechamento = reader.getFechamento();

        mediaLongaSimpFecham = calcs.getMovingMediaSimple(fechamento, 100);
        mediaLongaSimpAbert = calcs.getMovingMediaSimple(abertura, 100);
        mediaLongaSimpAlta = calcs.getMovingMediaSimple(alta, 100);
        mediaLongaSimpBaixa = calcs.getMovingMediaSimple(baixa, 100);

        mediaIntermSimpFecham = calcs.getMovingMediaSimple(fechamento, 50);
        mediaIntermSimpAbert = calcs.getMovingMediaSimple(abertura, 50);
        mediaIntermSimpAlta = calcs.getMovingMediaSimple(alta, 50);
        mediaIntermSimpBaixa = calcs.getMovingMediaSimple(baixa, 50);

        mediaCurtaSimpFecham = calcs.getMovingMediaSimple(fechamento, 25);
        mediaCurtaSimpAbert = calcs.getMovingMediaSimple(abertura, 25);
        mediaCurtaSimpAlta = calcs.getMovingMediaSimple(alta, 25);
        mediaCurtaSimpBaixa = calcs.getMovingMediaSimple(baixa, 25);

        mediaLongaExpFecham = calcs.expAverageToCsv(fechamento, multLong);
        mediaLongaExpAbert = calcs.expAverageToCsv(abertura, multLong);
        mediaLongaExpAlta = calcs.expAverageToCsv(alta, multLong);
        mediaLongaExpBaixa = calcs.expAverageToCsv(baixa, multLong);

        mediaIntermExpFecham = calcs.expAverageToCsv(fechamento, multInterm);
        mediaIntermExpAbert = calcs.expAverageToCsv(abertura, multInterm);
        mediaIntermExpAlta = calcs.expAverageToCsv(alta, multInterm);
        mediaIntermExpBaixa = calcs.expAverageToCsv(baixa, multInterm);

        mediaCurtaExpFecham = calcs.expAverageToCsv(fechamento, multCurto);
        mediaCurtaExpAbert = calcs.expAverageToCsv(abertura, multCurto);
        mediaCurtaExpAlta = calcs.expAverageToCsv(alta, multCurto);
        mediaCurtaExpBaixa = calcs.expAverageToCsv(baixa, multCurto);

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

        double desvioPadFechamento = calcs.SD(fechamento);
        double desvioPadAbertura = calcs.SD(abertura);
        double desvioPadAlta = calcs.SD(alta);
        double desvioPadBaixa = calcs.SD(baixa);

        CSVWriter csvWriter = new CSVWriter();
        String[] fileName = {
                stockSymbol + "dadosCurtaSimples.csv",
                stockSymbol + "dadosIntermedSimples.csv",
                stockSymbol + "dadosLongaSimples.csv",
                stockSymbol + "dadosCurtaExp.csv",
                stockSymbol + "dadosIntermedExp.csv",
                stockSymbol + "dadosLongaExp.csv" };

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

    @Override
    public void run() {

        if (it < (fechamento.size() - (periodoLonga - 1))) {
            result1 = calcs.getMovingMediaSimpleChart(fechamento, periodoCurta, it);
            result2 = calcs.getMovingMediaSimpleChart(fechamento, periodoInterm, it);
            result3 = calcs.getMovingMediaSimpleChart(fechamento, periodoLonga, it);
        }
        result4 = calcs.average(Double.parseDouble(fechamento.get(it)), multCurto);
        result5 = calcs.average(Double.parseDouble(fechamento.get(it)), multInterm);
        result6 = calcs.average(Double.parseDouble(fechamento.get(it)), multLong);
        // System.out.println(result1);
        demo.addToSeries(result1, result2, result3, result4, result5, result6, time.get(it));
        it++;
        if (it >= (fechamento.size() - (periodoLonga - 1))) {
            executor.shutdown();
        }
        System.out.println("Thread: " + stockSymbol + " simplescurta: " + it);
    }

}
