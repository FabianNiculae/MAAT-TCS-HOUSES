package com.maat.model;

import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HousesCupPointsFilters implements Serializable {

    private static final long serialVersionUID = -4882222639556115374L;

    @Builder.Default
    private List<String> house = new ArrayList<>();

    /**
     * Lower boundary of point range
     */
    @Builder.Default
    private Integer startPoints = null;

    /**
     * Upper boundary of point range
     */
    @Builder.Default
    private Integer endPoints = null;

    /**
     * Lower boundary of date range
     */
    @Builder.Default
    private Date startDate = null;

    /**
     * Upper boundary of date range
     */
    @Builder.Default
    private Date endDate = null;

    @Builder.Default
    private List<Integer> academicYear = new ArrayList<>();

    @Builder.Default
    private String searchString = null;


}
