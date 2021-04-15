package pl.nice.snconverter.customer.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CustomerIdOnlyDTO {
    Long id;

    @JsonCreator
    public CustomerIdOnlyDTO(@JsonProperty("id") Long id) {
        this.id = id;
    }
}
