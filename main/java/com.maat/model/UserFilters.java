package com.maat.model;

import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
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
public class UserFilters implements Serializable {
    private static final long serialVersionUID = 2460931844210422325L;

    @Builder.Default
    private List<String> house = new ArrayList<>();
    @Builder.Default
    private List<String> roles = new ArrayList<>(Arrays.asList(
            "ROLE_ADMIN","ROLE_TEACHER","ROLE_STUDY_ADVISOR","ROLE_TA","ROLE_HOUSE_KEEPER","ROLE_PREFECT"));
    @Builder.Default
    private Date startDate = null;
    @Builder.Default
    private Date endDate = null;
    @Builder.Default
    private String searchString = null;

}
