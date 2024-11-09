package com.osrs.pod.database.domain.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.persistence.*;


@Table(name="items")
@Entity
@Component
@Getter
@Setter
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

}