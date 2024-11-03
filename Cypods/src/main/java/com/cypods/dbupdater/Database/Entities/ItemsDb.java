package com.cypods.dbupdater.Database.Entities;

import org.springframework.stereotype.Component;

import javax.persistence.*;

@Entity
@Table(name="items")
public class ItemsDb {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Long id;

//    @Column(name = "item_id") // Add this line to specify the column name
    private Integer itemId;

    @Column
    private String item_name;

    @Column
    private String item_examine;

    @Column
    private Integer item_limit;

    @Column
    private Integer item_high_alch;

    @Column
    private Integer item_low_alch;

//    @Lob
    @Column
    private byte[] data;

    @Column
    private Integer item_value;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getItem_examine() {
        return item_examine;
    }

    public void setItem_examine(String item_examine) {
        this.item_examine = item_examine;
    }

    public Integer getItem_limit() {
        return item_limit;
    }

    public void setItem_limit(Integer item_limit) {
        this.item_limit = item_limit;
    }

    public Integer getItem_high_alch() {
        return item_high_alch;
    }

    public void setItem_high_alch(Integer item_high_alch) {
        this.item_high_alch = item_high_alch;
    }

    public Integer getItem_low_alch() {
        return item_low_alch;
    }

    public void setItem_low_alch(Integer item_low_alch) {
        this.item_low_alch = item_low_alch;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public Integer getItem_value() {
        return item_value;
    }

    public void setItem_value(Integer item_value) {
        this.item_value = item_value;
    }
}