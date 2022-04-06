package main_pod;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
                        "Date: " + d.getXValue().toString() + " Price : " + formatPrice(d.getYValue())));

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
        series.getData().add(new XYChart.Data(epochToHuman(	1648389900L	),	557000000	));
        series.getData().add(new XYChart.Data(epochToHuman(	1648390200L	),	559000003	));
        series.getData().add(new XYChart.Data(epochToHuman(	1648390800L	),	562338499	));
        series.getData().add(new XYChart.Data(epochToHuman(	1648391400L	),	562100000	));
        series.getData().add(new XYChart.Data(epochToHuman(	1648391700L	),	561999998	));
        series.getData().add(new XYChart.Data(epochToHuman(	1648392000L	),	557842500	));
        series.getData().add(new XYChart.Data(epochToHuman(	1648392600L	),	561998000	));
        series.getData().add(new XYChart.Data(epochToHuman(	1648393200L	),	556202000	));
        series.getData().add(new XYChart.Data(epochToHuman(	1648393500L	),	556305000	));
        series.getData().add(new XYChart.Data(epochToHuman(	1648394100L	),	561999997	));
        series.getData().add(new XYChart.Data(epochToHuman(	1648395300L	),	556325099	));
        series.getData().add(new XYChart.Data(epochToHuman(	1648395900L	),	556150000	));
        series.getData().add(new XYChart.Data(epochToHuman(	1648396500L	),	561999994	));
        series.getData().add(new XYChart.Data(epochToHuman(	1648397100L	),	562350000	));
        series.getData().add(new XYChart.Data(epochToHuman(	1648398000L	),	555555555	));
        series.getData().add(new XYChart.Data(epochToHuman(	1648398900L	),	562398000	));
        series.getData().add(new XYChart.Data(epochToHuman(	1648399500L	),	562350000	));
        series.getData().add(new XYChart.Data(epochToHuman(	1648399800L	),	562349995	));
        series.getData().add(new XYChart.Data(epochToHuman(	1648400100L	),	555599000	));
        series.getData().add(new XYChart.Data(epochToHuman(	1648400400L	),	562273305	));
        series.getData().add(new XYChart.Data(epochToHuman(	1648400700L	),	559000000	));
        series.getData().add(new XYChart.Data(epochToHuman(	1648401300L	),	556000000	));
        series.getData().add(new XYChart.Data(epochToHuman(	1648401600L	),	562339992	));
        series.getData().add(new XYChart.Data(epochToHuman(	1648402200L	),	555435500	));



        
        //System.out.println(grabData.getItemJsonPrice_RuneLine("https://prices.runescape.wiki/api/v1/osrs/timeseries?id=26374&timestep=5m"));
	}
	
	private String formatPrice(Number ItemPrice) {
		String s = "";
		BigDecimal bd = new BigDecimal(1);;
		
		if((ItemPrice.intValue() / 1000)<1000){
			double d = (ItemPrice.doubleValue() / 1000);
			bd = new BigDecimal(d).setScale(2, RoundingMode.HALF_UP );
			s = bd +"K";
			
		}
		else if((ItemPrice.intValue() / 1000)<1000000) {
			double d = (ItemPrice.doubleValue() / 1000000);
			bd = new BigDecimal(d).setScale(2, RoundingMode.HALF_UP );
			s = bd +"M";
		}
		else if((ItemPrice.intValue() / 1000)>1000000) {
			double d = (ItemPrice.doubleValue() / 10000000000L);
			bd = new BigDecimal(d).setScale(2, RoundingMode.HALF_UP );
			s = bd +"B";
		}
		else {
			s = ItemPrice.doubleValue()+"";
		}
		
		return s;
	}
}
