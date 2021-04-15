package pl.nice.snconverter.exception;

public class DatabaseTableEmptyException extends RuntimeException{
    public DatabaseTableEmptyException(String message) {
        super(message);
    }
}
