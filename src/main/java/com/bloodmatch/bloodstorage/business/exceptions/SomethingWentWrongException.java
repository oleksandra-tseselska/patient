package com.bloodmatch.bloodstorage.business.exceptions;

public class SomethingWentWrongException extends RuntimeException {
    public SomethingWentWrongException(String message) {
        super(message);
    }
}
