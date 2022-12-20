package com.maat.model;

import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Representation of possible filters for student object
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class StudentFilters implements Serializable {
    private static final long serialVersionUID = 2460931844210422325L;

    @Builder.Default
    private List<String> gender = new ArrayList<>();
    @Builder.Default
    private List<String> program = new ArrayList<>();
    @Builder.Default
    private List<String> house = new ArrayList<>();
    @Builder.Default
    private List<Integer> year = new ArrayList<>();
    @Builder.Default
    private List<Integer> experience = new ArrayList<>();
    @Builder.Default
    private List<String> doGroup = new ArrayList<>();
    @Builder.Default
    private List<String> status = new ArrayList<>();

    /**
     * Start of the date range of lastModified column
     */
    @Builder.Default
    private Date startDate = null;

    /**
     * End of the date range of lastModified column
     */
    @Builder.Default
    private Date endDate = null;
    @Builder.Default
    private String searchString = null;

}
