package com.cypods.geBuddy;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class item {
    String icon;
    String icon_large;
    Long id;
    String type;
    String typeIcon;
    String name;
    String Description;
    List current ;
    List today;
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

    public List getCurrent() {
        return current;
    }

    public void setCurrent(List current) {
        this.current = current;
    }

    public List getToday() {
        return today;
    }

    public void setToday(List today) {
        this.today = today;
    }

    public String getMembers() {
        return members;
    }

    public void setMembers(String members) {
        this.members = members;
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
