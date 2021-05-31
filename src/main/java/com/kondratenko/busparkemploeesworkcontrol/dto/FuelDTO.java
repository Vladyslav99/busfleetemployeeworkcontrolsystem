package com.kondratenko.busparkemploeesworkcontrol.dto;

import com.kondratenko.busparkemploeesworkcontrol.entity.Fuel;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FuelDTO {

    private BigDecimal price;

    private Fuel.FuelType fuelType;
}
