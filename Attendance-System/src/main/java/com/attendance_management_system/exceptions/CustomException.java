package com.attendance_management_system.exceptions;

import org.springframework.dao.DataAccessException;

public class CustomException extends Throwable {

    public CustomException(String s, DataAccessException e) {
    }
}
