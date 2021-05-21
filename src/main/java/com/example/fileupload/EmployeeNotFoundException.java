package com.example.fileupload;

class EmployeeNotFoundException extends RuntimeException {

    EmployeeNotFoundException(String id) {
        super("Could not find employee " + id);
    }
}
