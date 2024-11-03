package com.cypods.dbupdater.Dto;

import org.springframework.stereotype.Component;

@Component
public class itemMaplet {
    String examine;
    Integer id;
    boolean members;
    Integer lowalch;
    Integer limit;
    Integer value;
    Integer highalch;
    String icon;
    String name;

    public String getExamine() {
        return examine;
    }

    public void setExamine(String examine) {
        this.examine = examine;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isMembers() {
        return members;
    }

    public void setMembers(boolean members) {
        this.members = members;
    }

    public Integer getLowalch() {
        return lowalch;
    }

    public void setLowalch(Integer lowalch) {
        this.lowalch = lowalch;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Integer getHighalch() {
        return highalch;
    }

    public void setHighalch(Integer highalch) {
        this.highalch = highalch;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
