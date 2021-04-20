package pl.nice.snconverter.exception;

public class DatabaseTableEmptyException extends RuntimeException{
    public DatabaseTableEmptyException(String message) {
        super(message);
    }

    public String getErrorCode() {
        String errorCode = "0xDTE";
        return errorCode;
    }
}
