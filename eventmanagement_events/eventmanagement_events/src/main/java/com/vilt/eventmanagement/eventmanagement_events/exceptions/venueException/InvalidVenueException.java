package com.vilt.eventmanagement.eventmanagement_events.exceptions.venueException;

public class InvalidVenueException extends RuntimeException {
    public InvalidVenueException(String message) {
        super(message);
    }

    public InvalidVenueException(String message, Throwable cause) {
        super(message, cause);
    }
}
