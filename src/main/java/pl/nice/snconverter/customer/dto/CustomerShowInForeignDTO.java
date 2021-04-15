package pl.nice.snconverter.customer.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CustomerShowInForeignDTO {
    Long id;

    String name;
}
