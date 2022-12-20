package com.maat.controller;

import com.maat.message.ResponseMessage;
import com.maat.model.*;
import com.maat.service.UserService;
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
 * Class for CRUD operations on User entries within the Maat database.
 * @author Brand Hauser
 */
@CrossOrigin("http://localhost:8080")
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    UserService fileService;

    /**
     * Http Post request to add a user to the Maat database.
     * @param user - the {@link User User} object representation of the user
     *                 converted from JSON
     * @return the http response - if successful response includes JSON object
     */
    @PostMapping()
    public ResponseEntity<?> addUser(@RequestBody User user) {
        try {
            fileService.ifUserExists(user.getIdNumber());
            if (user.getHouse() != null) user.setDateAssignedToHouse(new Date());
            user.setEnabled(true);
            UserSimple resultUser = fileService.saveUser(user);
            return new ResponseEntity<>(resultUser, HttpStatus.CREATED);
        } catch (Exception e) {
            String message = "Could not add user: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
        }
    }

    /**
     * Http Get request to retrieve a list of all user from the Maat database.
     * @return http response containing the list of {@link User User} object representing the
     * users to be converted to JSON
     */
    @GetMapping()
    public ResponseEntity<?> findAllUser(@RequestParam(required = false, name = "id") Integer id,
                                         @RequestParam(required = false, name="pageNum", defaultValue="0") Integer pageNum,
                                         @RequestParam(required = false, name="pageSize", defaultValue="-1") Integer pageSize,
                                         @RequestParam(required = false, name="sortOn", defaultValue="lastName") String sortOn,
                                         @RequestParam(required = false, name="asc", defaultValue="true") Boolean asc) {
        try {
            if (id == null) {
                Pageable pageable = fileService.createPageable(pageNum, pageSize, sortOn, asc);
                UserFilters userFilters = new UserFilters();
                Map<String, Object> users = fileService.getFilteredUsers(userFilters, pageable);
                if ((Boolean)users.get("empty")) {
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                }
                List<Object> resultList = new java.util.ArrayList<>(Collections.singletonList(users));
                resultList.add( fileService.getFilterables());
                return new ResponseEntity<>(resultList, HttpStatus.OK);
            } else {
                UserSimple resultUser = fileService.getUserById(id);
                if (resultUser == null) {
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                }
                return new ResponseEntity<>(resultUser, HttpStatus.OK);
            }
        } catch (Exception e) {
            String message = "Failed to retrieve data: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
        }
    }

    /**
     * Http Put request to update data of user in Maat database.
     * @param user - the {@link User User} object representation of the user
     *                 converted from JSON
     * @return http response containing the {@link User User} object representation of the
     * user to be
     * converted to JSON
     */
    @PutMapping()
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        try {
            UserSimple resultUser = fileService.updateUser(user);
            return new ResponseEntity<>(resultUser, HttpStatus.OK);
        } catch (Exception e) {
            String message = "Could not update user data: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
        }
    }

    /**
     * Http Delete request to delete a user from the Maat database.
     * @param id - the id number of the user to be deleted
     * @return http response containing a message declaring success or failure of deletion
     */
    @DeleteMapping()
    public ResponseEntity<ResponseMessage> deleteUser(@RequestParam(name = "id") Integer id) {
        String message;
        try {
            message = fileService.deleteUserById(id);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not delete user: " + e.getMessage();
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
     * Http Post method to get a list of users filtered on the options provided in the request body.
     * @param filters the {@link com.maat.model.UserFilters UserFilters} object representation of the filters to be used
     * @return http response containing list of all filtered {@link com.maat.model.User User} objects from the database
     */
    @PostMapping("/filter")
    public ResponseEntity<?> findUsersFiltered(@RequestBody UserFilters filters,
                                               @RequestParam(required = false, name="pageNum", defaultValue="0") Integer pageNum,
                                               @RequestParam(required = false, name="pageSize", defaultValue="-1") Integer pageSize,
                                               @RequestParam(required = false, name="sortOn", defaultValue="lastName") String sortOn,
                                               @RequestParam(required = false, name="asc", defaultValue="true") Boolean asc) {
        try {
            Pageable pageable = fileService.createPageable(pageNum, pageSize, sortOn, asc);
            Map<String, Object> users = fileService.getFilteredUsers(filters, pageable);
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception e) {
            String message = e.getMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
        }
    }

}
