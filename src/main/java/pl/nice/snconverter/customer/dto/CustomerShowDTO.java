package pl.nice.snconverter.customer.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CustomerShowDTO {
    Long id;

    String name;

    String idax;
}
