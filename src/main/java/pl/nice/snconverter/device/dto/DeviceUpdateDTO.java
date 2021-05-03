package pl.nice.snconverter.device.dto;

import lombok.Builder;
import lombok.Value;
import pl.nice.snconverter.customer.dto.CustomerIdOnlyDTO;
import pl.nice.snconverter.message.MessageContent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Value
@Builder
public class DeviceUpdateDTO {
    @NotNull(message = MessageContent.VALID_NOT_NULL)
    @NotBlank(message = MessageContent.VALID_NOT_BLANK)
    @Size(max = 50, message = MessageContent.VALID_MAX_SIZE + 50)
    String name;

    @NotNull(message = MessageContent.VALID_NOT_NULL)
    @NotBlank(message = MessageContent.VALID_NOT_BLANK)
    @Size(max = 20, message = MessageContent.VALID_MAX_SIZE + 20)
    String serialNumber;

    byte[] image;

    @NotNull(message = MessageContent.VALID_NOT_NULL)
    LocalDate dateOfShipment;

    @NotNull(message = MessageContent.VALID_NOT_NULL)
    @NotBlank(message = MessageContent.VALID_NOT_BLANK)
    String vatId;

    CustomerIdOnlyDTO customer;
}
