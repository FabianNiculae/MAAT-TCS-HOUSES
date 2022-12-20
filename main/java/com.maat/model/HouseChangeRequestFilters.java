package com.maat.model;

import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Representation of possible filters for HouseChangeRequest object
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HouseChangeRequestFilters implements Serializable {

    private static final long serialVersionUID = 6708092730020136612L;

    /** Only target house fits the filter */
    @Builder.Default
    private List<String> targetHouse = new ArrayList<>();

    /** Only old house fits the filter */
    @Builder.Default
    private List<String> oldHouse = new ArrayList<>();

    /** Either target or old house fits the filter */
    @Builder.Default
    private List<String> eitherHouse = new ArrayList<>();
    @Builder.Default
    private List<String> status = new ArrayList<>();
    @Builder.Default
    private Date startDate = null;
    @Builder.Default
    private Date endDate = null;
    @Builder.Default
    private String searchString = null;
}
