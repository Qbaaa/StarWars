package com.qbaaa.StarWars.models;

import lombok.*;

@Builder
@Setter
@Getter
@EqualsAndHashCode
@ToString
public class StarShips {

    private int id;
    private String name;
    private String model;
    private String manufacturer;
    private String costInCredits;
    private String length;
    private String maxAtmospheringSpeed;
    private String crew;
    private String passengers;
    private String cargoCapacity;
    private String consumables;
    private String hyperdriveRating;
    private String mglt;
    private String starshipClass;
}
