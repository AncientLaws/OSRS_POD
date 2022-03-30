package main_pod;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.embed.swing.SwingNode;
import javafx.geometry.Side;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Tooltip;
import javafx.util.Duration;

//import org.jfree.chart.fx.ChartViewer;


public class chartPane {
	 //defining the axes
    final CategoryAxis xAxis = new CategoryAxis(); 
    final NumberAxis yAxis = new NumberAxis();
    final LineChart<String, Number> lineChart = new LineChart<String,Number>(xAxis, yAxis);
    XYChart.Series series = new XYChart.Series();
    
    GET grabData = new GET();
    
	public LineChart createChart() {
  
		/*Setting linechart labelss*/
		xAxis.setLabel("Date");
        xAxis.setAnimated(true); // axis animations are removed
        yAxis.setLabel("Price");
        yAxis.setAnimated(true); // axis animations are removed
        xAxis.applyCss();
        lineChart.setTitle("Torva Full helm");
        lineChart.setAnimated(true); // disable animations
        series.setName("Date");
        lineChart.setPrefSize(730, 370);

        // add series to chart
        lineChart.getData().add(series);
        
        chart_populateChart();
        installToolTip();
        
        return lineChart;
    }
	
	/**Converting epoch date format returned from runeline to normal date*/
	private String epochToHuman(Long d) {
		Long d1 = d*1000;
		Date date = new Date(d1);
		DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		String formatted = format.format(date);
		
		return formatted;
	}
	
   /**Installing tooltip for each data point*/
	private void installToolTip() {
        for (XYChart.Series<String, Number> s : lineChart.getData()) {
            for (XYChart.Data<String, Number> d : s.getData()) {
            	Tooltip tt = new Tooltip();
            	tt.setShowDelay(Duration.millis(100));
            	tt.install(d.getNode(), new Tooltip(
                        "Date: " + d.getXValue().toString() + " Price : " + d.getYValue()));

                //Adding css class on hover
                d.getNode().setOnMouseEntered(event -> d.getNode().getStyleClass().add("onHover"));
                //Removing css class on exit
                d.getNode().setOnMouseExited(event -> d.getNode().getStyleClass().remove("onHover"));
            }
        }
	}
	
	/**Populating the chart with data*/
	protected void chart_populateChart() {
		//grabData.getItemJsonPrice_RuneLine("https://prices.runescape.wiki/api/v1/osrs/timeseries?id=26374&timestep=5m");
		//String s = grabData.getItemJsonPrice_RuneLine(null)[0][1];
        /*Populating chart data*/
        series.getData().add(new XYChart.Data(epochToHuman(1648387200L), 1));
        series.getData().add(new XYChart.Data(epochToHuman(1648387500L), 15));
        series.getData().add(new XYChart.Data(epochToHuman(1648391400L), 23));
        series.getData().add(new XYChart.Data(epochToHuman(1648405200L), 30));
        
        System.out.println(grabData.getItemJsonPrice_RuneLine("https://prices.runescape.wiki/api/v1/osrs/timeseries?id=26374&timestep=5m"));
	}
	
}
