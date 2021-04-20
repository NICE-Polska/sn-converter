package pl.nice.snconverter.exception;


public class ObjectNotFoundException extends RuntimeException{
    public ObjectNotFoundException(String message) {
        super(message);
    }

    public String getErrorCode() {
        String errorCode = "0xONF";
        return errorCode;
    }
}
