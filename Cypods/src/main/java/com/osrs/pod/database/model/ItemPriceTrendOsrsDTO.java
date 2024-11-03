package com.osrs.pod.database.model;

import org.springframework.stereotype.Component;

@Component
public class ItemPriceTrendOsrsDTO {
    String trend;
    String price;
    String change;

    public String getTrend() {
        return trend;
    }

    public void setTrend(String trend) {
        this.trend = trend;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setPrice(Integer price) {
        this.price = price.toString();
    }

    public String getChange() {
        return change;
    }

    public void setChange(String change) {
        this.change = change;
    }
}
