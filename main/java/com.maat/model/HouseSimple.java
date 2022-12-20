package com.maat.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * POJO class to represent a House from the Maat database as an object without nested objects.
 * @author Brand Hauser
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class HouseSimple implements Serializable {

    private static final long serialVersionUID = -1763669642834815641L;

    public HouseSimple(String name) {
        this.name = name;
    }

    private String name;

    private String houseKeeperName;

    private int houseKeeperIdNumber;

    private String houseKeeperEmail;

    private List<List<String>> prefects;

    private int population;

    private List<Integer> activeYears;

    /**
     * Adds a year to the list of active years,
     * @param year - the year to be added
     */
    public void addActiveYear(int year) {
        this.activeYears.add(year);
    }
}
