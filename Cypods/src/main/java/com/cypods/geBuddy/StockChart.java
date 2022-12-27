package com.cypods.geBuddy;

// Import the necessary classes
import javafx.scene.chart.Chart;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.fx.ChartViewer;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.CandlestickRenderer;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.data.xy.*;
import org.springframework.stereotype.Component;

import java.awt.*;


public class StockChart extends ApplicationFrame {

    static IntervalXYDataset dataset = null;
    private ChartViewer chartViewerPrice;
    private ChartViewer chartViewerVolume;
    JFreeChart priceChart;
    JFreeChart volumeChart;
    DataModeler dataModeler = new DataModeler();


    public ChartViewer getChartViewerPrice() {
        setChartViewerPrice();
        return chartViewerPrice;
    }

    public ChartViewer getChartViewerVolume() {
        setChartViewerVolume();
        return chartViewerVolume;
    }

    public void setChartViewerPrice() {
        chartViewerPrice = new ChartViewer(createChart(createDataset()));
        chartViewerPrice.setPrefSize(1063, 250); //.setPrefSize(1063, 351)
        chartViewerPrice.setLayoutX(4);  //setLayoutX(4);
        chartViewerPrice.setLayoutY(52);  //setLayoutY(52);
        chartViewerPrice.getCanvas().getChart().setBackgroundPaint(new Color(108, 88, 56));
    }

    public void setChartViewerVolume() {
        chartViewerVolume = new ChartViewer(createChart((IntervalXYDataset) dataModeler.createRandomDataset()));
        chartViewerVolume.setPrefSize(1063, 100);
        chartViewerVolume.setLayoutX(4);
        chartViewerVolume.setLayoutY(301);
        chartViewerVolume.getCanvas().getChart().setBackgroundPaint(new Color(108, 88, 56));
    }



    public StockChart(final String title) {
        super(title);
        //setContentPane(createDemoPanel());
    }

    private static IntervalXYDataset createDataset() {
        XYSeries series = new XYSeries("S1");
        for (int x = 0; x < 10; x++) {
            series.add(x, x + Math.random() * 4.0);
        }
        XYSeriesCollection dataset = new XYSeriesCollection(series);

        return dataset;
    }

    private static JFreeChart createChart(final IntervalXYDataset dataset) {
        final JFreeChart chart = ChartFactory.createCandlestickChart(
                "Candlestick Demo",
                "Time",
                "Price",
                (OHLCDataset) dataset,
                true
        );
        final XYPlot plot = (XYPlot) chart.getPlot();
        final DateAxis axis = (DateAxis) plot.getDomainAxis();
        axis.setLowerMargin(0.0);
        axis.setUpperMargin(0.0);
        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setLowerMargin(0.30);
        rangeAxis.setUpperMargin(0.30);
        final CandlestickRenderer renderer = (CandlestickRenderer) plot.getRenderer();
        renderer.setAutoWidthMethod(CandlestickRenderer.WIDTHMETHOD_SMALLEST);
        renderer.setAutoWidthGap(0.001);
        renderer.setUpPaint(Color.red);
        renderer.setDownPaint(Color.green);
        plot.setRenderer(renderer);
        final NumberAxis volumeAxis = new NumberAxis("Volume");
        volumeAxis.setRange(0, 100);
        final XYBarRenderer volumeRenderer = new XYBarRenderer();
        plot.setDataset(1, createVolumeDataset());
        plot.setRenderer(1, volumeRenderer);
        plot.setRangeAxis(1, volumeAxis);
        plot.mapDatasetToRangeAxis(1, 1);
        return chart;
    }

    private static XYDataset createVolumeDataset() {
        XYSeries series = new XYSeries("S1");
        for (int x = 0; x < 10; x++) {
            series.add(x, x + Math.random() * 4.0);
        }
        XYSeriesCollection dataset = new XYSeriesCollection(series);
        return dataset;
    }

    public static ChartPanel createDemoPanel() {
        final JFreeChart chart = createChart(createDataset());
        return new ChartPanel(chart);
    }

}

