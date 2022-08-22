/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2004, by Object Refinery Limited and Contributors.
 *
 * Project Info:  http://www.jfree.org/jfreechart/index.html
 *
 * This library is free software; you can redistribute it and/or modify it under the terms
 * of the GNU Lesser General Public License as published by the Free Software Foundation;
 * either version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with this
 * library; if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 * Boston, MA 02111-1307, USA.
 *
 * [Java is a trademark or registered trademark of Sun Microsystems, Inc. 
 * in the United States and other countries.]
 *
 * --------------------
 * DynamicDataDemo.java
 * --------------------
 * (C) Copyright 2002-2004, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited).
 * Contributor(s):   -;
 *
 * $Id: DynamicDataDemo.java,v 1.12 2004/05/07 16:09:03 mungady Exp $
 *
 * Changes
 * -------
 * 28-Mar-2002 : Version 1 (DG);
 *
 */

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

/**
 * A demonstration application showing a time series chart where you can
 * dynamically add
 * (random) data by clicking on a button.
 *
 */
public class DynamicDataDemo extends ApplicationFrame implements ActionListener {

    /** The time series data. */
    private TimeSeries series1;
    private TimeSeries series2;
    private TimeSeries series3;

    /** The most recent value added. */
    private double lastValue = 100.0;
    private TimeSeries series4;
    private TimeSeries series5;
    private TimeSeries series6;

    /**
     * Constructs a new demonstration application.
     *
     * @param title    the frame title.
     * @param series62
     * @param series52
     * @param series42
     * @param series32
     * @param series22
     * @param series12
     * @param result6
     * @param result5
     * @param result4
     * @param result3
     * @param result2
     * @param result1
     */
    public DynamicDataDemo(final String title, final String stockSymbol, TimeSeries series1, TimeSeries series2,
            TimeSeries series3, TimeSeries series4, TimeSeries series5, TimeSeries series6) {

        super(title);
        this.series1 = series1;
        this.series2 = series2;
        this.series3 = series3;
        this.series4 = series4;
        this.series5 = series5;
        this.series6 = series6;

        final TimeSeriesCollection dataset = new TimeSeriesCollection();
        dataset.addSeries(series1);
        dataset.addSeries(series2);
        dataset.addSeries(series3);

        final TimeSeriesCollection dataset1 = new TimeSeriesCollection();
        dataset1.addSeries(series4);
        dataset1.addSeries(series5);
        dataset1.addSeries(series6);

        final JFreeChart chart = createChart(dataset, (3600000.0 * 12.0), stockSymbol + "-Gráfico de Médias Simples");

        final JFreeChart chart1 = createChart(dataset1,
                (3600000.0 * 12.0), stockSymbol + "-Gráfico de médias Exponenciais");

        final ChartPanel chartPanel = new ChartPanel(chart);
        final ChartPanel chartPanel1 = new ChartPanel(chart1);
        GridLayout experimentLayout = new GridLayout(0, 1);
        final JPanel content = new JPanel(experimentLayout);
        content.add(chartPanel);
        content.add(chartPanel1);
        chartPanel.setPreferredSize(new java.awt.Dimension(450, 400));
        chartPanel1.setPreferredSize(new java.awt.Dimension(450, 400));
        setContentPane(content);
    }

    public void addToSeries(Double curta, Double interm, Double longa, Double curtaEx, Double intermEx,
            Double longaEx, Date time) {
        this.series1.add(new Millisecond(time), curta);
        this.series2.add(new Millisecond(time), interm);
        this.series3.add(new Millisecond(time), longa);
        this.series4.add(new Millisecond(time), curtaEx);
        this.series5.add(new Millisecond(time), intermEx);
        this.series6.add(new Millisecond(time), longaEx);
    }

    /**
     * Creates a sample chart.
     * 
     * @param dataset the dataset.
     * 
     * @return A sample chart.
     */
    private JFreeChart createChart(final XYDataset dataset, final Double range, final String title) {
        final JFreeChart result = ChartFactory.createTimeSeriesChart(
                title,
                "Time",
                "Value",
                dataset,
                true,
                true,
                false);
        final XYPlot plot = result.getXYPlot();
        ValueAxis axis = plot.getDomainAxis();
        axis.setAutoRange(true);
        axis.setFixedAutoRange(range); // 60 seconds
        axis = plot.getRangeAxis();
        return result;
    }

    // ****************************************************************************
    // * JFREECHART DEVELOPER GUIDE *
    // * The JFreeChart Developer Guide, written by David Gilbert, is available *
    // * to purchase from Object Refinery Limited: *
    // * *
    // * http://www.object-refinery.com/jfreechart/guide.html *
    // * *
    // * Sales are used to provide funding for the JFreeChart project - please *
    // * support us so that we can continue developing free software. *
    // ****************************************************************************

    /**
     * Handles a click on the button by adding new (random) data.
     *
     * @param e the action event.
     */
    public void actionPerformed(final ActionEvent e) {
        if (e.getActionCommand().equals("ADD_DATA")) {
            final double factor = 0.90 + 0.2 * Math.random();
            this.lastValue = this.lastValue * factor;
            final Millisecond now = new Millisecond();
            System.out.println("Now = " + now.toString());
            // this.series.add(new Millisecond(), this.lastValue);
        }
    }

    /**
     * Starting point for the demonstration application.
     *
     * @param args ignored.
     */

}
