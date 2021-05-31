package com.kondratenko.busparkemploeesworkcontrol.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TripAcceptanceForm {

    private Long driverTripId;

    private Long driverId;

    private String description;
}
