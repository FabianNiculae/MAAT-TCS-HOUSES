package com.maat.model;

import com.maat.exception.InvalidEmailException;
import com.maat.exception.InvalidIdNumberException;
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
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * POJO class to represent an User from the Maat database as an object.
 * @author Brand Hauser
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@NamedEntityGraphs({
        @NamedEntityGraph(name = "graph.Users.house.id",
                attributeNodes = {@NamedAttributeNode(value = "house", subgraph = "house-sub")},
                subgraphs = {
                        @NamedSubgraph(name = "house-sub", attributeNodes = {@NamedAttributeNode(value = "name")})
                }
        )
})
@Table(name="users")
public class User implements Serializable {

    private static final long serialVersionUID = 7057642896417621252L;

    public User(int idNum, String firstName, String lastName, boolean enabled) {
        this.idNumber = idNum;
        this.firstName = firstName;
        this.lastName = lastName;
        this.enabled = enabled;
        this.roles = new ArrayList<>();
    }

    public User(int idNumber) {
        this.idNumber = idNumber;
    }

    @Id
    @Column(name="id_number")
    Integer idNumber;

    @Column(name="first_name")
    String firstName;

    @Column(name = "link_name")
    String linkName;

    @Column(name="last_name")
    String lastName;

    @Column(name="email")
    String email;

    @Column(name = "enabled")
    boolean enabled;

    @Column(name="date_assigned_to_house")
    Date dateAssignedToHouse;

    @ManyToOne(targetEntity = House.class)
    @JoinColumn(name="house")
    private House house;

    @ManyToMany
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id_number"),
            inverseJoinColumns = @JoinColumn(name = "role_name", referencedColumnName = "role"))
    private Collection<Role> roles;

    /**
     * Checks to see if the data entered into a staff object is valid.
     * @throws InvalidIdNumberException -
     * @throws InvalidEmailException -
     * @throws InvalidNameException -
     */
    public void validateStaffData() throws InvalidIdNumberException, InvalidEmailException, InvalidNameException {
        String message;
        if (!ValidityChecker.idNumIsValid(this.idNumber)) {
            message = "User Number {0} is not valid";
            throw new InvalidIdNumberException(MessageFormat.format(message, this.idNumber));
        }
        if (!ValidityChecker.teacherEmailIsValid(this.email)) {
            message = "Email {0} for staff {1} is not a valid staff email.";
            throw new InvalidEmailException(MessageFormat.format(message, this.email, this.idNumber));
        }
        if (!ValidityChecker.nameIsValid(this.firstName)) {
            message = "First name {0} for staff {1} is not a valid name.";
            throw new InvalidNameException(MessageFormat.format(message, this.firstName, this.idNumber));
        }
        if (!ValidityChecker.nameIsValid(this.linkName)) {
            message = "First name {0} for staff {1} is not a valid name.";
            throw new InvalidNameException(MessageFormat.format(message, this.firstName, this.idNumber));
        }
        if (!ValidityChecker.nameIsValid(this.lastName)) {
            message = "Last name {0} for staff {1} is not a valid name.";
            throw new InvalidNameException(MessageFormat.format(message, this.lastName, this.idNumber));
        }
    }

    /**
     * Adds a role to the list of roles.
     * @param role - the role to be added
     */
    public void addRole(Role role) {
        this.roles.add(role);
    }


    @Override
    public String toString() {
        return this.firstName + " " + this.lastName;
    }

    /**
     * Converts a User object to a {@link com.maat.model.UserSimple UserSimple} object so that it
     * can be displayed simply as text.
     * @return the UserSimple object
     */
    public UserSimple createSimpleCopy() {
        UserSimple simpleCopy = new UserSimple();
        simpleCopy.setIdNumber(this.idNumber);
        simpleCopy.setFirstName(this.firstName);
        simpleCopy.setLastName(this.lastName);
        simpleCopy.setEmail(this.email);
        simpleCopy.setEnabled(this.enabled);
        simpleCopy.setDateAssignedToHouse(this.dateAssignedToHouse);
        List<String> roleList = new ArrayList<>();
        if (this.getRoles() != null) {
            for (Role role : this.getRoles()) {
                roleList.add(role.getRole());
            }
        }
        simpleCopy.setRoles(roleList);
        if (this.getHouse() != null) {
            simpleCopy.setHouse(this.getHouse().getName());
        }
        return simpleCopy;
    }
}
