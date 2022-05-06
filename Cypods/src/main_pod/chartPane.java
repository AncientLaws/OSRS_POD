package main_pod;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.geom.Rectangle2D;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.fx.ChartViewer;
import org.jfree.chart.fx.interaction.ChartMouseEventFX;
import org.jfree.chart.fx.interaction.ChartMouseListenerFX;
import org.jfree.chart.fx.overlay.CrosshairOverlayFX;
import org.jfree.chart.plot.Crosshair;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.ui.RectangleEdge;
import org.jfree.data.general.DatasetUtils;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

//import org.jfree.chart.fx.ChartViewer;


public class chartPane extends StackPane implements ChartMouseListenerFX {
    
    private ChartViewer chartViewer;

    private Crosshair xCrosshair;

    private Crosshair yCrosshair;

    public chartPane() {
        XYDataset dataset = createDataset();
        JFreeChart chart = createChart(dataset); 
        this.chartViewer = new ChartViewer(chart);
        this.chartViewer.addChartMouseListener(this);
        getChildren().add(this.chartViewer);
       
        CrosshairOverlayFX crosshairOverlay = new CrosshairOverlayFX();
        this.xCrosshair = new Crosshair(Double.NaN, Color.GRAY, 
                new BasicStroke(0f));
        this.xCrosshair.setStroke(new BasicStroke(1.5f, 
                BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 1, 
                new float[]{2.0f, 2.0f}, 0));
        this.xCrosshair.setLabelVisible(true);
        this.yCrosshair = new Crosshair(Double.NaN, Color.GRAY, 
                new BasicStroke(0f));
        this.yCrosshair.setStroke(new BasicStroke(1.5f, 
                BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 1, 
                new float[] {2.0f, 2.0f}, 0));
        this.yCrosshair.setLabelVisible(true);
        crosshairOverlay.addDomainCrosshair(xCrosshair);
        crosshairOverlay.addRangeCrosshair(yCrosshair);
        
        Platform.runLater(() -> {
            this.chartViewer.getCanvas().addOverlay(crosshairOverlay);
        });
    }

    @Override
    public void chartMouseClicked(ChartMouseEventFX event) {
        // ignore
    }

    @Override
    public void chartMouseMoved(ChartMouseEventFX event) {
        Rectangle2D dataArea = this.chartViewer.getCanvas().getRenderingInfo().getPlotInfo().getDataArea();
        JFreeChart chart = event.getChart();
        XYPlot plot = (XYPlot) chart.getPlot();
        ValueAxis xAxis = plot.getDomainAxis();
        double x = xAxis.java2DToValue(event.getTrigger().getX(), dataArea, 
                RectangleEdge.BOTTOM);
        // make the crosshairs disappear if the mouse is out of range
        if (!xAxis.getRange().contains(x)) { 
            x = Double.NaN;                  
        }
        double y = DatasetUtils.findYValue(plot.getDataset(), 0, x);
        this.xCrosshair.setValue(x);
        this.yCrosshair.setValue(y);
    }
    private static XYDataset createDataset() {
        XYSeries series = new XYSeries("S1");
        for (int x = 0; x < 10; x++) {
            series.add(x, x + Math.random() * 4.0);
        }
        XYSeriesCollection dataset = new XYSeriesCollection(series);
        return dataset;
    }

    private static JFreeChart createChart(XYDataset dataset) {
        JFreeChart chart = ChartFactory.createXYLineChart(
                "CrosshairOverlayDemo1", "X", "Y", dataset);
        return chart;
    } 
    
}
