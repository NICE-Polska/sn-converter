package pl.nice.snconverter.customer.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;
import pl.nice.snconverter.message.MessageContent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Value
@Builder
public class CustomerCreateDTO {
    @JsonCreator
    public CustomerCreateDTO(@JsonProperty("name") String name, @JsonProperty("idax") String idax) {
        this.name = name;
        this.idax = idax;
    }

    @NotNull(message = MessageContent.VALID_NOT_NULL)
    @NotBlank(message = MessageContent.VALID_NOT_BLANK)
    @Size(max = 60, message = MessageContent.VALID_MAX_SIZE + 60)
    String name;

    @NotNull(message = MessageContent.VALID_NOT_NULL)
    @NotBlank(message = MessageContent.VALID_NOT_BLANK)
    @Size(max = 4, message = MessageContent.VALID_MAX_SIZE + 4)
    String idax;
}
//TODO zrobic te pieprzone JsonCreatory dla wszytkich create i update
