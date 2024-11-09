package com.osrs.pod.database.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Item {
    public Integer id;
    public String icon;
    public String icon_large;
    public String type;
    public String typeIcon;
    public String name;
    public String description;
    public Current current;
    public Today today;
    public String members;
    public Day30 day30;
    public Day90 day90;
    public Day180 day180;
}
