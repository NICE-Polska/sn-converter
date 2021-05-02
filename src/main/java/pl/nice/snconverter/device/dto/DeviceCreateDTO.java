package pl.nice.snconverter.device.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;
import pl.nice.snconverter.customer.dto.CustomerIdOnlyDTO;
import pl.nice.snconverter.exception.unique.Unique;
import pl.nice.snconverter.media.dto.MediaOnlyIdDTO;
import pl.nice.snconverter.message.MessageContent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Value
@Builder
public class DeviceCreateDTO {
    @JsonCreator
    public DeviceCreateDTO(
            @JsonProperty("name") String name, @JsonProperty("serialNumber") String serialNumber,
            @JsonProperty("dateOfShipment") LocalDate dateOfShipment, @JsonProperty("vatId") String vatId,
            @JsonProperty("customer") CustomerIdOnlyDTO customer, @JsonProperty("media") MediaOnlyIdDTO media) {
        this.customer = customer;
        this.dateOfShipment = dateOfShipment;
        this.media = media;
        this.name = name;
        this.serialNumber = serialNumber;
        this.vatId = vatId;
    }

    @NotNull(message = MessageContent.VALID_NOT_NULL)
    @NotBlank(message = MessageContent.VALID_NOT_BLANK)
    @Size(max = 50, message = MessageContent.VALID_MAX_SIZE + 50)
    String name;

    @NotNull(message = MessageContent.VALID_NOT_NULL)
    @NotBlank(message = MessageContent.VALID_NOT_BLANK)
    @Size(max = 20, message = MessageContent.VALID_MAX_SIZE + 20)
    String serialNumber;

    @NotNull(message = MessageContent.VALID_NOT_NULL)
    LocalDate dateOfShipment;

    @NotNull(message = MessageContent.VALID_NOT_NULL)
    @NotBlank(message = MessageContent.VALID_NOT_BLANK)
    String vatId;

    @NotNull(message = MessageContent.VALID_NOT_NULL)
    CustomerIdOnlyDTO customer;

    MediaOnlyIdDTO media;
}
