package com.zaver.bookahotel.exception;

public class RoomIsNotAvailableException extends RuntimeException {
    public RoomIsNotAvailableException(String message) {
        super(message);
    }
}

