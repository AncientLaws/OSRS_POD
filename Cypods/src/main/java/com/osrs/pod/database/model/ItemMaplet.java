package com.osrs.pod.database.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemMaplet implements Serializable {
    String examine;
    Integer id;
    boolean members;
    Integer lowalch;
    Integer limit;
    Integer value;
    Integer highalch;
    String icon;
    String name;

}
