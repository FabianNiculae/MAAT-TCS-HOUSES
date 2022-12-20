package com.maat.controller;

import com.maat.message.ResponseMessage;
import com.maat.model.HouseChangeRequest;
import com.maat.model.HouseChangeRequestSimple;
import com.maat.model.HouseChangeRequestFilters;
import com.maat.model.HouseChangeRequestsEnabler;
import com.maat.repository.HouseChangeRequestEnablerRepository;
import com.maat.service.HouseChangeRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Class for CRUD operations on HouseChangeRequest entries within the Maat database.
 * @author Brand Hauser
 */
@CrossOrigin("http://localhost:8080")
@RestController
@RequestMapping("/api/houseChangeRequests")
public class HouseChangeRequestController {


    @Autowired
    HouseChangeRequestService fileService;

    /**
     * Http Post request to add a house change request member to the Maat database.
     * @param houseChangeRequest - the {@link HouseChangeRequest HouseChangeRequest} object
     *                           representation of the house change request converted from JSON
     * @return the http response - if successfull response includes JSON object
     */
    @PostMapping()
    public ResponseEntity<?> addHouseChangeRequest(@RequestBody HouseChangeRequest houseChangeRequest) {
        try {
            HouseChangeRequestSimple resultHCR = fileService.save(houseChangeRequest);
            return new ResponseEntity<>(resultHCR, HttpStatus.CREATED);
        } catch (Exception e) {
            String message = "Could not add request: " + e.getMessage();
            System.out.println("\u001B[33mERROR: " + e + "\u001B[37m");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
        }
    }

    /**
     * Http Get request to retrieve a list of all house change requests from the Maat database.
     * @return http response containing the list of {@link HouseChangeRequest HouseChangeRequest}
     * objects representing the
     * requests to be converted to JSON
     */
    @GetMapping()
    public ResponseEntity<?> findAllHouseChangeRequests(@RequestParam(required = false, name = "studentId") Integer studentId,
                                                        @RequestParam(required = false, name="pageNum", defaultValue="0") Integer pageNum,
                                                        @RequestParam(required = false, name="pageSize", defaultValue="-1") Integer pageSize,
                                                        @RequestParam(required = false, name="sortOn", defaultValue="decidedDate") String sortOn,
                                                        @RequestParam(required = false, name="asc", defaultValue="true") Boolean asc) {
        try {
            if (studentId == null) {
                Pageable pageable = fileService.createPageable(pageNum, pageSize, sortOn, asc);
                Map<String, Object> resultHCR = fileService.getAllHouseChangeRequests(pageable);
                if ((Boolean) resultHCR.get("empty")) {
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                } else {
                    List<Object> resultList = new java.util.ArrayList<>(Collections.singletonList(resultHCR));
                    resultList.add( fileService.getFilterables());
                    return new ResponseEntity<>(resultList, HttpStatus.OK);
                }
            } else {
                List<HouseChangeRequestSimple> resultHCR = fileService.getHouseChangeRequestsByStudent(studentId);
                if (resultHCR.isEmpty()) {
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                }
                return new ResponseEntity<>(resultHCR, HttpStatus.OK);
            }
        } catch (Exception e) {
            String message = "Failed to retrieve data: " + e.getMessage();
            System.out.println("\u001B[33mERROR: " + e + "\u001B[37m");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
        }
    }

    /**
     * Http Get request to retrieve all house change requests of a specified student.
     * @param id - the student number of the specified student
     * @return http response containing list of all {@link HouseChangeRequest HouseChangeRequest}
     * objects submitted by the
     * specified student
     */
    @GetMapping("/houseChangeRequestsByStudentId/{id}")
    public ResponseEntity<?> findHouseChangeRequestByStudentId(@PathVariable int id) {
        try {
            List<HouseChangeRequestSimple> resultHCR = fileService.getHouseChangeRequestsByStudent(id);
            return new ResponseEntity<>(resultHCR, HttpStatus.OK);
        } catch (Exception e) {
            String message = "Could not retrieve request data: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    /**
     * Http Put request to update the information of a house change request.
     * @param houseChangeRequest - the {@link HouseChangeRequest HouseChangeRequest} object representing
     *                           the new information
     * @return http response containing the updated {@link HouseChangeRequest HouseChangeRequest} object
     */
    @PutMapping()
    public ResponseEntity<?> updateHouseChangeRequest(@RequestBody HouseChangeRequest houseChangeRequest) {
        try {
            HouseChangeRequestSimple resultHCR = fileService.updateHouseChangeRequest(houseChangeRequest);
            return new ResponseEntity<>(resultHCR, HttpStatus.OK);
        } catch (Exception e) {
            String message = "Could not update House Change Request data: " + e.getMessage();
            System.out.println("\u001B[33mERROR: " + e + "\u001B[37m");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
        }
    }

    /**
     * Http Delete request to delete a house change request from the Maat database.
     * @param houseChangeRequest - Java object matching the data to be deleted
     * @return http response containing message declaring the success or failure of the deletion
     */
    @DeleteMapping()
    public ResponseEntity<ResponseMessage> deleteHouseChangeRequest(@RequestBody HouseChangeRequest houseChangeRequest) {
        String message;
        try {
            message = fileService.deleteHouseChangeRequestById(houseChangeRequest);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not delete request: " + e.getMessage();
            System.out.println("\u001B[33mERROR: " + e + "\u001B[37m");
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
     * Http Post method to get a list of {@link com.maat.model.HouseChangeRequest HouseChangeRequest} filtered on the options provided in the request body.
     * @param filters the {@link com.maat.model.HouseChangeRequestFilters HouseChangeRequestFilters} object representation of the filters to be used
     * @return http response containing list of all filtered {@link com.maat.model.HouseChangeRequest HouseChangeRequests} objects from the database
     */
    @PostMapping("/filter")
    public ResponseEntity<?> findFilteredHCRs(@RequestBody HouseChangeRequestFilters filters,
                                              @RequestParam(required = false, name="pageNum", defaultValue="0") Integer pageNum,
                                              @RequestParam(required = false, name="pageSize", defaultValue="-1") Integer pageSize,
                                              @RequestParam(required = false, name="sortOn", defaultValue="decidedDate") String sortOn,
                                              @RequestParam(required = false, name="asc", defaultValue="true") Boolean asc) {
        try {
            Pageable pageable = fileService.createPageable(pageNum, pageSize, sortOn, asc);
            Map<String, Object> hcrs = fileService.getFilteredHouseChangeRequests(filters, pageable);
            return new ResponseEntity<>(hcrs, HttpStatus.OK);
        } catch (Exception e) {
            String message = e.getMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
        }
    }

    /**
     * Turns on or off the students ability to submit house change requests.
     * @param enable - boolean value true = students can submit requests
     * @return the entity representing the current state
     */
    @PutMapping("/enable")
    public ResponseEntity<?> setEnabled(@RequestParam boolean enable) {
        try {
            HouseChangeRequestsEnabler result = fileService.setEnabled(enable);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            String message = e.getMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
        }
    }

    /**
     * Query the database to see if house change requests can be submitted.
     * @return the entity representing the current state
     */
    @GetMapping("/enable")
    public ResponseEntity<?> isEnabled() {
        try {
            HouseChangeRequestsEnabler enabler = fileService.isEnebled();
            return new ResponseEntity<>(enabler, HttpStatus.OK);
        } catch (Exception e) {
            String message = e.getMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
        }
    }
 }
