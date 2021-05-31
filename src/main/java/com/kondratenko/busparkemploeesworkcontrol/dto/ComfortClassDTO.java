package com.kondratenko.busparkemploeesworkcontrol.dto;

import com.kondratenko.busparkemploeesworkcontrol.entity.ComfortClass;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ComfortClassDTO {

    private BigDecimal price;


    private ComfortClass.ComfortClassType comfortClassType;
}
