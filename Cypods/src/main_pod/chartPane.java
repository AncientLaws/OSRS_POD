package main_pod;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.xy.XYDataset;

import javafx.embed.swing.SwingNode;

//import org.jfree.chart.fx.ChartViewer;
import org.jfree.chart.JFreeChart;

public class chartPane {
	

	public static JFreeChart createChart() {
		 
        double[] values = { 95, 49, 14, 59, 50, 66, 47, 40, 1, 67,
                            12, 58, 28, 63, 14, 9, 31, 17, 94, 71,
                            49, 64, 73, 97, 15, 63, 10, 12, 31, 62,
                            93, 49, 74, 90, 59, 14, 15, 88, 26, 57,
                            77, 44, 58, 91, 10, 67, 57, 19, 88, 84                                
                          };
 
 
        HistogramDataset dataset = new HistogramDataset();
        dataset.addSeries("key", values, 20);
 
        //CategoryDataset categoryData = new CategoryDataset();
        
        
        
        //JFreeChart lineChart = ChartFactory.createLineChart("Torva",
           //                    "Price", "Time",  dataset);
        
        JFreeChart lineChart2 = ChartFactory.createXYLineChart("Torva",
                "Time", "Price", dataset);
        
        //
        lineChart2.setBorderVisible(true);
        lineChart2.getXYPlot();
        
        
 
        return lineChart2;
    }
     
	
}
