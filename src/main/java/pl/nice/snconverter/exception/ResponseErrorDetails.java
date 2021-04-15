package pl.nice.snconverter.exception;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseErrorDetails {
    private int status;
    private String message;
    private String description;
    private String errorCode;
    private String url;
}
//TODO Zamiast String url zrobic Location location
