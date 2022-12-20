package com.maat.message;

/**
 * Basic class for constructing messages as a response to an http request.
 * Modified from https://www.bezkoder.com/spring-boot-upload-csv-file/
 */
public class ResponseMessage {
    private String message;

    public ResponseMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
