package com.cypods.dbupdater.Dto;

import org.springframework.stereotype.Component;

@Component
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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getIcon_large() {
        return icon_large;
    }

    public void setIcon_large(String icon_large) {
        this.icon_large = icon_large;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeIcon() {
        return typeIcon;
    }

    public void setTypeIcon(String typeIcon) {
        this.typeIcon = typeIcon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getMembers() {
        return members;
    }

    public void setMembers(String members) {
        this.members = members;
    }

    public ItemPriceTrendOsrsDTO getCurrent() {
        return current;
    }

    public void setCurrent(ItemPriceTrendOsrsDTO current) {
        this.current = current;
    }

    public ItemPriceTrendOsrsDTO getToday() {
        return today;
    }

    public void setToday(ItemPriceTrendOsrsDTO today) {
        this.today = today;
    }

    public ItemPriceTrendOsrsDTO getDay30() {
        return day30;
    }

    public void setDay30(ItemPriceTrendOsrsDTO day30) {
        this.day30 = day30;
    }

    public ItemPriceTrendOsrsDTO getDay60() {
        return day60;
    }

    public void setDay60(ItemPriceTrendOsrsDTO day60) {
        this.day60 = day60;
    }

    public ItemPriceTrendOsrsDTO getDay90() {
        return day90;
    }

    public void setDay90(ItemPriceTrendOsrsDTO day90) {
        this.day90 = day90;
    }

    @Override
    public String toString() {
        return "item{" +
                "icon='" + icon + '\'' +
                ", icon_large='" + icon_large + '\'' +
                ", id=" + id +
                ", type='" + type + '\'' +
                ", typeIcon='" + typeIcon + '\'' +
                ", name='" + name + '\'' +
                ", Description='" + Description + '\'' +
                ", current=" + current +
                ", today=" + today +
                ", members='" + members + '\'' +
                '}';
    }
}
