package pl.nice.snconverter.device.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class DeviceIdOnlyDTO {
    Long id;

    @JsonCreator
    public DeviceIdOnlyDTO(Long id) {
        this.id = id;
    }
}
