package org.safari.houseservicebackend.exception;

public class RecordNotFoundException extends RuntimeException {
    public RecordNotFoundException() {
        super("record not found");
    }

    public RecordNotFoundException(String message) {
        super(message);
    }
}
