package com.maat.model;

import com.maat.exception.InvalidDateException;
import com.maat.exception.InvalidStatusException;
import com.maat.helper.ValidityChecker;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Date;

/**
 * POJO class to represent a House Change Request from the Maat database as an object.
 * @author Brand Hauser
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@NamedEntityGraph(name = "graph.ChangeRequest.simple",
        attributeNodes = {
            @NamedAttributeNode(value = "student", subgraph = "student-sub"),
            @NamedAttributeNode(value = "targetHouse", subgraph = "targetHouse-sub"),
            @NamedAttributeNode(value = "oldHouse", subgraph = "oldHouse-sub")
        },
        subgraphs = {
                @NamedSubgraph(name = "student-sub",
                        attributeNodes = {
                                @NamedAttributeNode(value = "firstName"),
                                @NamedAttributeNode(value = "lastName")
                        }),
                @NamedSubgraph(name = "targetHouse-sub",
                        attributeNodes = {@NamedAttributeNode(value = "name")}
                ),
                @NamedSubgraph(name = "oldHouse-sub",
                        attributeNodes = {@NamedAttributeNode(value = "name")}
                )
        }
)
@Table(name="house_change_requests")
public class HouseChangeRequest implements Serializable {

    private static final long serialVersionUID = 6708092730020136612L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(targetEntity = Student.class)
    @JoinColumn(name="student", nullable = false)
    private Student student;

    @ManyToOne(targetEntity = House.class)
    @JoinColumn(name="target_house")
    private House targetHouse;

    @ManyToOne(targetEntity = House.class)
    @JoinColumn(name="old_house")
    private House oldHouse;

    @Column(name="explanation", columnDefinition = "TEXT")
    private String explanation;

    @Column(name="request_date")
    private Date requestDate;

    @Column(name="status")
    private String status;

    @Column(name="denial_explanation", columnDefinition = "TEXT")
    private String denialExplanation;

    @Column(name="decided_date")
    private Date decidedDate;

    /**
     * Checks to see if the data entered into a house change request object is valid.
     * @throws InvalidStatusException -
     * @throws InvalidDateException -
     */
    public void validateHouseChangeRequestData() throws InvalidStatusException,
            InvalidDateException {
        String message;
        if (this.explanation != null) {
            this.explanation = this.explanation.replaceAll("^[\r\n]", " ");
        }
        if (!ValidityChecker.hcrStatusIsValid(status)) {
            message = "Status {0} is not valid";
            throw new InvalidStatusException(MessageFormat.format(message, this.status));
        }
        if (this.denialExplanation != null) {
            this.denialExplanation = this.denialExplanation.replaceAll("^[\r\n;]", " ");
        }
        if (!ValidityChecker.dateIsValid(this.requestDate)) {
            message = "Date {0} is not valid.  Check that it is in the proper format yyyy-MM-dd";
            throw new InvalidDateException(MessageFormat.format(message, this.requestDate));
        }
        if (!ValidityChecker.dateIsValid(this.decidedDate)) {
            message = "Date {0} is not valid.  Check that it is in the proper format yyyy-MM-dd";
            throw new InvalidDateException(MessageFormat.format(message, this.decidedDate));
        }
    }

    /**
     * Converts a HouseCHangeRequest object into a
     * {@link com.maat.model.HouseChangeRequestSimple HouseCHangeRequestSimple} object so that it can be displayed
     * simply as text.
     * @param student - the student who made the request
     * @return the HouseChangeRequestSimple object
     */
    public HouseChangeRequestSimple createSimpleVersion(Student student) {
        HouseChangeRequestSimple simpleHcr = new HouseChangeRequestSimple();
        if (student != null) {
            simpleHcr.setStudentName(student.getFirstName() + " " + student.getLastName());
            simpleHcr.setStudent(student.getIdNumber());
        }
        if (this.targetHouse != null) {
            simpleHcr.setTargetHouse(this.targetHouse.getName());
        }
        if (this.oldHouse != null) {
            simpleHcr.setOldHouse(this.oldHouse.getName());
        }
        simpleHcr.setExplanation(this.explanation);
        simpleHcr.setRequestDate(this.requestDate);
        simpleHcr.setStatus(this.status);
        simpleHcr.setDenialExplanation(this.denialExplanation);
        simpleHcr.setDecidedDate(this.decidedDate);
        return simpleHcr;
    }

    public HouseChangeRequest copy(HouseChangeRequest houseChangeRequest, Student student) {
        this.student = student;
        this.targetHouse = houseChangeRequest.getTargetHouse();
        this.oldHouse = houseChangeRequest.getOldHouse();
        this.explanation = houseChangeRequest.getExplanation();
        this.requestDate = houseChangeRequest.getRequestDate();
        if (!houseChangeRequest.getStatus().equals("UNDECIDED") && this.status.equals("UNDECIDED")) {
            this.decidedDate = new Date();
        }
        this.status = houseChangeRequest.getStatus();
        this.denialExplanation = houseChangeRequest.getDenialExplanation();
        return this;
    }
}
