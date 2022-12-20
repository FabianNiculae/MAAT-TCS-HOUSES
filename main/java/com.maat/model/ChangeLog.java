package com.maat.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;
import java.text.*;

/**
 * POJO class to represent a change made to the Maat database to be used to look up changes applied since user last viewed
 * the data throught the web application.
 * @author Brand Hauser
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="change_logs")
public class ChangeLog implements Serializable {

    private static final long serialVersionUID = 6973826297743277088L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private int id;

    @Column(name="date")
    private String date;

    @Column(name="id_changed")
    private int idChanged;

    /**
     * Converts a String representation of a date to the corresponding Date object.
     * @param args - the String to be converted
     * @return the corresponding Date object
     * @throws ParseException - if the string cannot be parsed appropriately
     */
    public Date getDateObject(String[] args) throws ParseException {
        SimpleDateFormat ft = new SimpleDateFormat ("yyyy/MM/dd");
        String input = args.length == 0 ? "1818-11-11" : args[0];
        Date date;
        date = ft.parse(input);
        return date;
    }

    /**
     * Converts a Date object to the appropriate string representation as required by the Maat database.
     * @param date - the Date object to be converted
     */
    public void setDateFromObject(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat ("yyyy/MM/dd");
        this.date = formatter.format(date);
    }
}
