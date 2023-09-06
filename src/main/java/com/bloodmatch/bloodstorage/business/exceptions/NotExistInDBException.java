package com.bloodmatch.bloodstorage.business.exceptions;

import java.util.NoSuchElementException;

public class NotExistInDBException extends NoSuchElementException {
    public NotExistInDBException(String message) {
        super(message);
    }
}