package com.osrs.pod.application.controllers;

import com.osrs.pod.application.services.DataModeler;
import javafx.application.Platform;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.fx.ChartViewer;
import org.jfree.chart.fx.interaction.ChartMouseEventFX;
import org.jfree.chart.fx.interaction.ChartMouseListenerFX;
import org.jfree.chart.fx.overlay.CrosshairOverlayFX;
import org.jfree.chart.panel.CrosshairOverlay;
import org.jfree.chart.plot.Crosshair;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.chart.ui.RectangleEdge;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DatasetUtils;
import org.jfree.data.time.*;
import org.jfree.data.xy.XYDataset;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.Calendar;

import static com.osrs.pod.application.ApplicationConstant.BORDERS;
import static com.osrs.pod.application.ApplicationConstant.DEBUG;

public class Charts implements ChartMouseListenerFX {

	private ChartViewer chartViewerPrice;
	private ChartViewer chartViewerVolume;

	private Crosshair xCrosshair;
	private Crosshair yCrosshair;

	JFreeChart priceChart;
	JFreeChart volumeChart;
	XYDataset dataset;
	XYDataset XYDatasetTimeSeriesVolume;
	DefaultCategoryDataset volumeCategoryDataset;
	CrosshairOverlayFX crosshairOverlay;
	TimeSeries series;
	TimeSeries volumeSeries;

	private double maxValue = 0;

	private double chartWidth;
	private double chartHeight;


	Color chartSeriesColor;
	Color chartBackgroundColor;

	javafx.geometry.Rectangle2D screenBounds;

	DataModeler dataModeler =  new DataModeler();

	VBox chartsPane = new VBox();

	Rectangle clipRect = new Rectangle();

	public Charts() {
	}

	public Charts(double chartWidth, double chartHeight) {
		this.chartWidth = chartWidth;
		this.chartHeight = chartHeight;

		dataset = dataModeler.createRandomDataset();
		volumeCategoryDataset = dataModeler.createRandomCategoryDataset();

		priceChart = createChart(dataset);
		volumeChart = createChart(volumeCategoryDataset);

		screenBounds = Screen.getPrimary().getVisualBounds();

		chartViewerVolume = new ChartViewer(volumeChart);
		chartViewerVolume.setPrefSize(chartWidth, 100);
		chartViewerVolume.setMinHeight(100);
		chartViewerVolume.getCanvas().getChart().setBackgroundPaint(new Color(108, 88, 56, 100));

		chartViewerPrice = new ChartViewer(priceChart);
		chartViewerPrice.setPrefSize(1063, 351);
		chartViewerPrice.setMinHeight(151); //351
		chartViewerPrice.getCanvas().getChart().setBackgroundPaint(new Color(108, 88, 56,100));

		initPriceChart(priceChart);
		initVolumeChart(volumeChart);
		if(BORDERS) {
			chartsPane.setStyle("-fx-border-color: orange");
		}
		/*Allow the price and volume chart nodes to grow within the vbox*/
		VBox.setVgrow(chartViewerPrice, Priority.ALWAYS);
		VBox.setVgrow(chartViewerVolume, Priority.ALWAYS);

		/*Clips off charts when the VBox area gets too small*/
		clipRect.widthProperty().bind(chartsPane.widthProperty());
		clipRect.heightProperty().bind(chartsPane.heightProperty());
		chartsPane.setClip(clipRect);

		chartsPane.getChildren().addAll(chartViewerPrice,chartViewerVolume);
	}


	@Override
	public void chartMouseClicked(ChartMouseEventFX event) { // ignore
	}

	private JFreeChart initPriceChart(JFreeChart chart) {

		chart.getXYPlot().getRenderer().setSeriesVisibleInLegend(0, false, false); //Makes legend invisible
		Color chartBackgroundColor = new Color(126, 102, 64, 100); //new Color(124, 101, 61);
		Color chartSeriesColor = new Color(120, 173, 255);

		chart.getPlot().setBackgroundPaint(chartBackgroundColor);//0x866b46
		chart.getXYPlot().getRenderer().setSeriesPaint(0, chartSeriesColor);
		chart.getXYPlot().getRenderer().setSeriesStroke(0, new BasicStroke(3.0f));
		chart.getXYPlot().getRenderer().setSeriesVisible(0, true);
		chart.getXYPlot().getRangeAxis().setTickLabelPaint(Color.ORANGE);
		chart.getXYPlot().getDomainAxis().setTickLabelPaint(Color.ORANGE);

		return chart;
	}
	private JFreeChart initVolumeChart(JFreeChart chart) {


		Color chartBackgroundColor = new Color(126, 102, 64,100); //new Color(124, 101, 61);
		Color chartSeriesColor = new Color(120, 173, 255);

		chart.getPlot().setBackgroundPaint(chartBackgroundColor);//0x866b46

		chart.getCategoryPlot().getRenderer().setSeriesPaint(0, chartSeriesColor);
		chart.getCategoryPlot().getRenderer().setSeriesStroke(0, new BasicStroke(3.0f));
		chart.getCategoryPlot().getRenderer().setDefaultOutlinePaint(chartSeriesColor);

		chart.getCategoryPlot().getRenderer().setSeriesVisible(0, true);
		chart.getCategoryPlot().getRangeAxis().setTickLabelPaint(Color.ORANGE);
		chart.getCategoryPlot().getDomainAxis().setTickLabelPaint(Color.ORANGE);
		((BarRenderer) chart.getCategoryPlot().getRenderer()).setBarPainter(new StandardBarPainter()); //Old look of bar chart

		chart.removeLegend();

		return chart;
	}

	/**
	 * Method to create a price/volume/etc chart once the item is selected by the user.
	 */
	protected void runChart(int itemID, String timePeriod) {
//		chartViewerPrice = null;
//		chartViewerVolume = null;
		volumeChart = null;
		priceChart = null;
		dataset = null;
		XYDatasetTimeSeriesVolume = null;
		volumeCategoryDataset = null;
		series = null;
		volumeSeries = null;
		chartsPane.getChildren().clear();

		initCrosshairOverlay();

		createItemPriceAndVolumeDataset(itemID, timePeriod);
		priceChart = createChart(dataset);
		volumeChart = createChart(XYDatasetTimeSeriesVolume); 						//Create Line Chart
		//volumeChart = createChart(volumeCategoryDataset);
		//volumeCategoryDataset = dataModeler.createRandomCategoryDataset();

		chartViewerPrice.setChart(priceChart);
		chartViewerVolume.setChart(volumeChart);


		chartBackgroundColor = new Color(126, 102, 64,100); //new Color(124, 101, 61);
		chartSeriesColor = new Color(120, 173, 255);

		runPriceChartSettings();
		runVolumeChartSettings();


		Platform.runLater(() -> {
			this.yCrosshair.setLabelVisible(true);
			crosshairOverlay.addDomainCrosshair(xCrosshair);
			crosshairOverlay.addRangeCrosshair(yCrosshair);

			this.chartViewerPrice.getCanvas().addOverlay(crosshairOverlay);
			this.chartViewerVolume.getCanvas().addOverlay(crosshairOverlay);

			chartViewerVolume.setPrefSize(chartViewerVolume.getPrefWidth(), chartViewerVolume.getPrefHeight());
			chartViewerPrice.setPrefSize(chartViewerPrice.getPrefWidth(), chartViewerPrice.getPrefHeight());

			formatChartValues(priceChart, chartViewerPrice);
			formatChartValues(volumeChart, chartViewerVolume);

			/*Clips off chart node when the VBox area gets too small (stops overflow)*/
			clipRect.widthProperty().bind(chartsPane.widthProperty());
			clipRect.heightProperty().bind(chartsPane.heightProperty());
			chartsPane.setClip(clipRect);

			chartsPane.getChildren().addAll(chartViewerPrice,chartViewerVolume);
		});

	}

	/**
	 * Creates randomized data
	 * Exists because when the program start, it looks better when there is data in the graph
	 *
	 * @return XYDataset
	 */

	private void runPriceChartSettings(){
		defaultChartSettings(priceChart);
		priceChart.getXYPlot().getRangeAxis().setTickLabelPaint(Color.ORANGE);
		priceChart.getXYPlot().getDomainAxis().setTickLabelPaint(Color.ORANGE);
		priceChart.getXYPlot().getRangeAxis().setFixedDimension(40);


//		chartViewerPrice.setPrefSize(chartWidth, 351);
		chartViewerPrice.addChartMouseListener(this);
		chartViewerPrice.getCanvas().getChart().setBackgroundPaint(new Color(108, 88, 56,100));
//		chartViewerPrice.setTranslateX(5);
		chartViewerPrice.setPrefSize(chartWidth, 500); //new
		chartViewerPrice.setMinHeight(151); //351
		VBox.setVgrow(chartViewerPrice, Priority.ALWAYS); // Allow the price chart to grow
	}

	private void runVolumeChartSettings(){
		defaultChartSettings(volumeChart);
		volumeChart.getXYPlot().getRangeAxis().setTickLabelPaint(Color.ORANGE);
		volumeChart.getXYPlot().getDomainAxis().setTickLabelPaint(Color.ORANGE);
		volumeChart.getXYPlot().getRangeAxis().setFixedDimension(40);

//		chartViewerVolume.setPrefSize(chartWidth, 100);
		chartViewerVolume.addChartMouseListener(this);
//		chartViewerVolume.setTranslateX(chartViewerVolumeOffset);
		chartViewerVolume.setPrefSize(chartWidth, 100);
		chartViewerVolume.setMinHeight(100);
		chartViewerVolume.getCanvas().getChart().setBackgroundPaint(new Color(108, 88, 56,100));
		VBox.setVgrow(chartViewerVolume, Priority.ALWAYS); // Allow the volume chart to grow
	}

	private void defaultChartSettings(JFreeChart volumeChart) {
		volumeChart.getXYPlot().getRenderer().setSeriesVisibleInLegend(0, false, false); //Makes legend invisible
		volumeChart.getPlot().setBackgroundPaint(chartBackgroundColor);//0x866b46
		volumeChart.getXYPlot().getRenderer().setSeriesPaint(0, chartSeriesColor);
		volumeChart.getXYPlot().getRenderer().setSeriesStroke(0, new BasicStroke(3.0f));
		volumeChart.getXYPlot().getRenderer().setSeriesVisible(0, true);
		volumeChart.getXYPlot().setRangePannable(true);
		volumeChart.getXYPlot().setRangeCrosshairLockedOnData(true);
		volumeChart.getXYPlot().setDomainPannable(true);
	}

	private static JFreeChart createChart(XYDataset dataset) {
		JFreeChart chart = ChartFactory.createTimeSeriesChart(null, null, null, dataset);//createXYLineChart(null, null, null, dataset);
		//JFreeChart volumeChart = ChartFactory.createBarChart(null, null, null, dataset);
		return chart;
	}
	private static JFreeChart createChart(DefaultCategoryDataset dataset) {
		JFreeChart volumeChart = ChartFactory.createBarChart(null, null, null, dataset);
		//JFreeChart volumeChart = ChartFactory.createXYBarChart()
		return volumeChart;
	}
	protected ChartViewer charts_chartViewerPrice() {
		return chartViewerPrice;
	}

	protected ChartViewer charts_chartViewerVolume() {
		return chartViewerVolume;
	}

	private CrosshairOverlay initCrosshairOverlay (){

		crosshairOverlay = new CrosshairOverlayFX();
		this.xCrosshair = new Crosshair(Double.NaN, Color.WHITE, new BasicStroke(0f));
		Crosshair zCrosshair = new Crosshair();
		this.xCrosshair.setStroke(
				new BasicStroke(1.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 1, new float[]{2.0f, 2.0f}, 0));
		this.xCrosshair.setLabelVisible(false);
		this.yCrosshair = new Crosshair(Double.NaN, Color.WHITE, new BasicStroke(0f));
		this.yCrosshair.setStroke(
				new BasicStroke(1.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 1, new float[]{2.0f, 2.0f}, 0));
		this.yCrosshair.setLabelVisible(true);

		this.yCrosshair.setLabelPaint(Color.WHITE);
		this.yCrosshair.setLabelBackgroundPaint(new Color(126, 102, 64));

		return crosshairOverlay;
	}

	/**
	 * Method that creates the dataset used to populate the graph
	 * Input: ItemID, timeperiod
	 *
	 * @return XYDataset
	 */

	public void createItemPriceAndVolumeDataset(int itemID, String timePeriod) {
		series = new TimeSeries("Price");
		volumeSeries = new TimeSeries("Volume");
		RequestController getDataGet = new RequestController();
		String[][] itemPriceArrayStrings;
		String x = null;
		Double y;
		double z;
		Calendar cal = Calendar.getInstance();


			if(timePeriod == "6Month"){
				itemPriceArrayStrings = getDataGet.get_api_parseRuneLitePrice("24h",itemID); //Retrieve a years worth of data
			}
			else{
				itemPriceArrayStrings = getDataGet.get_api_parseRuneLitePrice(timePeriod,itemID);
			}

			cal.add(Calendar.MONTH, -6);
			for (int i = 0; i < itemPriceArrayStrings.length - 1; i++) {
				//If item price value isn't available, don't add it to the dataset
				if (Double.parseDouble(itemPriceArrayStrings[i][1]) == 0) {
					continue;
				} else if (Double.parseDouble(itemPriceArrayStrings[i][2]) == 0) {
					continue;
				} else if (Double.parseDouble(itemPriceArrayStrings[i][3]) == 0) {
					continue;
				} else if (Double.parseDouble(itemPriceArrayStrings[i][4]) == 0) {
					continue;
				}

				x = itemPriceArrayStrings[i][0]; 										//timestamp
				y = dataModeler.avgValue(Double.parseDouble(itemPriceArrayStrings[i][1]), 			//item price high
						Double.parseDouble(itemPriceArrayStrings[i][2])); 				//item price low
				z = dataModeler.avgValue(Double.parseDouble(itemPriceArrayStrings[i][3]),			//item price high
						Double.parseDouble(itemPriceArrayStrings[i][4])); 				//item price low

				Second second = new Second(dataModeler.epochToDateTime_x1000(x));

				//Custom timeseries for 6 months
				if(timePeriod == "6Month"){
					if(second.getFirstMillisecond() > cal.getTimeInMillis()){
						series.addOrUpdate(second, y);
						volumeSeries.addOrUpdate(second, z);
					}
				}
				else{
					series.addOrUpdate(second, y);
					volumeSeries.addOrUpdate(second, z);
				}

				Number n = Double.parseDouble(x);
				//volumeCategoryDataset.addValue(n,y,z);

				if (DEBUG == true) {
					System.out.println("\nnew Day(epochToDateTime_x1000(x)) \t: " + new Day(dataModeler.epochToDateTime_x1000(x)) +
							"\nnew Minute (epochToDateTime_x1000(x)): \t" + new Minute(dataModeler.epochToDateTime_x1000(x))
							+ "\nnew Second (epochToDateTime_x1000(x)): \\t" + new Second(dataModeler.epochToDateTime_x1000(x)));
				}

			}




		XYDatasetTimeSeriesVolume = new TimeSeriesCollection(volumeSeries);
		volumeCategoryDataset = new DefaultCategoryDataset();
		dataset = new TimeSeriesCollection(series);

		//return new TimeSeriesCollection(series);
	}
	/**
	 * @Purpose
	 * Keep the cross-hair on the datapoint in the graph
	 */
	public void chartMouseMoved(ChartMouseEventFX event) {
		Rectangle2D dataArea = null;
		JFreeChart chart = event.getChart();

		// Bug fix: Set the dataArea (chart size & pointer location) based on which chart is calling this event
		if(event.getChart() == priceChart) {
			dataArea = this.chartViewerPrice.getCanvas().getRenderingInfo().getPlotInfo().getDataArea();
		} else if (event.getChart() == volumeChart) {
			dataArea = this.chartViewerVolume.getCanvas().getRenderingInfo().getPlotInfo().getDataArea();
		}
		else { //Exception going to be thrown if a new chart is defined in the future, handle this
			dataArea = null;//this.chartViewerPrice.getCanvas().getRenderingInfo().getPlotInfo().getDataArea();
		}
		XYPlot plot = (XYPlot) chart.getPlot();
		ValueAxis xAxis = plot.getDomainAxis();
		double x = xAxis.java2DToValue(event.getTrigger().getX(), dataArea,
				RectangleEdge.BOTTOM);


		double y = DatasetUtils.findYValue(plot.getDataset(), 0, x);
		this.xCrosshair.setValue(x);
		this.yCrosshair.setValue(Math.round(y));


	}



	/**
	 * Method that resizes chart width
	 * Exists because an event listener needs to use this method to update chart size
	 *
	 * @return nothing
	 */
	protected void resizeChartH(VBox chartsPane, double h) {
		chartsPane.setPrefHeight(h);
		chartHeight = h;
	}

	/**
	 * Method that resizes chart Height
	 * Exists because an event listener needs to use this method to update chart size
	 *
	 * @return nothing
	 */
	protected void resizeChartW(VBox chartsPane, double w) {
		chartsPane.setPrefWidth(w);
		chartWidth = w;
	}


	/**
	 * @Purpose
	 * Shortens the numbers in the axis of graphs to make it easily read-able  j
	 * */
	private void formatChartValues(JFreeChart chart, ChartViewer chartViewer){
		final long MILLION = 1000000L;
		final long BILLION = 1000000000L;
		final long TRILLION = 1000000000000L;
		final long THOUSAND = 1000L;

		NumberAxis chartRangeAxis = (NumberAxis) chart.getXYPlot().getRangeAxis();
		dataModeler.setNumberFormatOverrideAxis(MILLION, BILLION, TRILLION, THOUSAND, chartRangeAxis);
		chartViewer.getChart().getXYPlot().setRangeAxis(chartRangeAxis);
	}

	public VBox getChartsPane() {
		return chartsPane;
	}

	public void setChartsPane(VBox chartsPane) {
		this.chartsPane = chartsPane;
	}

	public ChartViewer getChartViewerPrice() {
		return chartViewerPrice;
	}

	public void setChartViewerPrice(ChartViewer chartViewerPrice) {
		this.chartViewerPrice = chartViewerPrice;
	}

	public ChartViewer getChartViewerVolume() {
		return chartViewerVolume;
	}

	public void setChartViewerVolume(ChartViewer chartViewerVolume) {
		this.chartViewerVolume = chartViewerVolume;
	}

	public void clearChartsPane(){
		chartsPane.getChildren().clear();
	}

}

