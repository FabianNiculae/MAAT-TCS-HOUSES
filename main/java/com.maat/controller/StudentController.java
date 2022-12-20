package com.maat.controller;

import com.maat.message.ResponseMessage;
import com.maat.model.*;
import com.maat.model.StudentFilters;
import com.maat.model.StudentSimple;
import com.maat.model.StudentSimpleHouse;
import com.maat.service.StudentService;
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
 * Class for CRUD operations on Student entries within the Maat database.
 * @author Brand Hauser
 */
@CrossOrigin("http://localhost:8080")
@RestController
@RequestMapping("/api/students")
public class StudentController {

    @Autowired
    StudentService fileService;


    /**
     * Http Post request to add a student to the Maat database.
     * @param student - the {@link com.maat.model.Student Student} object representation of the student to be added
     * @return http response containing the {@link com.maat.model.Student Student} object representation of the added
     * student upon success
     */
    @PostMapping()
    public ResponseEntity<?> addStudent(@RequestBody Student student) {
        try {
            student.setEnabled(true);
            fileService.ifStudentExists(student.getIdNumber());
            if (student.getHouse() != null) student.setDateAssignedToHouse(new Date());
            StudentSimple resultStudent = fileService.saveStudent(student);
            return new ResponseEntity<>(resultStudent, HttpStatus.CREATED);
        } catch (Exception e) {
            String message = "Could not add student: " + e.getMessage();
            System.out.println("\u001B[33mERROR: " + e + "\u001B[37m");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
        }
    }

    /**
     * Http Get method to retrieve all students from the Maat database.
     * Paginate if both pageNum and pageSize are given as parameters
     * @return http response containing list of all {@link com.maat.model.Student Student} objects from the database
     * to be converted to JSON, possibly paginated
     */
    @GetMapping()
    public ResponseEntity<?> findAllStudents(@RequestParam(required = false, name = "id") Integer id,
                                             @RequestParam(required = false, name="pageNum", defaultValue="0") Integer pageNum,
                                             @RequestParam(required = false, name="pageSize", defaultValue="-1") Integer pageSize,
                                             @RequestParam(required = false, name="sortOn", defaultValue="lastName") String sortOn,
                                             @RequestParam(required = false, name="asc", defaultValue="true") Boolean asc) {
        try {
            if (id == null) {
                Pageable pageable = fileService.createPageable(pageNum, pageSize, sortOn, asc);
                Map<String, Object> students = fileService.getAllStudents(pageable);
                if ((Boolean)students.get("empty")) {
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                }
                List<Object> resultList = new java.util.ArrayList<>(Collections.singletonList(students));
                resultList.add( fileService.getFilterables());
                return new ResponseEntity<>(resultList, HttpStatus.OK);
            } else {
                StudentSimple resultStudent = fileService.getStudentById(id);
                if (resultStudent == null) {
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                }
                return new ResponseEntity<>(resultStudent, HttpStatus.OK);
            }
        } catch (Exception e) {
            String message = "Failed to retrieve data: " + e.getMessage();
            System.out.println("\u001B[33mERROR: " + e + "\u001B[37m");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
        }
    }

    /**
     * Http Post method to get a list of students filtered on the options provided in the request body.
     * @param filters the {@link com.maat.model.StudentFilters StudentFilters} object representation of the filters to be used
     * @return http response containing list of all filtered {@link com.maat.model.Student Student} objects from the database
     */
    @PostMapping("/filter")
    public ResponseEntity<?> findStudentsFiltered(@RequestBody StudentFilters filters,
                                                  @RequestParam(required = false, name="pageNum", defaultValue="0") Integer pageNum,
                                                  @RequestParam(required = false, name="pageSize", defaultValue="-1") Integer pageSize,
                                                  @RequestParam(required = false, name="sortOn", defaultValue="lastName") String sortOn,
                                                  @RequestParam(required = false, name="asc", defaultValue="true") Boolean asc) {
        try {
            Pageable pageable = fileService.createPageable(pageNum, pageSize, sortOn, asc);
            Map<String, Object> students = fileService.getFilteredStudents(filters, pageable);
            return new ResponseEntity<>(students, HttpStatus.OK);
        } catch (Exception e) {
            String message = e.getMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
        }
    }

    /**
     * Http Get request to retrieve a specified student from the Maat database for the Student view.
     * @param id - the student id number of the selected student
     * @return http response containing the {@link com.maat.model.Student Student} object representation of the
     * selected student to be converted to JSON
     */
    @GetMapping("/student")
    public ResponseEntity<?> findStudentById(@RequestParam(name = "id") int id) {
        try {
            StudentSimpleHouse resultStudent = fileService.getStudentByIdStudentView(id);
            if (resultStudent == null) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(resultStudent, HttpStatus.OK);
        } catch (Exception e) {
            String message = "Could not retrieve student data: " + e.getMessage();
            System.out.println("\u001B[33mERROR: " + e + "\u001B[37m");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
        }
    }

    /**
     * Http Update request to modify the data of a student in the Maat database.
     * @param student - the {@link com.maat.model.Student Student} object representation of the student
     * @return http response containing the updated {@link com.maat.model.Student Student} object
     */
    @PutMapping()
    public ResponseEntity<?> updateStudent(@RequestBody Student student) {
        try {
            StudentSimple resultStudent = fileService.updateStudent(student);
            return new ResponseEntity<>(resultStudent, HttpStatus.OK);
        } catch (Exception e) {
            String message = "Could not update student data: " + e.getMessage();
            System.out.println("\u001B[33mERROR: " + e + "\u001B[37m");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
        }
    }

    /**
     * Http Delete request to delete a student from the Maat database.
     * @param id - the student id number of the selected student
     * @return http response containing message declaring the success or failure of the deletion
     */
    @DeleteMapping()
    public ResponseEntity<ResponseMessage> deleteStudent(@RequestParam(name = "id") Integer id) {
        String message;
        try {
            message = fileService.deleteStudentById(id);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not delete student: " + e.getMessage();
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
            System.out.println("\u001B[33mERROR: " + e + "\u001B[37m");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
        }
    }
}
