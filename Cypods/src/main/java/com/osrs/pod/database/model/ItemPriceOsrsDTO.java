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
public class ItemPriceOsrsDTO {
    String icon;
    String icon_large;
    Long id;
    String type;
    String typeIcon;
    String name;
    String Description;
    ItemPriceTrendOsrsDTO current ;
    ItemPriceTrendOsrsDTO today;
    ItemPriceTrendOsrsDTO day30;
    ItemPriceTrendOsrsDTO day60;
    ItemPriceTrendOsrsDTO day90;

    String members;

}
