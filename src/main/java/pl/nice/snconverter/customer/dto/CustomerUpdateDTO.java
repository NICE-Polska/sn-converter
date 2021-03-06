package pl.nice.snconverter.customer.dto;

import lombok.Builder;
import lombok.Value;
import pl.nice.snconverter.message.MessageContent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Value
@Builder
public class CustomerUpdateDTO {
    @NotNull(message = MessageContent.VALID_NOT_NULL)
    @NotBlank(message = MessageContent.VALID_NOT_BLANK)
    @Size(max = 60, message = MessageContent.VALID_MAX_SIZE + 60)
    String name;

    @NotNull(message = MessageContent.VALID_NOT_NULL)
    String idax;
}
