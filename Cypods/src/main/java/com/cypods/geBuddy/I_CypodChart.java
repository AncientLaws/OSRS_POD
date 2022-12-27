package com.cypods.geBuddy;

import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYDataset;

public interface I_CypodChart {

    void createChart();
    void runChart();
    XYDataset createItemPriceDataset(int i, String s);

}
