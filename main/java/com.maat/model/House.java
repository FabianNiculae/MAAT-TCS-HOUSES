package com.maat.model;

import com.maat.exception.InvalidNameException;
import com.maat.helper.ValidityChecker;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * POJO class to represent a House from the Maat database as an object.
 * @author Brand Hauser
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="houses")
public class House implements Serializable {

    private static final long serialVersionUID = -4660005367999809929L;

    public House(String name) {
        this.name = name;
    }

    @Id
    @Column(name="name")
    private String name;

    @OneToOne
    @JoinColumn(name="housekeeper", referencedColumnName = "id_number")
    private User houseKeeper;

    @OneToMany
    @JoinTable(name="House_Prefects",
                joinColumns = @JoinColumn(name="name"),
                inverseJoinColumns = @JoinColumn(name = "id_number"))
    private Collection<User> prefects;

    @Column(name="population")
    private Integer population;

    @ElementCollection
    @CollectionTable(name = "house_years", joinColumns = @JoinColumn(name = "house"))
    @Column(name = "active_years")
    private List<Integer> activeYears;

    @Override
    public String toString() {
        return this.name;
    }

    /**
     * Checks to see if the data entered into a house object is valid.
     * @throws InvalidNameException -
     */
    public void validateHouseData() throws InvalidNameException {
        String message;
        if (!ValidityChecker.nameIsValid(this.name)) {
            message = "Name {0} for house is not valid.";
            throw new InvalidNameException(MessageFormat.format(message, this.name));
        }
    }

    /**
     * Converts a House object to a {@link com.maat.model.HouseSimple HouseSimple} object
     * so that its data can be displayed simply.
     * @return the HouseSimple object
     */
    public HouseSimple createHouseSimple() {
        HouseSimple simpleHouse = new HouseSimple();
        String name;
        simpleHouse.setName(this.name);
        if (this.houseKeeper != null) {
            name = this.houseKeeper.getFirstName() + " " + this.houseKeeper.getLastName();
            simpleHouse.setHouseKeeperName(name);
            simpleHouse.setHouseKeeperIdNumber(this.houseKeeper.getIdNumber());
            simpleHouse.setHouseKeeperEmail(this.houseKeeper.getEmail());
        }
        if (this.prefects != null) {
            List<List<String>> prefectList = new ArrayList<>();
            for (User prefect : this.prefects) {
                name = prefect.getFirstName() + " " + prefect.getLastName();
                String idNumber = String.valueOf(prefect.getIdNumber());
                List<String> list = Arrays.asList(idNumber, name, prefect.getEmail());
                prefectList.add(list);
            }
            simpleHouse.setPrefects(prefectList);
        }
        if (population != null) {
            simpleHouse.setPopulation(this.population);
        }
        simpleHouse.setActiveYears(new ArrayList<>());
        if (this.activeYears != null && !this.activeYears.isEmpty()) {
            for (Integer year : activeYears) {
                simpleHouse.addActiveYear(year);
            }
        }
        return simpleHouse;
    }

    /**
     * Copies the attributes of a house to this house.
     * @param house - the house to copy
     * @return this
     */
    public House update(House house) {
        this.houseKeeper = house.getHouseKeeper();
        this.prefects = house.getPrefects();
        this.activeYears = house.getActiveYears();
        return this;
    }
}
