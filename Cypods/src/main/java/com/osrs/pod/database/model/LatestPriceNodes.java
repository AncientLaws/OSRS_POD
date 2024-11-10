package com.osrs.pod.database.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LatestPriceNodes implements Serializable {
    private Map<String, Data> data;

    // Getter and setter
    public Map<String, Data> getData() {
        return data;
    }

    @Override
    public String toString() {
        return "LatestPriceNodes{" +
                "data=" + data +
                '}';
    }

}
