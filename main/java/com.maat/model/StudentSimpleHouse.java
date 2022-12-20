package com.maat.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * POJO class to represent a student from the Maat database as an object without links to other objects.
 * @author Brand Hauser
 */
@NoArgsConstructor
@Getter
@Setter
public class StudentSimpleHouse implements Serializable {

    private static final long serialVersionUID = 6399599602401824474L;

    protected int idNumber;

    private String firstName;

    private String lastName;

    private String email;

    private boolean enabled;

    private List<String> roles;

    private String gender;

    private String nationality;

    private String program;

    private HouseSimple house;

    private Integer cohort;

    private Integer experience;

    private String doGroup;

    private String status;
}
