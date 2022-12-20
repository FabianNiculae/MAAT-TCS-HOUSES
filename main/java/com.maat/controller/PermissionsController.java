package com.maat.controller;

import com.maat.message.ResponseMessage;
import com.maat.model.Permissions;
import com.maat.service.PermissionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Class for CRUD operations on Permissions entries within the Maat database.
 * @author Brand Hauser
 */
@CrossOrigin("http://localhost:8080")
@RestController
@RequestMapping("/api/permissions")
public class PermissionsController {

    @Autowired
    PermissionsService fileService;

    /**
     * Http Post request to add a permissions to the Maat database.
     * @param permissions - the {@link Permissions Permissions} object representing the permissions to be added to the database
     * @return http response containing the {@link Permissions Permissions} object upon success
     */
    @PostMapping()
    public ResponseEntity<?> addPermissions(@RequestBody Permissions permissions) {
        try {
            Permissions resultPermissions = fileService.savePermissions(permissions);
            return new ResponseEntity<>(resultPermissions, HttpStatus.CREATED);
        } catch (Exception e) {
            String message = "Could not add permissions: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
        }
    }


    /**
     * Http Get request to retrieve a list of all permissionss within the Maat database.
     * @return http response containing the list of {@link Permissions Permissions} objects to be converted to JSON
     */
    @GetMapping()
    public ResponseEntity<?> findAllPermissionss() {
        try {
            List<Permissions> permissions = fileService.getAllPermissionss();
            if (permissions.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(permissions, HttpStatus.OK);
        } catch (Exception e) {
            String message = "Failed to retrieve data: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
        }
    }

    /**
     * Http Put request to update a permissions within the Maat database.
     * @param permissions - the {@link Permissions Permissions} object representation of the selected permissions
     * @return http response containing the updated {@link Permissions Permissions} object representation of the
     * selected permissions
     */
    @PutMapping()
    public ResponseEntity<?> updatePermissions(@RequestBody Permissions permissions) {
        try {
            Permissions resultPermissions = fileService.updatePermissions(permissions);
            return new ResponseEntity<>(resultPermissions, HttpStatus.OK);
        } catch (Exception e) {
            String message = "Could not update permissions data: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
        }
    }

    /**
     * Http Delete request to delete a permissions from the Maat database.
     * @param permissions - the {@link Permissions Permissions} object representation of the selected permissions
     * @return http response containing a message declaring success or failure of deletion
     */
    @DeleteMapping()
    public ResponseEntity<ResponseMessage> deletePermissions(@RequestBody Permissions permissions) {
        String message;
        try {
            message = fileService.deletePermissions(permissions);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not delete permissions: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
        }
    }
}
