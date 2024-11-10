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
@NoArgsConstructor
@AllArgsConstructor
public class Data implements Serializable {
    Integer id;
    Integer high;
    Long highTime;
    Integer low;
    Long lowTime;
}
