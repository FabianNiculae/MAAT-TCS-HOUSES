package com.maat.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

/**
 * POJO class to represent the user privilege entity of the Maat database.
 * @author Brand Hauser
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Privilege implements Serializable {

    private static final long serialVersionUID = -8311366465369152546L;

    public Privilege(String privilege) {
        this.privilege = privilege;
    }

    @Id
    @Column(name = "Privilege")
    private String privilege;

    @ManyToMany(mappedBy = "privileges")
    @JsonIgnore
    private Collection<Role> roles;
}
