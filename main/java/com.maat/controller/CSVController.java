package com.maat.controller;

import com.maat.helper.CSVHelper;
import com.maat.message.ResponseMessage;
import com.maat.service.CSVService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;

/**
 * Modified from https://www.bezkoder.com/spring-boot-upload-csv-file/.
 * @author Brand Hauser
 */
@CrossOrigin("http://localhost:8080")
@Controller
@RequestMapping("/api/csvUpload")
public class CSVController {

    @Autowired
    CSVService fileService;

    /**
     * RESTful services method to receive and process a CSV multipart file from the Maat webapp.
     * @param file - Multipart File passed by http post request
     * @return Response message to declare success or failure of upload
     */
    @PostMapping()
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
        System.out.println("Uploading . . .");
        String message;

        if (CSVHelper.hasCSVFormat(file)) {
            try {
                fileService.save(file);
                message = "Uploaded the file successfully: " + file.getOriginalFilename();
                System.out.println("Upload complete.");
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
            } catch (Exception e) {
                message = "Could not upload the file: " + file.getOriginalFilename() + "!";
                System.out.println("Upload failed");
                System.out.println(Arrays.toString(e.getStackTrace()));
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
            }
        }

        message = "Please upload a csv file!";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
    }
}
