package pl.nice.snconverter.response;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

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
