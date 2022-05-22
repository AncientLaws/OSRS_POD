package main_pod;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.LegendItem;
import org.jfree.chart.LegendItemCollection;
import org.jfree.chart.LegendItemSource;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.date.SerialDate;
import org.jfree.chart.fx.ChartViewer;
import org.jfree.chart.fx.interaction.ChartMouseEventFX;
import org.jfree.chart.fx.interaction.ChartMouseListenerFX;
import org.jfree.chart.fx.overlay.CrosshairOverlayFX;
import org.jfree.chart.labels.XYToolTipGenerator;
import org.jfree.chart.plot.Crosshair;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.ui.RectangleEdge;
import org.jfree.data.general.DatasetUtils;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.data.time.Day;
import org.jfree.data.time.Minute;
import org.jfree.data.time.RegularTimePeriod;
import org.jfree.data.time.Second; 
import org.jfree.data.time.TimeSeries; 
import org.jfree.data.time.TimeSeriesCollection; 
import org.jfree.data.xy.XYDataset; 

//import org.jfree.chart.fx.ChartViewer;
//test commit dev branch
public class charts implements ChartMouseListenerFX {

	private ChartViewer chartViewer;

	private Crosshair xCrosshair;

	private Crosshair yCrosshair;
	JFreeChart chart;
	XYDataset dataset;
	CrosshairOverlayFX crosshairOverlay;
	
	private double maxValue = 0;

	public charts() {
		dataset = createDataset();
		chart = createChart(dataset);
		
		chartViewer = new ChartViewer(chart);
		chartViewer.setPrefSize(1063, 351);
		chartViewer.setLayoutX(4);
		chartViewer.setLayoutY(52);
		//chartViewer.addChartMouseListener(this);
		// getChildren().add(this.chartViewer);


        chart.getXYPlot().getRenderer().setSeriesVisibleInLegend(0, false, false); //Makes legend invisible
        Color chartBackgroundColor = new Color(126, 102, 64); //new Color(124, 101, 61);
        Color chartSeriesColor = new Color(120, 173, 255);
        chart.getPlot().setBackgroundPaint( chartBackgroundColor);//0x866b46
        chart.getXYPlot().getRenderer().setSeriesPaint(0, chartSeriesColor);
        chart.getXYPlot().getRenderer().setSeriesStroke(0, new BasicStroke(3.0f));
        chart.getXYPlot().getRenderer().setSeriesVisible(0, true);

		crosshairOverlay = new CrosshairOverlayFX();
		Crosshair x = new Crosshair();
		
		this.xCrosshair = new Crosshair(Double.NaN, Color.WHITE, new BasicStroke(0f));
		this.xCrosshair.setStroke(
				new BasicStroke(1.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 1, new float[] { 2.0f, 2.0f }, 0));
		this.xCrosshair.setLabelVisible(true);
		this.yCrosshair = new Crosshair(Double.NaN, Color.WHITE, new BasicStroke(0f));
		this.yCrosshair.setStroke(
				new BasicStroke(1.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 1, new float[] { 2.0f, 2.0f }, 0));
		this.yCrosshair.setLabelVisible(true);
		crosshairOverlay.addDomainCrosshair(xCrosshair);
		crosshairOverlay.addRangeCrosshair(yCrosshair);
    	
		
		 Platform.runLater(() -> {
		 this.chartViewer.getCanvas().addOverlay(crosshairOverlay); });
		 
	}

	
	  @Override public void chartMouseClicked(ChartMouseEventFX event) { // ignore
	  }
	 

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
        
        xAxis.setLabel("test ");
        double y = DatasetUtils.findYValue(plot.getDataset(), 0, x);
        this.xCrosshair.setValue(x);
        this.yCrosshair.setValue(y);

    }
    protected void runchart(String url) {
    	dataset = createItemPriceDataset(url);
		chart = createChart(dataset);
		chartViewer = new ChartViewer(chart);
		chartViewer.setPrefSize(1063, 351);
		chartViewer.setLayoutX(4);
		chartViewer.setLayoutY(52);
		chartViewer.addChartMouseListener(this);
		
        chart.getXYPlot().getRenderer().setSeriesVisibleInLegend(0, false, false); //Makes legend invisible
        Color chartBackgroundColor = new Color(126, 102, 64); //new Color(124, 101, 61);
        Color chartSeriesColor = new Color(120, 173, 255);
        chart.getPlot().setBackgroundPaint(chartBackgroundColor);//0x866b46
        chart.getXYPlot().getRenderer().setSeriesPaint(0, chartSeriesColor);
        chart.getXYPlot().getRenderer().setSeriesStroke(0, new BasicStroke(3.0f));
        chart.getXYPlot().getRenderer().setSeriesVisible(0, true);
        
        crosshairOverlay = new CrosshairOverlayFX();
		this.xCrosshair = new Crosshair(Double.NaN, Color.WHITE, new BasicStroke(0f));
		this.xCrosshair.setStroke(
				new BasicStroke(1.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 1, new float[] { 2.0f, 2.0f }, 0));
		this.xCrosshair.setLabelVisible(false);
		this.yCrosshair = new Crosshair(Double.NaN, Color.WHITE, new BasicStroke(0f));
		this.yCrosshair.setStroke(
				new BasicStroke(1.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 1, new float[] { 2.0f, 2.0f }, 0));
		this.yCrosshair.setLabelVisible(true);
		crosshairOverlay.addDomainCrosshair(xCrosshair);
		crosshairOverlay.addRangeCrosshair(yCrosshair);
    	
		
		 Platform.runLater(() -> {
		 this.chartViewer.getCanvas().addOverlay(crosshairOverlay); });

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
		JFreeChart chart = ChartFactory.createTimeSeriesChart(null, null, null, dataset);//createXYLineChart(null, null, null, dataset);
		
		return chart;
	}

	protected ChartViewer charts_chartViewer() {
		return chartViewer;
	}
	
	protected XYDataset createItemPriceDataset(String url) {
		TimeSeries series = new TimeSeries( "Item Data" );
		GET getDataGet = new GET();
		String [][] itemPriceArrayStrings;
		itemPriceArrayStrings = getDataGet.getItemJsonPrice_RuneLine(url);
	   		
		for(int i = 0; i < itemPriceArrayStrings.length-1;i++) {
			//If item price value isn't available, don't add it to the dataset
			if(Double.parseDouble(itemPriceArrayStrings[i][1]) == 0) {
				continue;
			}
			String x = itemPriceArrayStrings[i][0]; //timestamp
			Double y = Double.parseDouble(itemPriceArrayStrings[i][1])/1000; //item price
			
			series.addOrUpdate(new Second(epochToDateTime(x)), y);
			
			if(y > maxValue) {maxValue = y;	}
			
			System.out.println("\nnew Day(epochToDateTime(x)) \t: " + new Day(epochToDateTime(x)) + 
			"\nnew Minute (epochToDateTime(x)): \t" + new Minute (epochToDateTime(x)) 
			+ "\nnew Second (epochToDateTime(x)): \\t" + new Second (epochToDateTime(x)));
			
			
		}

		return new TimeSeriesCollection(series);
	}
	
	private Date epochToDateTime(String epoch) {
		
		Long longEpoch = Long.parseLong(epoch)*1000;  
		LocalDateTime localDateTime = Instant.ofEpochMilli(longEpoch).atZone(ZoneId.systemDefault()).toLocalDateTime();
		Instant i = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
		java.util.Date date1 = Date.from(i);
		return  date1;
	}
	

}
