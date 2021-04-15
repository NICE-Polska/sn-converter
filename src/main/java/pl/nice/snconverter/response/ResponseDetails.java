package pl.nice.snconverter.response;

import lombok.Builder;
import lombok.Data;

import java.net.URI;

@Data
@Builder
public class ResponseDetails {
    private String status;
    private String message;
    private Object data;
    private Object errors;
    private Integer pageNumber;
    private String nextPage;
    private Integer totalPages;
    private String url;
    private URI location;
}
