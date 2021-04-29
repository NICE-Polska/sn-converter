package pl.nice.snconverter.customer.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;
import pl.nice.snconverter.customer.CustomerService;
import pl.nice.snconverter.exception.unique.Operation;
import pl.nice.snconverter.exception.unique.Unique;
import pl.nice.snconverter.message.MessageContent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Value
@Builder
public class CustomerUpdateDTO {
    @JsonCreator
    public CustomerUpdateDTO(@JsonProperty("name") String name, @JsonProperty("idax") String idax) {
        this.name = name;
        this.idax = idax;
    }

    @NotNull(message = MessageContent.VALID_NOT_NULL)
    @NotBlank(message = MessageContent.VALID_NOT_BLANK)
    @Size(max = 60, message = MessageContent.VALID_MAX_SIZE + 60)
    String name;

    @Unique(fieldName = "idax", handler = CustomerService.class, operation = Operation.UPDATE, message = MessageContent.VALID_UNIQUE)
    @NotNull(message = MessageContent.VALID_NOT_NULL)
    String idax;
}
