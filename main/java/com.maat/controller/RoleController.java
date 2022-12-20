package com.maat.controller;

import com.maat.message.ResponseMessage;
import com.maat.model.Role;
import com.maat.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Class for CRUD operations on Role entries within the Maat database.
 * @author Brand Hauser
 */
@CrossOrigin("http://localhost:8080")
@RestController
@RequestMapping("/api/roles")
public class RoleController {

    @Autowired
    RoleService fileService;

    /**
     * Http Post request to add a role to the Maat database.
     * @param role - the {@link Role Role} object representing the role to be added to the database
     * @return http response containing the {@link Role Role} object upon success
     */
    @PostMapping()
    public ResponseEntity<?> addRole(@RequestBody Role role) {
        try {
            Role resultRole = fileService.saveRole(role);
            return new ResponseEntity<>(resultRole, HttpStatus.CREATED);
        } catch (Exception e) {
            String message = "Could not add role: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
        }
    }


    /**
     * Http Get request to retrieve a list of all roles within the Maat database.
     * @return http response containing the list of {@link Role Role} objects to be converted to JSON
     */
    @GetMapping()
    public ResponseEntity<?> findAllRoles() {
        try {
            List<Role> role = fileService.getAllRoles();
            if (role.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(role, HttpStatus.OK);
        } catch (Exception e) {
            String message = "Failed to retrieve data: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
        }
    }

    /**
     * Http Put request to update a role within the Maat database.
     * @param role - the {@link Role Role} object representation of the selected role
     * @return http response containing the updated {@link Role Role} object representation of the
     * selected role
     */
    @PutMapping()
    public ResponseEntity<?> updateRole(@RequestBody Role role) {
        try {
            Role resultRole = fileService.updateRole(role);
            return new ResponseEntity<>(resultRole, HttpStatus.OK);
        } catch (Exception e) {
            String message = "Could not update role data: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
        }
    }

    /**
     * Http Delete request to delete a role from the Maat database.
     * @param role - the {@link Role Role} object representation of the selected role
     * @return http response containing a message declaring success or failure of deletion
     */
    @DeleteMapping()
    public ResponseEntity<ResponseMessage> deleteRole(@RequestBody Role role) {
        String message;
        try {
            message = fileService.deleteRole(role);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not delete role: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
        }
    }
}
