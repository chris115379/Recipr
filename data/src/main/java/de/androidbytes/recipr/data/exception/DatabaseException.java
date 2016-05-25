package de.androidbytes.recipr.data.exception;

/**
 * Created by Christoph on 30.04.2016.
 */
public class DatabaseException extends RuntimeException {
    
    public DatabaseException() {
    }
    
    public DatabaseException(String message) {
        super(message);
    }
    
    public DatabaseException(Throwable cause) {
        super(cause);
    }
    
    public DatabaseException(String message, Throwable cause) {
        super(message, cause);
    }
}