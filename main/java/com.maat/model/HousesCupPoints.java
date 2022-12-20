package com.maat.model;

import com.maat.exception.InvalidDateException;
import com.maat.helper.ValidityChecker;
import com.maat.helper.RecordConverter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.text.MessageFormat;
import java.text.ParseException;
import java.util.Date;


/**\
 * POJO class to represent an entry into the Houses Cup Points data table.
 * @author Brand Hauser
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "houses_cup")
@NamedEntityGraph(name = "graph.HousesCup.all",
        attributeNodes = {@NamedAttributeNode(value = "house", subgraph = "houseAll-sub"),
                @NamedAttributeNode(value = "student", subgraph = "student-sub"),
                @NamedAttributeNode(value = "assigningUser", subgraph = "user-sub")},
        subgraphs = {
                @NamedSubgraph(name = "houseAll-sub", attributeNodes = {
                        @NamedAttributeNode(value = "name")
                }),
                @NamedSubgraph(name = "student-sub", attributeNodes = {
                        @NamedAttributeNode(value = "firstName"),
                        @NamedAttributeNode(value = "lastName")
                }),
                @NamedSubgraph(name = "user-sub", attributeNodes = {
                        @NamedAttributeNode(value = "firstName"),
                        @NamedAttributeNode(value = "lastName")
                })
        }
)
public class HousesCupPoints implements Serializable {

    private static final long serialVersionUID = -4882222639556115374L;

    @OneToOne
    @JoinColumn(name = "student", referencedColumnName = "id_number")
    private Student student;

    @OneToOne
    @JoinColumn(name = "assigning_user", referencedColumnName = "id_number")
    private User assigningUser;

    @Column(name = "explanation", columnDefinition = "TEXT")
    private String explanation;

    @OneToOne
    @JoinColumn(name = "house", referencedColumnName = "name")
    private House house;

    @Column(name = "points")
    private int points;

    @Id
    @Column(name = "date")
    private Date date;

    @Column(name = "academic_year")
    private int academicYear;

    public void setDate(Date date) throws ParseException {
        this.date = date;
        this.academicYear = RecordConverter.getAcademicYear(date);
    }

    /**
     * Converts a HousesCupPoints object into a {@link com.maat.model.HousesCupPointsSimple HousesCupPointsSimple}
     * object so that it can be displayed simply as text.
     * @return the HousesCupPoints Simple object
     */
    public HousesCupPointsSimple createSimpleCopy() {
        HousesCupPointsSimple simpleCopy = new HousesCupPointsSimple();
        simpleCopy.setStudent(this.student.getFirstName() + " " + this.student.getLastName());
        simpleCopy.setAssigningUser(this.assigningUser.getFirstName() + " " + this.assigningUser.getLastName());
        simpleCopy.setExplanation(this.explanation);
        simpleCopy.setHouse(this.house.getName());
        simpleCopy.setPoints(this.points);
        simpleCopy.setDate(this.date);
        simpleCopy.setAcademicYear(this.academicYear);
        return simpleCopy;
    }

    /**
     * Checks the data of the housesCupPoints object to ensure it is valid.
     * @throws InvalidDateException -
     */
    public void validateHousesCupPointsData() throws InvalidDateException {
        if (this.explanation != null) {
            this.explanation = this.explanation.replaceAll("^[\r\n]", " ");
        }
        if (!ValidityChecker.dateIsValid(this.date)) {
            String message = "Date {0} is not valid.  Check that it is in the proper format yyyy-MM-dd";
            throw new InvalidDateException(MessageFormat.format(message, this.date));
        }
    }

    /**
     * Copies the attributes other than serialUID from one HousesCupPoints object to this one.
     * @param housesCupPoints - the object to copy
     */
    public void copy(HousesCupPoints housesCupPoints) {
        this.student = housesCupPoints.getStudent();
        this.assigningUser = housesCupPoints.getAssigningUser();
        this.date = housesCupPoints.getDate();
        this.explanation = housesCupPoints.getExplanation();
        this.house = housesCupPoints.getHouse();
        this.points = housesCupPoints.getPoints();
        this.academicYear = housesCupPoints.getAcademicYear();
    }
}
