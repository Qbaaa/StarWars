package com.qbaaa.StarWars.models;

import lombok.*;

@Builder
@Setter
@Getter
@EqualsAndHashCode
@ToString
public class HousesWorld {

    private int id;
    private String name;
    private String rotationPeriod;
    private String orbitalPeriod;
    private String diameter;
    private String climate;
    private String gravity;
    private String terrain;
    private String surfaceWater;
    private String population;
}
