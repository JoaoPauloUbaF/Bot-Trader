
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

public class Chart extends JFrame {
    List<Double> medCurta = new ArrayList<Double>();
    List<Double> medInterm = new ArrayList<Double>();
    List<Double> medLong = new ArrayList<Double>();
    List<String> horario = new ArrayList<String>();

    public Chart(List<Double> medCurta, List<Double> medInterm, List<Double> medLong, List<String> horario) {
        this.medCurta = medCurta;
        this.medInterm = medInterm;
        this.medLong = medLong;
        this.horario = horario;
        initUI();
    }

    public void initUI() {

        XYDataset dataset = createDataset();
        JFreeChart chart = createChart(dataset);
        XYPlot plot = chart.getXYPlot();
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        // rangeAxis.setRange(0.6, 0.65);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        chartPanel.setBackground(Color.white);

        add(chartPanel);

        pack();
        setTitle("Line chart");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private XYDataset createDataset() {

        var series1 = new XYSeries("Media Curta");

        for (int i = 0; i < this.medLong.size(); i++) {
            series1.add(i, this.medCurta.get(i));
        }

        var series2 = new XYSeries("Media Intermediaria");
        for (int i = 0; i < this.medLong.size(); i++) {
            series2.add(i, this.medInterm.get(i));
        }

        var series3 = new XYSeries("Media Longa");
        for (int i = 0; i < this.medLong.size(); i++) {
            series3.add(i, this.medLong.get(i));
        }

        var dataset = new XYSeriesCollection();
        dataset.addSeries(series1);
        dataset.addSeries(series2);
        dataset.addSeries(series3);

        return dataset;
    }

    private JFreeChart createChart(final XYDataset dataset) {

        JFreeChart chart = ChartFactory.createXYLineChart(
                "Indicadores",
                "Horário",
                "Valor",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false);

        XYPlot plot = chart.getXYPlot();

        var renderer = new XYLineAndShapeRenderer();
        renderer.setBaseShapesVisible(false);
        renderer.setSeriesPaint(0, Color.RED);
        renderer.setSeriesStroke(0, new BasicStroke(1.0f));
        renderer.setSeriesPaint(1, Color.BLUE);
        renderer.setSeriesStroke(1, new BasicStroke(1.0f));
        renderer.setSeriesPaint(2, Color.GREEN);
        renderer.setSeriesStroke(2, new BasicStroke(1.0f));

        plot.setRenderer(renderer);
        plot.setBackgroundPaint(Color.white);
        plot.setRangeGridlinesVisible(false);
        plot.setDomainGridlinesVisible(false);

        chart.getLegend().setFrame(BlockBorder.NONE);

        chart.setTitle(new TextTitle("Indicadores",
                new Font("Serif", Font.BOLD, 18)));

        return chart;
    }

}