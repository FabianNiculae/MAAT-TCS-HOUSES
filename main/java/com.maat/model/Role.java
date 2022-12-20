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
 * POJO class to represent the user role entity of the Maat database.
 * @author Brand Hauser
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Role implements Serializable {

    private static final long serialVersionUID = -6723237761092603694L;

    public Role(String role) {
        this.role = role;
    }
    public Role(String role, Collection<Privilege> privileges) {
        this.role = role;
        this.privileges = privileges;
    }

    @Id
    @Column(name = "role")
    private String role;

    @ManyToMany(mappedBy = "roles")
    @JsonIgnore
    private Collection<User> users;

    @ManyToMany
    @JoinTable(
            name = "privileges",
            joinColumns = @JoinColumn(name = "role_name", referencedColumnName = "role"),
            inverseJoinColumns = @JoinColumn(name = "privilege_name", referencedColumnName = "privilege")
            )
    private Collection<Privilege> privileges;

    public void updateAttributes(Role role) {
        this.role = role.getRole();
        this.users = role.getUsers();
        this.privileges = role.getPrivileges();
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && obj.getClass() == Role.class && ((Role) obj).getRole().equals(this.role);
    }
}
