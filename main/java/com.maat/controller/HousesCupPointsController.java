package com.maat.controller;

import com.maat.helper.RecordConverter;
import com.maat.message.ResponseMessage;
import com.maat.model.*;
import com.maat.service.HousesCupPointsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Class for CRUD operations on Houses Cup entries within the Maat database.
 * @author Brand Hauser
 */
@CrossOrigin("http://localhost:8080")
@RestController
@RequestMapping("/api/housesCup")
public class HousesCupPointsController {

    @Autowired
    HousesCupPointsService fileService;

    /**
     * Http request to add an entry to the houses cup points table.
     * @param housesCupPoints - the entry to be added
     * @return the added entry
     */
    @PostMapping()
    public ResponseEntity<?> addCupPoints(@RequestBody HousesCupPoints housesCupPoints) {
        try {
            HousesCupPointsSimple resultPointsEntry = fileService.save(housesCupPoints);
            return new ResponseEntity<>(resultPointsEntry, HttpStatus.CREATED);
        } catch (Exception e) {
            String message = "Could not add Houses Cup entry: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
        }
    }

    /**
     * Http request to find all entries into the houses cup points data table.
     * @return a list of all entries in the houses cup points table
     */
    @GetMapping()
    public ResponseEntity<?> findAllCupPointsEntries(@RequestParam(required = false, name="pageNum", defaultValue="0") Integer pageNum,
                                                     @RequestParam(required = false, name="pageSize", defaultValue="-1") Integer pageSize,
                                                     @RequestParam(required = false, name="sortOn", defaultValue="date") String sortOn,
                                                     @RequestParam(required = false, name="asc", defaultValue="true") Boolean asc) {
        try {
            Pageable pageable = fileService.createPageable(pageNum, pageSize, sortOn, asc);
            Map<String, Object> cupPointsList = fileService.getAllHousesCupPoints(pageable);
            if ((Boolean) cupPointsList.get("empty")) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            List<Object> resultList = new java.util.ArrayList<>(Collections.singletonList(cupPointsList));
            resultList.add(fileService.getFilterables());
            return new ResponseEntity<>(resultList, HttpStatus.OK);
        } catch (Exception e) {
            String message = "Failed to retrieve data: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
        }
    }

    /**
     * Http Delete request to update a houses cup points entry from the Maat database.
     * @param housesCupPoints - the entry to be updates
     * @return the updated houses cup points entry
     */
    @PutMapping()
    public ResponseEntity<?> updateCupPointsEntry(@RequestBody HousesCupPoints housesCupPoints) {
        try {
            HousesCupPointsSimple resultHousesCupEntry = fileService.updateHousesCupPoints(housesCupPoints);
            return new ResponseEntity<>(resultHousesCupEntry, HttpStatus.OK);
        } catch (Exception e) {
            String message = "Could not update Houses Cup entry: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
        }
    }

    /**
     * Http Delete request to delete a houses cup points entry from the Maat database.
     * @param housesCupPoints - the entry to be deleted
     * @return message declaring the success or failure of the delete
     */
    @DeleteMapping()
    public ResponseEntity<ResponseMessage> deleteHousesCupPointsEntry(@RequestBody HousesCupPoints housesCupPoints) {
        String message;
        try {
            message = fileService.deleteHousesCupPoints(housesCupPoints);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not delete Houses Cup entry: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
        }
    }

    /**
     * Retrieves the unique value from the appropriate columns within the database in order
     * to allow filtering based on these values.
     * @return the list of unique data points per column
     */
    @GetMapping("/filters")
    public ResponseEntity<?> getFilterables() {
        try {
            return new ResponseEntity<>(fileService.getFilterables(), HttpStatus.OK);
        } catch (Exception e) {
            String message = "Could not retrieve filterables: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
        }
    }

    /**
     * Http Post method to get a list of {@link com.maat.model.HousesCupPoints HousesCupPoints} filtered on the options provided in the request body.
     * @param filters the {@link com.maat.model.HousesCupPointsFilters HousesCupPointsFilters} object representation of the filters to be used
     * @return http response containing list of all filtered {@link com.maat.model.HousesCupPoints HousesCupPoints} objects from the database
     */
    @PostMapping("/filter")
    public ResponseEntity<?> findFilteredHCPs(@RequestBody HousesCupPointsFilters filters,
                                              @RequestParam(required = false, name="pageNum", defaultValue="0") Integer pageNum,
                                              @RequestParam(required = false, name="pageSize", defaultValue="-1") Integer pageSize,
                                              @RequestParam(required = false, name="sortOn", defaultValue="date") String sortOn,
                                              @RequestParam(required = false, name="asc", defaultValue="true") Boolean asc) {
        try {
            Pageable pageable = fileService.createPageable(pageNum, pageSize, sortOn, asc);
            Map<String, Object> hcps = fileService.getFilteredHousesCupPoints(filters, pageable);
            return new ResponseEntity<>(hcps, HttpStatus.OK);
        } catch (Exception e) {
            String message = e.getMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
        }
    }

    /**
     * Http request to find the sum of points grouped by house.
     * @param year the academic year of for the request, current academic year by default
     * @return a list of houses and their corresponding number of points
     */
    @GetMapping("/points")
    public ResponseEntity<?> getPointsByHouse(@RequestParam(required = false, name = "year") Integer year) {
        try {
            if (year == null) {
                year = RecordConverter.getAcademicYear(new Date());
            }
            return new ResponseEntity<>(fileService.getPointsByHouse(year), HttpStatus.OK);
        } catch (Exception e) {
            String message = "Could not retrieve points per house grouped by year " + year + ": " + e.getMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
        }
    }

    /**
     * Http request to find the sum of points grouped by student for a given house.
     * @param house the house for which the data is requested, required
     * @param year the academic year of for the request, current academic year by default
     * @return a list of houses and their corresponding number of points
     */
    @GetMapping("/inHouseComp")
    public ResponseEntity<?> getPointsPerStudent(@RequestParam(required = false, name = "year") Integer year,
                                                @RequestParam(required = true, name = "house") String house) {
        try {
            if (year == null) {
                year = RecordConverter.getAcademicYear(new Date());
            }
            return new ResponseEntity<>(fileService.getPointsPerStudent(year, house), HttpStatus.OK);
        } catch (Exception e) {
            String message = "Could not retrieve points per house grouped by year " + year + ": " + e.getMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
        }
    }
}
