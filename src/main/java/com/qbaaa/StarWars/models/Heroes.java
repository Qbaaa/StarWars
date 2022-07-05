package com.qbaaa.StarWars.models;

import lombok.*;
import java.util.List;

@Builder
@Setter
@Getter
@EqualsAndHashCode
@ToString
public class Heroes {

    private int id;
    private String name;
    private String height;
    private String mass;
    private String hairColor;
    private String skinColor;
    private String eyeColor;
    private String birthYear;
    private String gender;
    private HousesWorld homeWorld;
    private List<StarShips> listStarShips;
}
