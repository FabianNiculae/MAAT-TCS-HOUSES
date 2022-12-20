package com.maat.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class HouseChangeRequestsEnabler implements Serializable {

    private static final long serialVersionUID = 6738232800113771948L;
    @Id
    private int id;

    @Column(name = "enabled")
    private boolean enabled;
}
