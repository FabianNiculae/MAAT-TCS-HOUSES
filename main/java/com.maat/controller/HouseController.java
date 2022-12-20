package com.maat.controller;

import com.maat.message.ResponseMessage;
import com.maat.model.House;
import com.maat.model.HouseSimple;
import com.maat.service.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

/**
 * Class for CRUD operations on House entries within the Maat database.
 * @author Brand Hauser
 */
@CrossOrigin("http://localhost:8080")
@RestController
@RequestMapping("/api/houses")
public class HouseController {

    @Autowired
    HouseService fileService;

    /**
     * Http Post request to add a house to the Maat database.
     * @param house - the {@link com.maat.model.House House} object representing the house to be added to the database
     * @return http response containing the {@link com.maat.model.House House} object upon success
     */
    @PostMapping()
    public ResponseEntity<?> addHouse(@RequestBody House house) {
        try {
            HouseSimple resultHouse = fileService.saveHouse(house);
            return new ResponseEntity<>(resultHouse, HttpStatus.CREATED);
        } catch (Exception e) {
            String message = "Could not add house: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
        }
    }

    /**
     * Http Get request to retrieve a list of all houses within the Maat database.
     * @return http response containing the list of {@link com.maat.model.House House} objects to be converted to JSON
     */
    @GetMapping()
    public ResponseEntity<?> findAllHouses(@RequestParam(required = false, name = "name") String name) {
        try {
            System.out.println("find");
            if (name == null) {
                List<HouseSimple> houses = fileService.getAllHouses();
                if (houses.isEmpty()) {
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                }
                List<Object> resultList = new java.util.ArrayList<>(Collections.singletonList(houses));
                resultList.add( fileService.getFilterables());
                return new ResponseEntity<>(resultList, HttpStatus.OK);
            } else {
                HouseSimple resultHouse = fileService.getHouseByName(name);
                if(resultHouse == null) {
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                }
                return new ResponseEntity<>(resultHouse, HttpStatus.OK);
            }
        } catch (Exception e) {
            String message = "Failed to retrieve data: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
        }
    }

    /**
     * Http Get request to retrieve a selected house from the Maat database.
     * @param name - the name of the selected house
     * @return http response containing the {@link com.maat.model.House House} object representing the selected house
     * to be converted
     * to JSON
     */
    @GetMapping("/housesByName/{name}")
    public ResponseEntity<?> findHousesById(@PathVariable String name) {
        try {
            System.out.println("fail");
            HouseSimple resultHouse = fileService.getHouseByName(name);
            return new ResponseEntity<>(resultHouse, HttpStatus.OK);
        } catch (Exception e) {
            String message = "Could not retrieve house data: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    /**
     * Http Put request to update a house within the Maat database.
     * @param house - the {@link com.maat.model.House House} object representation of the selected house
     * @return http response containing the updated {@link com.maat.model.House House} object representation of the
     * selected house
     */
    @PutMapping()
    public ResponseEntity<?> updateHouse(@RequestBody House house) {
        try {
            System.out.println("update");
            HouseSimple resultHouse = fileService.updateHouse(house);
            return new ResponseEntity<>(resultHouse, HttpStatus.OK);
        } catch (Exception e) {
            String message = "Could not update house data: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
        }
    }

    /**
     * Http Delete request to delete a house from the Maat database.
     * @param name - the {@link com.maat.model.House House} object representation of the selected house
     * @return http response containing a message declaring success or failure of deletion
     */
    @DeleteMapping()
    public ResponseEntity<ResponseMessage> deleteHouse(@RequestParam(required = false, name = "name") String name) {
        String message;
        try {
            System.out.println("delete");
            message = fileService.deleteHouseByName(name);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not delete house: " + e.getMessage();
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
}
