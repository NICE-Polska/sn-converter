package pl.nice.snconverter.exception;

public class BadFileTypeException extends RuntimeException{
    public BadFileTypeException(String message) {
        super(message);
    }
    public String getErrorCode() {
        String errorCode = "0xBFT";
        return errorCode;
    }
}
