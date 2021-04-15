package pl.nice.snconverter.exception;

import lombok.Data;

@Data
public class PageNumberTooHighException extends RuntimeException{
    private int pageNumber;

    public PageNumberTooHighException(String message, int pageNumber) {
        super(message);
        this.pageNumber = pageNumber;
    }
}
