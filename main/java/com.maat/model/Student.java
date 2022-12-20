package com.maat.model;


import com.maat.exception.InvalidEmailException;
import com.maat.exception.InvalidIdNumberException;
import com.maat.exception.InvalidNameException;
import com.maat.helper.RecordConverter;
import com.maat.helper.ValidityChecker;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * POJO class to represent a student from the Maat database as an object.
 * @author Brand Hauser
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
@DynamicInsert
@Entity
@NamedEntityGraphs({
        @NamedEntityGraph(name = "graph.Student.house.id",
                attributeNodes = {@NamedAttributeNode(value = "house", subgraph = "house-sub")},
                subgraphs = {
                        @NamedSubgraph(name = "house-sub", attributeNodes = {@NamedAttributeNode(value = "name")})
                }
        ),
        @NamedEntityGraph(name = "graph.Students.all",
                attributeNodes = {@NamedAttributeNode(value = "house", subgraph = "houseAll-sub")},
                subgraphs = {
                        @NamedSubgraph(name = "houseAll-sub", attributeNodes = {
                                @NamedAttributeNode(value = "name"),
                                @NamedAttributeNode(value = "houseKeeper", subgraph = "keeper-sub"),
                                @NamedAttributeNode(value = "prefects", subgraph = "prefect-sub")}),
                        @NamedSubgraph(name = "keeper-sub", attributeNodes = {
                                @NamedAttributeNode(value = "firstName"),
                                @NamedAttributeNode(value = "lastName")
                        }),
                        @NamedSubgraph(name = "prefect-sub", attributeNodes = {
                                @NamedAttributeNode(value = "firstName"),
                                @NamedAttributeNode(value = "lastName")
                        })
                }
        )
})
@Table(name="students")
public class Student extends User implements Serializable {

    private static final long serialVersionUID = 3452840465978739243L;

    @Column(name="gender")
    private String gender;

    @Column(name = "birthday")
    private Date dob;

    @Column(name="nationality")
    private String nationality;

    @Column(name="program")
    private String program;

    @Column(name="cohort")
    private Integer cohort;

    @Column(name = "house_cohort")
    private Integer houseCohort;

    @Column(name="experience")
    private Integer experience;

    @Column(name="do_group")
    private String doGroup;

    @Column(name="status")
    private String status;

    public Student(Integer idNumber) {
        this.idNumber = idNumber;
    }


    /**
     * Checks to see if the data entered into a student object is valid.
     * @throws InvalidIdNumberException -
     * @throws InvalidEmailException -
     * @throws InvalidNameException -
     */
    public void validateStudentData() throws InvalidIdNumberException, InvalidEmailException, InvalidNameException {
        if (!ValidityChecker.idNumIsValid(this.idNumber)) {
            String message = "Student number {0} is not valid";
            throw new InvalidIdNumberException(MessageFormat.format(message, Integer.toString(this.idNumber)));
        }
        if (!ValidityChecker.studentEmailIsValid(this.email)) {
            String message = "Email {0} for student {1} is not a valid student email.";
            throw new InvalidEmailException(MessageFormat.format(message, this.email, Integer.toString(this.idNumber)));
        }
        if (!ValidityChecker.nameIsValid(this.firstName)) {
            String message = "First name {0} for student {1} is not a valid name.";
            throw new InvalidNameException(MessageFormat.format(message, this.firstName, Integer.toString(this.idNumber)));
        }
        if (!ValidityChecker.nameIsValid(this.linkName)) {
            String message = "First name {0} for student {1} is not a valid name.";
            throw new InvalidNameException(MessageFormat.format(message, this.firstName, Integer.toString(this.idNumber)));
        }
        if (!ValidityChecker.nameIsValid(this.lastName)) {
            String message = "Last name {0} for student {1} is not a valid name.";
            throw new InvalidNameException(MessageFormat.format(message, this.lastName, Integer.toString(this.idNumber)));
        }
    }

    /**
     * Converts a Student object into a {@link com.maat.model.StudentSimple StudentSimple} object so that
     * it can be displayed simply as text.
     * @return the StudentSimple object
     */
    public StudentSimple createStudentSimpleCopy() {
        StudentSimple simpleCopy = new StudentSimple();
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
        simpleCopy.setGender(this.gender);
        RecordConverter.getNationalityFromRecord(this.nationality);
        simpleCopy.setProgram(this.program);
        simpleCopy.setCohort(this.cohort);
        simpleCopy.setExperience(this.experience);
        simpleCopy.setDoGroup(this.doGroup);
        simpleCopy.setStatus(this.status);
        if (this.getHouse() != null) {
            simpleCopy.setHouse(this.getHouse().getName());
        }
        return simpleCopy;
    }

    /**
     * Converts a Student object into a {@link com.maat.model.StudentSimpleHouse StudentSimpleHouse} object so that
     * it can be displayed simply as text.
     * @return the StudentSimpleHouse object
     */
    public StudentSimpleHouse createSimpleHouseCopy() {
        StudentSimpleHouse studentSimpleHouse = new StudentSimpleHouse();
        studentSimpleHouse.setIdNumber(this.idNumber);
        studentSimpleHouse.setFirstName(this.firstName);
        studentSimpleHouse.setLastName(this.lastName);
        studentSimpleHouse.setEmail(this.email);
        studentSimpleHouse.setEnabled(this.enabled);
        List<String> roleList = new ArrayList<>();
        if (this.getRoles() != null) {
            for (Role role : this.getRoles()) {
                roleList.add(role.getRole());
            }
        }
        studentSimpleHouse.setRoles(roleList);
        studentSimpleHouse.setGender(this.gender);
        studentSimpleHouse.setNationality(this.nationality);
        studentSimpleHouse.setProgram(this.program);
        studentSimpleHouse.setCohort(this.cohort);
        studentSimpleHouse.setExperience(this.experience);
        studentSimpleHouse.setDoGroup(this.doGroup);
        studentSimpleHouse.setStatus(this.status);
        if (this.getHouse() != null) {
            HouseSimple simpleHouse = this.getHouse().createHouseSimple();
            studentSimpleHouse.setHouse(simpleHouse);
        }
        return studentSimpleHouse;
    }

    /**
     * Evaluates the data of the Student object and assigns the appropriate status based on the present/missing data.
     */
    public void evaluateStatus() {
        if (this.getHouse() != null) {
            this.status = "INHOUSE";
        } else if (this.doGroup != null) {
            this.status = "READY FOR HOUSE";
        } else if (this.email == null && (this.gender == null || this.nationality == null)) {
            this.status = "MISSING INFORMATION";
        } else if (this.email == null) {
            this.status = "MISSING CANVAS INFO";
        } else if (this.gender == null || this.nationality == null) {
            this.status = "MISSING OSIRIS INFO";
        } else if (this.firstName == null || this.lastName == null || this.program == null ||
                this.cohort == null || this.experience == null) {
            this.status = "MISSING INFORMATION";
        }
    }

    public void setIdNumber(Integer idNumber) {
        this.idNumber = idNumber;
    }

    public List<String> getRoleList() {
        List<String> roleList = new ArrayList<>();
        Collection<Role> roles = getRoles();
        if (roles != null) {
            for (Role role : roles) {
                roleList.add(role.getRole());
            }
        }
        return roleList;
    }

    @Override
    public String toString() {
        return this.firstName + " " + this.lastName;
    }
}
