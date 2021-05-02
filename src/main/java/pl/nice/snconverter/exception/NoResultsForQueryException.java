package pl.nice.snconverter.exception;

public class NoResultsForQueryException extends RuntimeException{
    public NoResultsForQueryException(String message) {
        super(message);
    }

    public String getErrorCode() {
        String errorCode = "0xNRFQ";
        return errorCode;
    }
}
